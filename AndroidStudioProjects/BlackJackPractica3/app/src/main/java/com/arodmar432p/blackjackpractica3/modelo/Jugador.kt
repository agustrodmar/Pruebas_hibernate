package com.arodmar432p.blackjackpractica3.modelo

data class Jugador(
    val nombre: String,
    val mano: MutableList<Carta> = mutableListOf()
)
