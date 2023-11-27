package com.arodmar432p.blackjackpractica3.vistaModelo

import com.arodmar432p.blackjackpractica3.modelo.Baraja
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arodmar432p.blackjackpractica3.modelo.Carta
import com.arodmar432p.blackjackpractica3.modelo.Jugador
import com.arodmar432p.blackjackpractica3.modelo.Naipe

class JuegoViewModel(private val recursos: Map<String, Int>, private val recursosBocaAbajo: Map<String, Int>) : ViewModel() {
    private val baraja = Baraja(recursos, recursosBocaAbajo)
    private val _jugadores = MutableLiveData<List<Jugador>>(emptyList())
    val jugadores: LiveData<List<Jugador>> get() = _jugadores
    private val _ganador = MutableLiveData<Jugador?>()
    val ganador: LiveData<Jugador?> get() = _ganador
    private val _turnoActual = MutableLiveData<Jugador>()
    val turnoActual: LiveData<Jugador> get() = _turnoActual // Exponer turnoActual como una propiedad pública
    private var partidaEnCurso: Boolean = false

    init {
        baraja.barajar()
        _turnoActual.value = _jugadores.value?.first()
    }

    fun agregarJugador(jugador: Jugador) {
        _jugadores.value = _jugadores.value?.plus(jugador)
    }

    fun iniciarPartida() {
        // Reparte dos cartas a cada jugador al inicio de la partida
        repartirCartas(2)

        // Comprueba si alguno de los jugadores tiene Blackjack
        for (jugador in _jugadores.value!!) {
            if (calcularPuntos(jugador.mano) == 21) {
                _ganador.value = jugador
                return
            }
        }
    }

    fun repartirCartas(numCartas: Int) {
        for (i in 0 until numCartas) {
            for (jugador in _jugadores.value!!) {
                val carta = baraja.dameCarta()
                carta.estaBocaAbajo = true  // Las cartas se reparten boca abajo
                jugador.mano.add(carta)
            }
        }
    }

    fun turnoJugador(nombre: String): Jugador? {
        return _jugadores.value?.find { it.nombre == nombre }
    }

    fun pasarTurno() {
        val jugadorActual = _turnoActual.value
        val proximoJugador = _jugadores.value?.let { jugadores ->
            val indiceActual = jugadores.indexOf(jugadorActual)
            val proximoIndice = (indiceActual + 1) % jugadores.size
            jugadores[proximoIndice]
        }
        _turnoActual.value = proximoJugador

        // Comprueba si ambos jugadores han tenido su turno
        if (_turnoActual.value == _jugadores.value?.first()) {
            // Comprueba quién está más cerca de 21 sin pasarse
            val puntosJugadorActual = calcularPuntos(jugadorActual!!.mano)
            val puntosProximoJugador = calcularPuntos(proximoJugador!!.mano)
            _ganador.value = when {
                puntosJugadorActual > 21 -> proximoJugador
                puntosProximoJugador > 21 -> jugadorActual
                puntosJugadorActual > puntosProximoJugador -> jugadorActual
                else -> proximoJugador
            }
            partidaEnCurso = false
        }
    }


    fun pedirCarta(jugador: Jugador) {
        val carta = baraja.dameCarta()
        carta.estaBocaAbajo = false  // Las cartas que se piden se muestran boca arriba
        jugador.mano.add(carta)

        // Comprueba si el jugador se ha pasado de 21
        if (calcularPuntos(jugador.mano) > 21) {
            // Si el jugador se pasa de 21, el otro jugador gana
            _ganador.value = _jugadores.value?.first { it != jugador }
        }
    }

    fun calcularPuntos(mano: List<Carta>): Int {
        var total = 0
        var ases = 0

        // Suma los puntos de todas las cartas
        for (carta in mano) {
            total += if (carta.valor != Naipe.AS) {
                carta.puntosMax
            } else {
                ases++
                carta.puntosMax // Temporalmente cuenta los ases como 11
            }
        }

        // Si el total es mayor que 21 y hay ases en la mano, trata de reducir el total restándole 10 por cada as
        while (total > 21 && ases > 0) {
            total -= 10
            ases--
        }

        return total
    }

    fun reiniciarPartida() {
        _jugadores.value = emptyList()
        _ganador.value = null
        partidaEnCurso = true
        iniciarPartida()
    }

}