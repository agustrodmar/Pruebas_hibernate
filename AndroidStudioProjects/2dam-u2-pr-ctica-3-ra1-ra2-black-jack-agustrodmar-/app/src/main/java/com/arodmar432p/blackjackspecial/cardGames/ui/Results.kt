package com.arodmar432p.blackjackspecial.cardGames.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.sp
import com.arodmar432p.blackjackspecial.R

@Composable
fun ResultsScreen(gameViewModel: BlackjackGameViewModel) {
    val player1Wins by gameViewModel.player1Wins.observeAsState(initial = 0)
    val player2Wins by gameViewModel.player2Wins.observeAsState(initial = 0)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.tapete),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Resultados", color = Color.White, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Victorias del Jugador 1: $player1Wins", color = Color.White)
        Text(text = "Victorias del Jugador 2: $player2Wins", color = Color.White)
    }
}