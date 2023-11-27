package com.arodmar432p.blackjackpractica3.modelo

data class Carta(
    val valor: Naipe,
    val palo: Palo,
    val imagen: Int,
    val puntosMax: Int,
    val idDrawable: Int,
    val imagenBocaAbajo: Int,
    var estaBocaAbajo: Boolean = true
)