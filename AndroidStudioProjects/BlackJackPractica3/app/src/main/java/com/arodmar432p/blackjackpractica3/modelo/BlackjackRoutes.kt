package com.arodmar432p.blackjackpractica3.modelo

sealed class BlackjackRoutes(val route: String) {
    object MainMenu : BlackjackRoutes("MainMenu")
    object PartidaBlackJack : BlackjackRoutes("PartidaBlackJack")
}
