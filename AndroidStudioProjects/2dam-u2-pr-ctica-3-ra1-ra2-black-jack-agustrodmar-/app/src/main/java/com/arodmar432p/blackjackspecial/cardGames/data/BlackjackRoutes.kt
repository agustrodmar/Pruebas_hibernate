package com.arodmar432p.blackjackspecial.cardGames.data

sealed class BlackjackRoutes(val route: String) {
    object MainMenuScreen : BlackjackRoutes("mainMenuScreen")
    object BlackjackScreen : BlackjackRoutes("BlackjackScreen")
    object BlackjackDealerScreen : BlackjackRoutes("BlackjackDealerScreen")
    object ResultsScreen : BlackjackRoutes("ResultsScreen")
}