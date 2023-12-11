package com.arodmar432p.blackjackspecial.cardGames.data

import androidx.annotation.DrawableRes


data class Card(
    val rank: Rank,
    val suit: Suit,
    val minPoints: Int,
    val maxPoints: Int,
    @DrawableRes val idDrawable: Int?
)