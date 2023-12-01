package com.arodmar432p.blackjackprueba3.modelo

import android.content.res.Resources
import com.arodmar432p.blackjackprueba3.modelo.Carta
import com.arodmar432p.blackjackprueba3.modelo.Jugador
import com.arodmar432p.blackjackprueba3.modelo.Palo
import com.arodmar432p.blackjackprueba3.modelo.Naipe

class Baraja(private val recursos: Resources, private val recursosBocaAbajo: Resources) {
    // Lista para almacenar las cartas de la baraja
    private val listaCartas = ArrayList<Carta>()

    init {

        /**
         * Función para crear una baraja de cartas.
         *
         * @param recursos Un mapa que asocia los nombres de los recursos con sus identificadores.
         */
        class Baraja(
            private val recursos: Map<String, Int>,
            private val recursosBocaAbajo: Map<String, Int>
        ) {
            // Lista para almacenar las cartas de la baraja
            private val listaCartas = ArrayList<Carta>()

            init {
                crearBaraja()
            }

            /**
             * Función para crear una baraja de cartas.
             */
            private fun crearBaraja() {
                // Itera sobre todos los palos
                for (palo in Palo.values()) {
                    // Itera sobre todos los naipes
                    for (naipe in Naipe.values()) {
                        // Determina los puntos mínimos y máximos de la carta
                        val puntosMin = if (naipe == Naipe.AS) 1 else naipe.valor
                        val puntosMax = if (naipe == Naipe.AS) 11 else naipe.valor

                        // Determina el nombre del recurso de la carta
                        val nombreRecurso = "${palo.toString().lowercase()}${naipe.valor}"

                        // Obtiene el identificador del recurso drawable
                        val idDrawable = recursos[nombreRecurso]
                            ?: throw RuntimeException("Recurso no encontrado: $nombreRecurso")

                        // Obtiene el identificador del recurso drawable para la carta boca abajo
                        val idDrawableBocaAbajo = recursosBocaAbajo[nombreRecurso]
                            ?: throw RuntimeException("Recurso no encontrado: $nombreRecurso")

                        // Añade la carta a la lista de cartas
                        listaCartas.add(
                            Carta(
                                naipe,
                                palo,
                                puntosMin,
                                puntosMax,
                                idDrawable,
                                idDrawableBocaAbajo
                            )
                        )
                    }
                }
            }
        }
    }
    /**
     * Función para barajar las cartas.
     */
    fun barajar() {
        listaCartas.shuffle()
    }

    /**
     * Función para obtener una carta de la baraja.
     *
     * @return Carta La carta obtenida de la baraja.
     */
    fun dameCarta(): Carta {
        return listaCartas.removeAt(listaCartas.size - 1)
    }

    /**
     * Función para repartir cartas a múltiples jugadores.
     *
     * @param jugadores La lista de jugadores a los que repartir las cartas.
     * @param numCartas El número de cartas a repartir a cada jugador.
     */
    fun repartirCartas(jugadores: List<Jugador>, numCartas: Int) {
        for (i in 0 until numCartas) {
            for (jugador in jugadores) {
                jugador.mano.add(dameCarta())
            }
        }
    }
}