package com.arodmar432p.blackjackspecial.cardGames.ui


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arodmar432p.blackjackspecial.R
import com.arodmar432p.blackjackspecial.cardGames.data.Player

@Composable
fun BlackjackScreen(gameViewModel: BlackjackGameViewModel) {
    val backgroundImage = painterResource(id = R.drawable.tapete)
    val players by gameViewModel.players.observeAsState(initial = emptyList())
    val winner by gameViewModel.winner.observeAsState()
    val gameInProgress by gameViewModel.gameInProgress.observeAsState(initial = false)
    val currentTurn by gameViewModel.currentTurn.observeAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = backgroundImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Muestra el turno actual
            currentTurn?.let {
                Text(text = "Turno: ${it.name}", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (gameInProgress) {
                players.forEachIndexed { index, player ->
                    currentTurn?.let { PlayerCard(player, gameViewModel) }
                }

            } else {
                winner?.let { winner ->
                    Text(text = "Winner is: ${winner.name}", color = Color.White)
                }
                Button(
                    onClick = { gameViewModel.startGame() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                    border = BorderStroke(2.dp, Color.White)
                ) {
                    Text(text = "Start Game", color = Color.Black)
                }
            }
        }
    }
}


@Composable
fun PlayerCard(player: Player, gameViewModel: BlackjackGameViewModel) {
    val winner by gameViewModel.winner.observeAsState()

    if (winner != null) {
        AlertDialog(
            onDismissRequest = { gameViewModel.closeDialog() },
            title = { Text(text = "Game Over") },
            text = { Text(text = "Winner is: ${winner!!.name}") },
            confirmButton = {
                Button(onClick = { gameViewModel.closeDialog() }) {
                    Text("Aceptar")
                }
            }
        )
    } else if (gameViewModel.showDialog.value == true) {
        AlertDialog(
            onDismissRequest = { gameViewModel.closeDialog() },
            title = { Text(text = "Game Over") },
            text = { Text(text = "El resultado es de empate") },
            confirmButton = {
                Button(onClick = { gameViewModel.closeDialog() }) {
                    Text("Aceptar")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "${player.name}", color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))

        // Debe mostrar los puntos solo cuando el jugador tenga el turno
        if (gameViewModel.currentTurn.value == player) {
            Text(text = "Points: ${gameViewModel.calculatePoints(player.hand)}", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Muestra las cartas del jugador
        Box(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            player.hand.forEachIndexed { cardIndex, card ->
                val isGameOver = gameViewModel.winner.value != null || gameViewModel.showDialog.value == true
                val shouldHideCard = gameViewModel.currentTurn.value != player && cardIndex != 0 && !isGameOver
                val cardResource = if (shouldHideCard) {
                    R.drawable.bocabajo
                } else {
                    card.idDrawable
                }
                Image(
                    painterResource(id = cardResource!!),
                    contentDescription = null,
                    modifier = Modifier
                        .height(150.dp)
                        .width(75.dp)
                        .offset(x = (cardIndex * 15).dp, y = (cardIndex * 20).dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { gameViewModel.hitMe(player) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                border = BorderStroke(2.dp, Color.White)
            ) {
                Text(text = "Hit Me", color = Color.Black)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { gameViewModel.pass(player) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                border = BorderStroke(2.dp, Color.White)
            ) {
                Text(text = "Pass", color = Color.Black)
            }
        }
    }
}

