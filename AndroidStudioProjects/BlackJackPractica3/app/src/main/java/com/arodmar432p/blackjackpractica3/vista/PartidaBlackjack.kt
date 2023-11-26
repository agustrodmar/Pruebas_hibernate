package com.arodmar432p.blackjackpractica3.vista

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arodmar432p.blackjackpractica3.vistaModelo.JuegoViewModel
import java.lang.reflect.Modifier
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.arodmar432p.blackjackpractica3.R
import com.arodmar432p.blackjackpractica3.modelo.Naipe
import com.arodmar432p.blackjackpractica3.modelo.Palo
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
fun PartidaBlackjack() {
    val juegoViewModel: JuegoViewModel = viewModel()
    val jugadores by juegoViewModel.jugadores.observeAsState(emptyList())
    val ganador by juegoViewModel.ganador.observeAsState(null)
    val turnoActual by juegoViewModel.turnoActual.observeAsState(null)

    val recursosBocaAbajo = painterResource(id = R.drawable.bocabajo)

    val recursos = Palo.values().flatMap { palo ->
        Naipe.values().map { naipe ->
            val nombreRecurso = "${palo.toString().lowercase()}${naipe.valor}"
            nombreRecurso to painterResource(id = R.drawable::class.java.getField(nombreRecurso).getInt(null))
        }
    }.toMap()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(painterResource("tapete"), contentDescription = null, modifier = Modifier.fillMaxSize())

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Blackjack", fontSize = 32.sp, color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))

            jugadores.forEach { jugador ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = jugador.nombre, fontSize = 24.sp, color = Color.White)

                    Spacer(modifier = Modifier.width(8.dp))

                    jugador.mano.forEach { carta ->
                        val imagen = if (turnoActual == jugador || ganador != null) {
                            painterResource(carta.imagen)
                        } else {
                            painterResource("bocabajo")
                        }
                        Image(imagen, contentDescription = null)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (ganador != null) {
                Text(text = "Ganador: ${ganador!!.nombre}", fontSize = 24.sp, color = Color.White)

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { juegoViewModel.reiniciarPartida() }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)), border = BorderStroke(2.dp, Color.White), modifier = Modifier.sizeIn(minWidth = 200.dp, minHeight = 50.dp)) {
                    Text(text = "Reiniciar partida")
                }
            } else {
                Button(onClick = { turnoActual?.let { juegoViewModel.pedirCarta(it) } }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)), border = BorderStroke(2.dp, Color.White), modifier = Modifier.sizeIn(minWidth = 200.dp, minHeight = 50.dp)) {
                    Text(text = "Pedir carta")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { juegoViewModel.pasarTurno() }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)), border = BorderStroke(2.dp, Color.White), modifier = Modifier.sizeIn(minWidth = 200.dp, minHeight = 50.dp)) {
                    Text(text = "Pasar turno")
                }
            }
        }
    }
}
