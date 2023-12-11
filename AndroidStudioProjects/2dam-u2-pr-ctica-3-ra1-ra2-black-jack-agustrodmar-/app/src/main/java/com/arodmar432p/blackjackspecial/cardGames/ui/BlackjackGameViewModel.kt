package com.arodmar432p.blackjackspecial.cardGames.ui

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arodmar432p.blackjackspecial.R
import com.arodmar432p.blackjackspecial.cardGames.data.Card
import com.arodmar432p.blackjackspecial.cardGames.data.Deck
import com.arodmar432p.blackjackspecial.cardGames.data.Player
import com.arodmar432p.blackjackspecial.cardGames.data.Rank

class BlackjackGameViewModel : ViewModel() {
    private val deck = Deck(cardImageMap = mapOf("corazonesa" to  R.drawable.corazonesa,
        "corazones2" to  R.drawable.corazones2,
        "corazones3" to  R.drawable.corazones3,
        "corazones4" to R.drawable.corazones4,
        "corazones5" to  R.drawable.corazones5,
        "corazones6" to R.drawable.corazones6,
        "corazones7" to  R.drawable.corazones7,
        "corazones8" to  R.drawable.corazones8,
        "corazones9" to R.drawable.corazones9,
        "corazones10" to R.drawable.corazones10,
        "corazonesj" to R.drawable.corazonesj,
        "corazonesq" to R.drawable.corazonesq,
        "corazonesk" to R.drawable.corazonesk,
        "diamantesa" to R.drawable.diamantesa,
        "diamantes2" to  R.drawable.diamantes2,
        "diamantes3" to  R.drawable.diamantes3,
        "diamantes4" to  R.drawable.diamantes4,
        "diamantes5" to  R.drawable.diamantes5,
        "diamantes6" to R.drawable.diamantes6,
        "diamantes7" to  R.drawable.diamantes7,
        "diamantes8" to  R.drawable.diamantes8,
        "diamantes9" to  R.drawable.diamantes9,
        "diamantes10" to  R.drawable.diamantes10,
        "diamantesj" to  R.drawable.diamantesj,
        "diamantesq" to  R.drawable.diamantesq,
        "diamantesk" to  R.drawable.diamantesk,
        "picasa" to  R.drawable.picasa,
        "picas2" to  R.drawable.picas2,
        "picas3" to  R.drawable.picas3,
        "picas4" to R.drawable.picas4,
        "picas5" to  R.drawable.picas5,
        "picas6" to  R.drawable.picas6,
        "picas7" to  R.drawable.picas7,
        "picas8" to  R.drawable.picas8,
        "picas9" to  R.drawable.picas9,
        "picas10" to  R.drawable.picas10,
        "picasj" to  R.drawable.picasj,
        "picasq" to  R.drawable.picasq,
        "picask" to  R.drawable.picask,
        "trebolesa" to  R.drawable.trebolesa,
        "treboles2" to  R.drawable.treboles2,
        "treboles3" to  R.drawable.treboles3,
        "treboles4" to  R.drawable.treboles4,
        "treboles5" to  R.drawable.treboles5,
        "treboles6" to  R.drawable.treboles6,
        "treboles7" to  R.drawable.treboles7,
        "treboles8" to  R.drawable.treboles8,
        "treboles9" to  R.drawable.treboles9,
        "treboles10" to  R.drawable.treboles10,
        "trebolesj" to  R.drawable.trebolesj,
        "trebolesq" to  R.drawable.trebolesq,
        "trebolesk" to R.drawable.trebolesk,
    ))
    private val _players = MutableLiveData<List<Player>>(emptyList())
    val players: LiveData<List<Player>> get() = _players
    private val _winner = MutableLiveData<Player?>()
    val winner: LiveData<Player?> get() = _winner
    private val _currentTurn = MutableLiveData<Player>()
    val currentTurn: LiveData<Player> get() = _currentTurn
    private val _gameInProgress = MutableLiveData<Boolean>()
    val gameInProgress: LiveData<Boolean> get() = _gameInProgress

    private val _showDialog = MutableLiveData<Boolean>() // Para declarar el ganador
    val showDialog: LiveData<Boolean> get() = _showDialog

