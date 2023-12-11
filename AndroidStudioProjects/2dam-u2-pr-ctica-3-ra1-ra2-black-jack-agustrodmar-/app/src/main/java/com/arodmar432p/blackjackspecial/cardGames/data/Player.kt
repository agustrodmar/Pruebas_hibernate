package com.arodmar432p.blackjackspecial.cardGames.data

data class Player(
    val name: String,
    var hand: MutableList<Card> = mutableListOf(),
    var points: Int
)
