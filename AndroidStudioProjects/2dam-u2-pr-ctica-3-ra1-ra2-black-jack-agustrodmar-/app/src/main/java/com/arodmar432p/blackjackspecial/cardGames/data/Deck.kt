package com.arodmar432p.blackjackspecial.cardGames.data


class Deck(private val cardImageMap: Map<String, Int>) {
    companion object {
        fun createDeck(cardImageMap: Map<String, Int>): ArrayList<Card> {
            val cardsList = ArrayList<Card>()
            for (suit in Suit.values()) {
                for (rank in Rank.values()) {
                    val minPoints = if (rank == Rank.ACE) 1 else if (rank.ordinal > 10) 10 else rank.ordinal
                    val maxPoints = if (rank == Rank.ACE) 11 else if (rank.ordinal > 10) 10 else rank.ordinal

                    val idDrawableName = when (suit) {
                        Suit.HEARTS -> "corazones"
                        Suit.DIAMONDS -> "diamantes"
                        Suit.CLUBS -> "treboles"
                        Suit.SPADES -> "picas"
                    } + when (rank) {
                        Rank.ACE -> "a"
                        Rank.TWO -> "2"
                        Rank.THREE -> "3"
                        Rank.FOUR -> "4"
                        Rank.FIVE -> "5"
                        Rank.SIX -> "6"
                        Rank.SEVEN -> "7"
                        Rank.EIGHT -> "8"
                        Rank.NINE -> "9"
                        Rank.TEN -> "10"
                        Rank.JACK -> "j"
                        Rank.QUEEN -> "q"
                        Rank.KING -> "k"
                    }

                    val idDrawable = cardImageMap[idDrawableName]
                    if (idDrawable != null) {
                        cardsList.add(Card(rank, suit, minPoints, maxPoints, idDrawable))
                    }
                }
            }
            return cardsList
        }
    }

    private val cardsList = createDeck(cardImageMap)


    fun shuffle() {
        cardsList.shuffle()
    }

    fun hasCards(): Boolean {
        return cardsList.isNotEmpty()
    }


    fun getCard(): Card {
        if (cardsList.isEmpty()) {
            throw IllegalStateException("Deck is empty!")
        }
        return cardsList.removeAt(cardsList.size - 1)
    }
}

