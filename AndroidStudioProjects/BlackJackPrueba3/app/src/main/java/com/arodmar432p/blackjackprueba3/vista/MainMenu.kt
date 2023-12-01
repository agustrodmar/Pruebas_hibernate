package com.arodmar432p.blackjackprueba3.vista

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arodmar432p.blackjackprueba3.R
import com.arodmar432p.blackjackprueba3.modelo.BlackjackRoutes


@Composable
fun MainMenu(navController: NavController) {
    val openDialog = remember { mutableStateOf(false) }
    val background = painterResource(id = R.drawable.tapete)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = background,
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Button(
                onClick = { openDialog.value = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                border = BorderStroke(2.dp, Color.White),
                modifier = Modifier.sizeIn(minWidth = 200.dp, minHeight = 50.dp)
            ) {
                Text("Empezar Partida", color = Color.Black)
            }

            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = { openDialog.value = false },
                    title = { Text("Elige un modo de juego", color= Color.White) },
                    text = {
                        Column {
                            Button(
                                onClick = { navController.navigate(BlackjackRoutes.PartidaBlackjack.route) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                                border = BorderStroke(2.dp, Color.White)
                            ) {
                                Text("Contra la máquina")
                            }
                            Button(
                                onClick = { /* 2 jugadores */ },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                                border = BorderStroke(2.dp, Color.White)
                            ) {
                                Text("2 jugadores")
                            }
                        }
                    },
                    confirmButton = { },
                    dismissButton = { },
                    containerColor = Color.Black
                )
            }

            Button(
                onClick = { /* Cargar Partida */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                border = BorderStroke(2.dp, Color.White),
                modifier = Modifier.sizeIn(minWidth = 200.dp, minHeight = 50.dp)
            ) {
                Text("Cargar Partida", color = Color.Black)
            }

            Button(
                onClick = { navController.navigate("Resultados") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                border = BorderStroke(2.dp, Color.White),
                modifier = Modifier.sizeIn(minWidth = 200.dp, minHeight = 50.dp)
            ) {
                Text("Resultados", color = Color.Black)
            }

            Button(
                onClick = { /* Salir */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                border = BorderStroke(2.dp, Color.White),
                modifier = Modifier.sizeIn(minWidth = 200.dp, minHeight = 50.dp)
            ) {
                Text("Salir", color = Color.Black)
            }
        }
    }
}
