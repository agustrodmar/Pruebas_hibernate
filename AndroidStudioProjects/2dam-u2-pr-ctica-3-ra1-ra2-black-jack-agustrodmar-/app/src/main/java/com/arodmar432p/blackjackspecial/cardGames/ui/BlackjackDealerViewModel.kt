package com.arodmar432p.blackjackspecial.cardGames.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arodmar432p.blackjackspecial.R
import com.arodmar432p.blackjackspecial.cardGames.data.Card
import com.arodmar432p.blackjackspecial.cardGames.data.Deck
import com.arodmar432p.blackjackspecial.cardGames.data.Player
import com.arodmar432p.blackjackspecial.cardGames.data.Rank

class BlackjackDealerViewModel : ViewModel() {
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
    private val player = Player("Player", mutableListOf(), 0)
    private val dealer = Player("Dealer", mutableListOf(), 0)
    val winner = MutableLiveData<String>()
    val playerPoints = MutableLiveData<Int>()
    val dealerPoints = MutableLiveData<Int>()
    val playerHand = MutableLiveData<List<Card>>()
    val dealerHand = MutableLiveData<List<Card>>()
    val gameInProgress = MutableLiveData<Boolean>()
    val isGameOver = MutableLiveData<Boolean>(false)
    val gameReset = MutableLiveData<Boolean>(false)

    fun startGame() {
        deck.shuffle()
        player.hand.clear()
        dealer.hand.clear()
        player.hand.add(deck.getCard())
        dealer.hand.add(deck.getCard())
        player.hand.add(deck.getCard())
        dealer.hand.add(deck.getCard())
        playerHand.value = player.hand
        dealerHand.value = dealer.hand
        gameInProgress.value = true
        calculatePoints()
    }



    fun playerTurn() {
        if (deck.hasCards()) {
            player.hand.add(deck.getCard())
            playerHand.value = player.hand
            calculatePoints()
        }
    }

    fun dealerTurn() {
        while (dealer.points < 17 && deck.hasCards()) {
            dealer.hand.add(deck.getCard())
            dealerHand.value = dealer.hand
            calculatePoints()
        }
        endTurn()
    }

    fun stand() {
        dealerTurn()
    }

    private fun calculatePoints(player: Player): Int {
        var total = 0
        var aces = 0

        for (card in player.hand) {
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

    private fun calculatePoints() {
        player.points = calculatePoints(player)
        dealer.points = calculatePoints(dealer)
        playerPoints.value = player.points
        dealerPoints.value = dealer.points
    }

    private fun endTurn() {
        when {
            player.points > 21 -> winner.value = "Dealer"
            dealer.points > 21 -> winner.value = "Player"
            player.points > dealer.points -> winner.value = "Player"
            dealer.points > player.points -> winner.value = "Dealer"
            else -> winner.value = "Draw"
        }
        isGameOver.value = true
    }

    fun closeDialog() {
        isGameOver.value = false
        gameReset.value = true
    }
}