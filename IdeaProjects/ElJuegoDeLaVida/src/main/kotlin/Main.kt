import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import kotlin.math.max
import kotlin.math.min

// SIGO TRABAJANDO EN EL PROGRAMA

/**
 * Clase que maneja la conexión a la base de datos y las operaciones relacionadas.
 */
class BaseDatos {
    private val url = "jdbc:mysql://localhost:3306/curso_sql"
    private val usuario = "root"
    private val contrasena = "1234"

    /**
     * Me dispongo a crear las tablas 'Generaciones' y 'Celulas' en la base de datos.
     */
    fun crearTablas() {
        val creacionTablaGeneraciones = """
            CREATE TABLE IF NOT EXISTS Generaciones (
            generacion_id INT AUTO_INCREMENT PRIMARY KEY,
            timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            num_celulas_vivas INT,
            num_celulas_muertas INT
            )
        """.trimIndent()

        // He elegido guardar fila y columna en la tabla celulas para guardar la posición
        val creacionTablaCelulas = """
            CREATE TABLE IF NOT EXISTS Celulas (
            celulas_id INT AUTO_INCREMENT PRIMARY KEY,
            generacion_id INT,
            fila INT,
            col INT,
            alive BOOLEAN,
            FOREIGN KEY (generacion_id) REFERENCES Generaciones(generacion_id)
            )
        """.trimIndent()

        conectar().use { connection ->
            connection.createStatement().use { stmt ->
                stmt.execute(creacionTablaCelulas)
                stmt.execute(creacionTablaGeneraciones)
            }
        }
    }

    /**
     * Establezco la conexión.
     */
    private fun conectar(): Connection {
        return DriverManager.getConnection(url, usuario, contrasena)
    }