    private val _player1Wins = MutableLiveData<Int>(0)

    // Para manejar mi pantalla de Resultados
    val player1Wins: LiveData<Int> get() = _player1Wins

    private val _player2Wins = MutableLiveData<Int>(0)
    val player2Wins: LiveData<Int> get() = _player2Wins

    private val _eventCloseApp = MutableLiveData<Boolean>()
    val eventCloseApp: LiveData<Boolean> get() = _eventCloseApp




    init {
        _gameInProgress.value = false
        val player1 = Player("Player 1", mutableListOf(), 0)
        val player2 = Player("Player 2", mutableListOf(), 0)
        _players.value = listOf(player1, player2)
        _currentTurn.value = player1
        checkForBlackjack()
        _showDialog.value = false

    }

    fun startGame() {
        _winner.value = null
        _showDialog.value = false
        restartGame()
        deck.shuffle()
        startDeal(2)
        _gameInProgress.value = true
    }
    private fun endGame(winner: Player?) {
        if (winner != null) {
            _winner.value = winner
            _showDialog.value = true
            if (winner.name == "Player 1") {
                _player1Wins.value = (_player1Wins.value ?: 0) + 1
            } else if (winner.name == "Player 2") {
                _player2Wins.value = (_player2Wins.value ?: 0) + 1
            }
        } else {
            _winner.value = null
            _showDialog.value = true
        }
    }

    fun closeDialog() {
        _showDialog.value = false
        _gameInProgress.value = false
    }

    fun startDeal(numCards: Int) {
        for (i in 0 until numCards) {
            for (player in _players.value!!) {
                player.hand.add(deck.getCard())
            }
        }
    }

    fun hitMe(player: Player) {
        Log.d("BlackjackGameViewModel", "Hit Me button pressed for ${player.name}")
        if (_currentTurn.value == player) {
            requestCard(player)
            if (_gameInProgress.value == true) {
                passTurn()
            }
        }
    }

    fun pass(player: Player) {
        if (_currentTurn.value == player) {
            passTurn()
        }
    }

    private fun requestCard(player: Player) {
        player.hand.add(deck.getCard())

        if (calculatePoints(player.hand) > 21) {
            endGame(_players.value?.first { it != player }!!)
        }
    }

    private fun passTurn() {
        val currentPlayer = _currentTurn.value
        val nextPlayer = _players.value?.let { players ->
            val currentIndex = players.indexOf(currentPlayer)
            val nextIndex = (currentIndex + 1) % players.size
            players[nextIndex]
        }
        _currentTurn.value = nextPlayer

        if (_currentTurn.value == _players.value?.first()) {
            val currentPlayerPoints = calculatePoints(currentPlayer!!.hand)
            val nextPlayerPoints = calculatePoints(nextPlayer!!.hand)
            val winner = when {
                currentPlayerPoints > 21 -> nextPlayer
                nextPlayerPoints > 21 -> currentPlayer
                currentPlayerPoints > nextPlayerPoints -> currentPlayer
                currentPlayerPoints < nextPlayerPoints -> nextPlayer
                else -> null  // Con esto pienso manejar el empate
            }
            endGame(winner)
        }
    }


    fun calculatePoints(hand: List<Card>): Int {
        var total = 0
        var aces = 0

        for (card in hand) {
            total += if (card.rank != Rank.ACE) {
                if (card.rank.ordinal >= Rank.JACK.ordinal) 10 else card.rank.ordinal + 1
            } else {
                aces++
                card.maxPoints
            }
        }

        while (total > 21 && aces > 0) {
            total -= 10
            aces--
        }

        return total
    }

    fun checkForBlackjack() {
        for (player in _players.value!!) {
            if (calculatePoints(player.hand) == 21) {
                _winner.value = player
                _showDialog.value = true
                return
            }
        }
    }

    fun restartGame() {
        for (player in _players.value!!) {
            player.hand.clear()
        }
        _currentTurn.value = _players.value?.first()
        _gameInProgress.value = false
    }

    fun closeApp() {
        _eventCloseApp.value = true
    }

    fun onAppClosed() {
        _eventCloseApp.value = false
    }
}

