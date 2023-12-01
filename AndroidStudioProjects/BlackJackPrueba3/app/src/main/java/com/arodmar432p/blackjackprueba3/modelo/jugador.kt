package com.arodmar432p.blackjackprueba3.modelo

data class Jugador(
    val nombre: String,
    val mano: MutableList<Carta> = mutableListOf()
)
