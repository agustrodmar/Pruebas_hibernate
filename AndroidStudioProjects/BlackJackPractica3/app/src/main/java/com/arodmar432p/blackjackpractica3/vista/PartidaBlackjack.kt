package com.arodmar432p.blackjackpractica3.vista


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arodmar432p.blackjackpractica3.R
import com.arodmar432p.blackjackpractica3.modelo.Jugador
import com.arodmar432p.blackjackpractica3.vistaModelo.JuegoViewModel


/*val recursos = mapOf(
        "corazonesa" to painterResource(id = R.drawable.corazonesa),
        "corazones2" to painterResource(id = R.drawable.corazones3),
        "corazones3" to painterResource(id = R.drawable.corazones4),
        "corazones5" to painterResource(id = R.drawable.corazones5),
        "corazones6" to painterResource(id = R.drawable.corazones6),
        "corazones7" to painterResource(id = R.drawable.corazones7),
        "corazones8" to painterResource(id = R.drawable.corazones8),
        "corazones9" to painterResource(id = R.drawable.corazones9),
        "corazonesj" to painterResource(id = R.drawable.corazonesj),
        "corazonesq" to painterResource(id = R.drawable.corazonesq),
        "corazonesk" to painterResource(id = R.drawable.corazonesk),
        "diamantesa" to painterResource(id = R.drawable.diamantesa),
        "diamantes2" to painterResource(id = R.drawable.diamantes2),
        "diamantes3" to painterResource(id = R.drawable.diamantes3),
        "diamantes4" to painterResource(id = R.drawable.diamantes4),
        "diamantes5" to painterResource(id = R.drawable.diamantes5),
        "diamantes5" to painterResource(id = R.drawable.diamantes6),
        "diamantes6" to painterResource(id = R.drawable.diamantes7),
        "diamantes7" to painterResource(id = R.drawable.diamantes8),
        "diamantes8" to painterResource(id = R.drawable.diamantes9),
        "diamantes9" to painterResource(id = R.drawable.diamantes10),
        "diamantesj" to painterResource(id = R.drawable.diamantesj),
        "diamantesq" to painterResource(id = R.drawable.diamantesq),
        "diamantesk" to painterResource(id = R.drawable.diamantesk),
        "picasa" to painterResource(id = R.drawable.picasa),
        "picas2" to painterResource(id = R.drawable.picas2),
        "picas3" to painterResource(id = R.drawable.picas3),
        "picas4" to painterResource(id = R.drawable.picas4),
        "picas5" to painterResource(id = R.drawable.picas5),
        "picas6" to painterResource(id = R.drawable.picas6),
        "picas7" to painterResource(id = R.drawable.picas7),
        "picas8" to painterResource(id = R.drawable.picas8),
        "picas9" to painterResource(id = R.drawable.picas9),
        "picas10" to painterResource(id = R.drawable.picas10),
        "picasj" to painterResource(id = R.drawable.picasj),
        "picasq" to painterResource(id = R.drawable.picasq),
        "picasak" to painterResource(id = R.drawable.picask),
        "trebolesa" to painterResource(id = R.drawable.trebolesa),
        "treboles2" to painterResource(id = R.drawable.treboles2),
        "treboles3" to painterResource(id = R.drawable.treboles3),
        "treboles4" to painterResource(id = R.drawable.treboles4),
        "treboles5" to painterResource(id = R.drawable.treboles5),
        "treboles6" to painterResource(id = R.drawable.treboles6),
        "treboles7" to painterResource(id = R.drawable.treboles7),
        "treboles8" to painterResource(id = R.drawable.treboles8),
        "treboles9" to painterResource(id = R.drawable.treboles9),
        "treboles10" to painterResource(id = R.drawable.treboles10),
        "trebolesj" to painterResource(id = R.drawable.trebolesj),
        "trebolesq" to painterResource(id = R.drawable.trebolesq),
        "trebolesk" to painterResource(id = R.drawable.trebolesk)

    )

     */


@Composable
fun PartidaBlackjack(juegoViewModel: JuegoViewModel) {
    val imagen = painterResource(id = R.drawable.tapete)
    val turnoActual by juegoViewModel.turnoActual.observeAsState()
    val jugadores by juegoViewModel.jugadores.observeAsState()
    val ganador by juegoViewModel.ganador.observeAsState()
    val partidaEnCurso = remember { mutableStateOf(true)}

    // Agrega dos jugadores y inicia la partida
    LaunchedEffect(Unit) {
        juegoViewModel.agregarJugador(Jugador("Jugador1"))
        juegoViewModel.agregarJugador(Jugador("Jugador2"))
        juegoViewModel.iniciarPartida()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = imagen,
            contentDescription = "Fondo de pantalla",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        turnoActual?.let { jugador ->
            Text(
                text = "Turno Jugador: ${jugador.nombre}",
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { turnoActual?.let { juegoViewModel.pedirCarta(it) } },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                border = BorderStroke(2.dp, Color.White),
                modifier = Modifier
                    .sizeIn(minWidth = 200.dp, minHeight = 50.dp)
                    .padding(8.dp)
            ) {
                Text("Pedir Carta")
            }

            Button(
                onClick = {
                    juegoViewModel.pasarTurno()
                    if (ganador != null) {
                        partidaEnCurso.value = false
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                border = BorderStroke(2.dp, Color.White),
                modifier = Modifier
                    .sizeIn(minWidth = 200.dp, minHeight = 50.dp)
                    .padding(8.dp)
            ) {
                Text("Plantarse")
            }

            if (!partidaEnCurso.value) {
                Button(
                    onClick = { juegoViewModel.reiniciarPartida() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                    border = BorderStroke(2.dp, Color.White),
                    modifier = Modifier
                        .sizeIn(minWidth = 200.dp, minHeight = 50.dp)
                        .padding(8.dp)
                ) {
                    Text("Reiniciar Partida")
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PartidaBlackjackPreview() {
    val juegoViewModel = JuegoViewModel(mapOf("recurso1" to R.drawable.recurso1, "recurso2" to R.drawable.recurso2), mapOf("recurso1" to R.drawable.recurso1, "recurso2" to R.drawable.recurso2))
    juegoViewModel.agregarJugador(Jugador("Jugador1"))
    juegoViewModel.agregarJugador(Jugador("Jugador2"))
    PartidaBlackjack(juegoViewModel)
}