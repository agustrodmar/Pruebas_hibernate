package com.arodmar432p.blackjackpractica3.vista


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.navigation.compose.rememberNavController
import com.arodmar432p.blackjackpractica3.R
import com.arodmar432p.blackjackpractica3.modelo.BlackjackRoutes
import com.arodmar432p.blackjackpractica3.modelo.Carta
import com.arodmar432p.blackjackpractica3.modelo.Jugador
import com.arodmar432p.blackjackpractica3.ui.theme.BlackJackPractica3Theme
import com.arodmar432p.blackjackpractica3.vistaModelo.JuegoViewModel


@Composable
fun PartidaBlackjack(juegoViewModel: JuegoViewModel) {
    val imagen = painterResource(id = R.drawable.tapete)
    val turnoActual by juegoViewModel.turnoActual.observeAsState()
    val jugadores by juegoViewModel.jugadores.observeAsState()
    val ganador by juegoViewModel.ganador.observeAsState()
    val partidaEnCurso = remember { mutableStateOf(true)}

    // Agrega dos jugadores e inicia la partida
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

        jugadores?.let { listaJugadores ->
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                listaJugadores.forEach { jugador ->
                    Text(
                        text = "${jugador.nombre}: ${juegoViewModel.calcularPuntos(jugador.mano)} puntos",
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )

                    jugador.mano.forEach { carta ->
                        val imagen = if (carta.estaBocaAbajo) {
                            painterResource(id = carta.imagenBocaAbajo)
                        } else {
                            painterResource(id = carta.idDrawable)
                        }

                        Image(
                            painter = imagen,
                            contentDescription = "Imagen de la carta",
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }
            }
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
                Text(
                    text = "¡${ganador?.nombre} ha ganado la partida con ${ganador?.let {
                        juegoViewModel.calcularPuntos(
                            it.mano)
                    }} puntos!",
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )

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
    val turnoActual = remember { mutableStateOf(Jugador("Jugador1")) }
    val jugadores = remember { mutableStateOf(listOf(Jugador("Jugador1"), Jugador("Jugador2"))) }
    val ganador = remember { mutableStateOf<Jugador?>(null) }
    val partidaEnCurso = remember { mutableStateOf(true) }

    PartidaBlackjack(turnoActual, jugadores, ganador, partidaEnCurso)
}