package com.arodmar432p.blackjackspecial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arodmar432p.blackjackspecial.cardGames.ui.BlackjackGameViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.BlackjackScreen
import com.arodmar432p.blackjackspecial.cardGames.ui.MainMenu
import com.arodmar432p.blackjackspecial.cardGames.data.BlackjackRoutes
import com.arodmar432p.blackjackspecial.cardGames.ui.BlackjackDealerScreen
import com.arodmar432p.blackjackspecial.cardGames.ui.BlackjackDealerViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.ResultsScreen
import com.arodmar432p.blackjackspecial.ui.theme.BlackjackSpecialTheme




class MainActivity : ComponentActivity() {
    private val vsGameViewModel: BlackjackGameViewModel by viewModels()

    private val dealerGameViewModel: BlackjackDealerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            BlackjackSpecialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = BlackjackRoutes.MainMenuScreen.route
                    ) {
                        composable(BlackjackRoutes.MainMenuScreen.route) {
                            MainMenu(navController = navController, gameViewModel = vsGameViewModel)
                        }
                        composable(BlackjackRoutes.BlackjackScreen.route) {
                            BlackjackScreen(gameViewModel = vsGameViewModel)
                        }
                        composable(BlackjackRoutes.BlackjackDealerScreen.route) {
                            BlackjackDealerScreen(blackjackDealerViewModel = dealerGameViewModel)
                        }
                        composable(BlackjackRoutes.ResultsScreen.route) {
                            ResultsScreen(gameViewModel = vsGameViewModel)
                        }
                    }
                }
            }
        }

        vsGameViewModel.eventCloseApp.observe(this) { event ->
            if (event) {
                finish()
                vsGameViewModel.onAppClosed()
            }
        }

    }
}
