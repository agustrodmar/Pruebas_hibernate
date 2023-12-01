package com.arodmar432p.blackjackprueba3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arodmar432p.blackjackprueba3.ui.theme.BlackJackPrueba3Theme
import com.arodmar432p.blackjackprueba3.vista.MainMenu
import com.arodmar432p.blackjackprueba3.modelo.BlackjackRoutes
import com.arodmar432p.blackjackprueba3.vista.PartidaBlackjack
import com.arodmar432p.blackjackprueba3.vistaModelo.JuegoViewModel

class MainActivity : ComponentActivity() {

    private val juegoViewModel: JuegoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            BlackJackPrueba3Theme {
                NavHost(navController = navController, startDestination = BlackjackRoutes.MainMenu.route) {
                    composable(BlackjackRoutes.MainMenu.route) { MainMenu(navController) }
                }
                NavHost(navController = navController, startDestination = BlackjackRoutes.PartidaBlackjack.route) {
                    composable(BlackjackRoutes.PartidaBlackjack.route) { PartidaBlackjack(juegoViewModel) }

                }
            }

        }
    }
}