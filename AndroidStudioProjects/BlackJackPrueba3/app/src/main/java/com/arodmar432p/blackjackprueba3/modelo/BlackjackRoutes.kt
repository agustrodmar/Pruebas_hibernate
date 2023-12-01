package com.arodmar432p.blackjackprueba3.modelo

sealed class BlackjackRoutes(val route: String) {
    object MainMenu : BlackjackRoutes("MainMenu")
    object PartidaBlackjack : BlackjackRoutes("PartidaBlackjack")
}
