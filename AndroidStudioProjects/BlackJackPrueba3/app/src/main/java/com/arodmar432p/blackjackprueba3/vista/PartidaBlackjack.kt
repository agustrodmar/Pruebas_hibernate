package com.arodmar432p.blackjackprueba3.vista


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.arodmar432p.blackjackprueba3.vistaModelo.JuegoViewModel


@Composable
fun PartidaBlackjack(juegoViewModel: JuegoViewModel) {
    val jugadores by juegoViewModel.jugadores.observeAsState(emptyList())
    val ganador by juegoViewModel.ganador.observeAsState(null)
    val turnoActual by juegoViewModel.turnoActual.observeAsState(null)

    Column {
        Text(text = "Blackjack")
        jugadores.forEach { jugador ->
            Text(text = "Jugador: ${jugador.nombre}")
            jugador.mano.forEach { carta ->
                Text(text = "Carta: ${carta.valor} de ${carta.palo}")
            }
        }
        ganador?.let {
            Text(text = "Ganador: ${it.nombre}")
        }
        turnoActual?.let {
            Text(text = "Turno actual: ${it.nombre}")
        }
        Button(onClick = { juegoViewModel.iniciarPartida() }) {
            Text(text = "Iniciar partida")
        }
        Button(onClick = { juegoViewModel.reiniciarPartida() }) {
            Text(text = "Reiniciar partida")
        }
    }
}