    /**
     * Guarda la información de la generación actual en la base de datos.
     *
     * @param numCelulasVivas Número de células vivas en la generación actual.
     * @param numCelulasMuertas Las celulas que han muerto en la generación actual.
     */
    fun guardarGeneracion(numCelulasVivas: Int, numCelulasMuertas: Int) {
        val query = "INSERT INTO Generaciones (num_celulas_vivas, num_celulas_muertas) VALUES (?, ?)"

        try {
            conectar().use { conn ->
                conn.prepareStatement(query).use { pstmt ->
                    pstmt.setInt(1, numCelulasVivas)
                    pstmt.setInt(2, numCelulasMuertas)
                    pstmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    /**
     * Para recuperar los datos de la Base de Datos
     */
    fun cargarGeneracion(generacionId: Int, game: GameOfLife) {
        val query = "SELECT fila, col, alive FROM Celulas WHERE generacion_id = ?"

        try {
            conectar().use { conn ->
                conn.prepareStatement(query).use { pstmt ->
                    pstmt.setInt(1, generacionId)

                    val rs = pstmt.executeQuery()
                    while (rs.next()) {
                        val row = rs.getInt("fila")
                        val col = rs.getInt("col")
                        val alive = rs.getBoolean("alive")
                        game.board[row][col] = alive
                    }
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    /**
     * Obtengo el número de células vivas en cada cuadrado...
     *
     * @param x Coordenada x del vértice superior izquierdo del cuadrado.
     * @param y Coordenada y del vértice superior izquierdo del cuadrado.
     * @param x1 Coordenada x del vértice inferior derecho del cuadrado.
     * @param y1 Coordenada y del vértice inferior derecho del cuadrado.
     * @return Número de células vivas en el cuadrado.
     */
    fun obtenerCelulasVivasEnCuadrado(x: Int, y: Int, x1: Int, y1: Int): Int {
        val query =
            "SELECT COUNT(*) FROM Celulas WHERE fila BETWEEN ? AND ? AND col BETWEEN ? AND ? AND alive = true"

        try {
            conectar().use { conn ->
                conn.prepareStatement(query).use { pstmt ->
                    pstmt.setInt(1, min(x, x1))
                    pstmt.setInt(2, max(x, x1))
                    pstmt.setInt(3, min(y, y1))
                    pstmt.setInt(4, max(y, y1))

                    val rs = pstmt.executeQuery()
                    if (rs.next()) {
                        return rs.getInt(1)
                    }
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return 0
    }

    /**
     * Me gustaría poder usarla en un futuro para sacar datos de la última gen
     */
    fun obtenerUltimaGeneracionId(): Int {
        val query = "SELECT generacion_id FROM Generaciones ORDER BY generacion_id DESC LIMIT 1"

        try {
            conectar().use { conn ->
                conn.prepareStatement(query).use { pstmt ->
                    val rs = pstmt.executeQuery()
                    if (rs.next()) {
                        return rs.getInt("generacion_id")
                    }
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return -1
    }

}


/**
 * Clase que representa el juego de la vida y realiza operaciones relacionadas con él.
 *
 * @property filas Número de filas en el tablero.
 * @property columnas Número de columnas en el tablero.
 */
class GameOfLife(val filas: Int, val columnas: Int) {
    var board = Array(filas) { BooleanArray(columnas) }
    private var previousBoard = Array(filas) { BooleanArray(columnas) }

    /**
     * Inicializa el tablero con células vivas o muertas de manera aleatoria.
     */
    fun iniciarTablero() {
        for (i in 0..<filas) {
            for (j in 0..<columnas) {
                board[i][j] = Math.random() < 0.5
            }
        }
    }

    /**
     * Calcula la siguiente generación del juego de la vida y actualiza la base de datos con las estadísticas.
     *
     * @param db Objeto BaseDatos, me sirve para hacer operaciones en la base de datos.
     */
    fun siguienteTablero(db: BaseDatos): Pair<Int, Int> {
        val newBoard = Array(filas) { BooleanArray(columnas) }
        var numCelulasVivas = 0
        var numCelulasMuertas = 0

        for (i in 0..<filas) {
            for (j in 0..<columnas) {
                val aliveNeighbors = countAliveNeighbors(i, j)
                if (previousBoard[i][j]) {
                    newBoard[i][j] = aliveNeighbors == 2 || aliveNeighbors == 3
                } else {
                    newBoard[i][j] = aliveNeighbors == 3
                }
                if (newBoard[i][j]) {
                    numCelulasVivas++
                } else {
                    numCelulasMuertas++
                }
            }
        }

        previousBoard = board
        board = newBoard
        db.guardarGeneracion(numCelulasVivas, numCelulasMuertas)

        return Pair(numCelulasVivas, numCelulasMuertas)
    }

    /**
     * Para recuperar objetos de la tabla
     *
     * @param generacionId La clave primaria del id de la generación
     * @param db El objeto de mi clase Base Datos
     */
    fun cargarTableroDesdeGeneracion(generacionId: Int, db: BaseDatos) {
        // Reinicia el tablero
        board = Array(filas) { BooleanArray(columnas) }

        // Carga las células de la generación específica desde la base de datos
        db.cargarGeneracion(generacionId, this)
    }

    /**
     * Cuenta el número de vecinos vivos alrededor de una célula en el tablero.
     *
     * @param fila Fila de la célula.
     * @param col Columna de la célula.
     * @return Número de vecinos vivos.
     */
    private fun countAliveNeighbors(fila: Int, col: Int): Int {
        var count = 0
        // Uso un doble bucle for calcular posiciones de filas y columnas
        for (i in -1..1) {
            for (j in -1..1) {
                val newRow = (fila + i + filas) % filas
                val newCol = (col + j + columnas) % columnas
                count += if (board[newRow][newCol]) 1 else 0
            }
        }
        count -= if (board[fila][col]) 1 else 0
        return count
    }

    /**
     * Imprime el estado actual del tablero en la consola.
     */
    fun printBoard() {
        for (i in 0..<filas) {
            for (j in 0..<columnas) {
                print(if (board[i][j]) "■ " else "□ ")
            }
            println()
        }
    }
}

/**
 * Función principal que ejecuta el programa del Juego de la Vida.
 * Crea una instancia del juego y de la base de datos, crea las tablas en la base de datos,
 * inicializa el tablero, guarda la primera generación en la base de datos,
 * itera 10 veces para calcular y mostrar las siguientes generaciones,
 * y finalmente obtiene y muestra el número de células vivas en un cuadrado específico.
 */
fun main() {
    // Objetos de la clase GameOfLife y de la Base Datos
    val game = GameOfLife(filas = 10, columnas = 10)
    val db = BaseDatos()

    db.crearTablas()
    game.iniciarTablero()
    db.guardarGeneracion(0, 0)

    // Cambio el número de veces que quiero que itere por el número de generaciones que quiero que calcule.
    // Si meto muchas MYSQL puede bloquearse
    for (i in 1..20) {
        val (numCelulasVivas, numCelulasMuertas) = game.siguienteTablero(db)

        println("Generación $i:")
        game.printBoard()
        println("Número de células vivas: $numCelulasVivas")
        println("Número de células muertas: $numCelulasMuertas")
        println()
    }

    // val celulasVivasEnCuadrado = db.obtenerCelulasVivasEnCuadrado(1, 1, 5, 5)
    // println("Número de células vivas en el cuadrado: $celulasVivasEnCuadrado")
}
