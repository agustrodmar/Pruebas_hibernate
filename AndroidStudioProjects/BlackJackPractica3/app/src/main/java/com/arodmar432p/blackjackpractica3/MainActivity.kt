package com.arodmar432p.blackjackpractica3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arodmar432p.blackjackpractica3.modelo.BlackjackRoutes
import com.arodmar432p.blackjackpractica3.ui.theme.BlackJackPractica3Theme
import com.arodmar432p.blackjackpractica3.vista.MainMenu

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            BlackJackPractica3Theme {
                NavHost(navController = navController, startDestination = BlackjackRoutes.MainMenu.route) {
                    composable(BlackjackRoutes.MainMenu.route) { MainMenu(navController) }
                }
            }
        }
    }
}