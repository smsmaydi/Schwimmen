package service
import entity.*

/**
 * Hier wird das Logik von dem Spiel erstellt
 */
class GameService(private val rootService: RootService): AbstractRefreshingService(){


    /**
     * Hier wird es kontroliert, ob in der Stapel noch drei Karten gibt.
     */
    fun areThereAnyThreeCardsToDraw():Boolean{
        val game = rootService.currentGame

        check(game!=null){
            "game must be startet"
        }
        return game.drawPile.size >= 3
    }

    /**
     * Erste drei Karten aus dem Stapel kopiert und danach wird die erste drei Karten im Stapel gelöscht.
     * @return Sie gibt eine Liste aus Card zurück
     */
    private fun getFirstThreeCardsInList():MutableList<Card> {
        val game = rootService.currentGame

        val allCards = mutableListOf<Card>()
        if (game != null) {

            allCards.add(game.drawPile[0])
            allCards.add(game.drawPile[1])
            allCards.add(game.drawPile[2])


            for(card in allCards){
                game.drawPile.remove(card)
            }

        }
        return allCards
    }

    /**
     * Startet ein neues Spiel.
     * Es hängt davon ab, wie viele Spieler es gibt.
     * @param playerNames Spielernamen
     * @param allCards eine Liste aus alle Karten
     */
        fun startGame(playerNames:List<String>,allCards: MutableList<Card> = defaultRandomCardList() as MutableList<Card>){
            check(playerNames.size in 2..4){"2 to 4 Player must be there"}
            val players = mutableListOf<Player>()
            for(playerName in playerNames){
                players.add(Player(playerName, getFirstThreeCardsInList() ))
            }
            var newGame = Game(
                Player(playerNames[0], getFirstThreeCardsInList() ),
                Player(playerNames[1], getFirstThreeCardsInList() ),
                Player(null, mutableListOf()),
                Player(null, mutableListOf())
            )
            if(playerNames.size == 2){
                newGame = Game(
                    Player(playerNames[0], getFirstThreeCardsInList() ),
                    Player(playerNames[1], getFirstThreeCardsInList()),null,null
                )
            }
            if(playerNames.size == 3){
                newGame = Game(
                    Player(playerNames[0], getFirstThreeCardsInList() ),
                    Player(playerNames[1], getFirstThreeCardsInList() ),
                    Player(playerNames[2], getFirstThreeCardsInList() ),null

                )
            }
            if(playerNames.size == 4){
                newGame = Game(
                    Player(playerNames[0], getFirstThreeCardsInList() ),
                    Player(playerNames[1], getFirstThreeCardsInList() ),
                    Player(playerNames[2], getFirstThreeCardsInList() ),
                    Player(playerNames[3], getFirstThreeCardsInList() )
                )
            }

        rootService.currentGame = newGame
            newGame.drawPile = allCards
            println("${newGame.drawPile.size}")
            newGame.middleCards = getFirstThreeCardsInList()
            for(player in newGame.players){

                player.hand = getFirstThreeCardsInList()
            }



            onAllRefreshables{refreshAfterStartNewGame()
            }
        }

    /**
     * Es wird hier Randomlist von Karten erstellt.
     */
    private fun defaultRandomCardList() = List(32) { index ->
        Card(
            CardType.values()[index / 8],
            CardValue.values()[(index % 8) + 5]
        )
    }.shuffled()


    /**
     * Es wird drei Karten aus dem Stapel gezogen
     * Aus 'drawPile' zur middleCards wird erste drei Karten hinzugefügt und erste drei Karten im drawPile gelöscht.
     */
    fun changeMiddleCards(){
        val game = rootService.currentGame

        game!!.middleCards[0] = game.drawPile[0]
        game.middleCards[1] = game.drawPile[1]
        game.middleCards[2] = game.drawPile[2]
        game.drawPile.removeFirst()
        game.drawPile.removeFirst()
        game.drawPile.removeFirst()
        onAllRefreshables { refreshAfterSwappingCards() }
    }

    /**
     * Die Funktion gibt zurück, wie viel Punkte die Kartenzahl haben.
     * @param aCard eine Karte
     * @return Double, die Punkte
     */
    fun getCardValue( aCard:CardValue):Double{

        val value:Double = when(aCard){
            CardValue.ACE -> 11.0
            CardValue.KING -> 10.0
            CardValue.QUEEN -> 10.0
            CardValue.JACK -> 10.0
            CardValue.TEN -> 10.0
            CardValue.NINE -> 9.0
            CardValue.EIGHT -> 8.0
            CardValue.SEVEN -> 7.0
            else -> 0.0
        }
        return value
    }

    /**
     * Diese Funktion berechnet die Karten in der Hand.
     * s1-s5 sind die verschiedene Situationen. Wenn eine davon true ist, wird die Funktion die Karten berechnen
     * Bei 'else' sind die Kartentypen und die Kartenzahl unterschiedlich und vergleicht sie miteinander. Danach
     * gibt die größte Zahl zurück.
     * @param handCards die Karten in der Hand.
     * @return Double wie viel man in der Hand hat.
     */
    fun handCardsCalculator(handCards:MutableList<Card>): Double {
        val firstCard: Card = handCards[0]
        val secondCard: Card = handCards[1]
        val thirdCard: Card = handCards[2]

        /**
         * s1 = Die drei Karten haben gleiche value
         * s2 = Alle Kartentypen sind gleich
         * s3 = Erste und zweite sind gleich
         * s4 = zweite und dritte sind gleich
         * s5 = erste und dritte sind gleich
         */
        val s1:Boolean = (firstCard.value == secondCard.value) && (firstCard.value == thirdCard.value)
        val s2:Boolean = (firstCard.type == secondCard.type) && (firstCard.type == thirdCard.type)
        val s3:Boolean = (firstCard.type == secondCard.type) && (secondCard.type != thirdCard.type)
        val s4:Boolean = (firstCard.type != secondCard.type) && (secondCard.type == thirdCard.type)
        val s5:Boolean = (firstCard.type == thirdCard.type) && (firstCard.type != secondCard.type)

        when(true){
            s1 -> return 30.5
            s2 -> return getCardValue(firstCard.value) + getCardValue(secondCard.value) + getCardValue(thirdCard.value)
            s3 -> return getCardValue(firstCard.value) + getCardValue(secondCard.value)
            s4 -> return getCardValue(secondCard.value) + getCardValue(thirdCard.value)
            s5 -> return getCardValue(firstCard.value) + getCardValue(thirdCard.value)
            // Wenn die alle Karten unterschiedliche Typen haben
            else -> {
                return if(getCardValue(firstCard.value).compareTo(getCardValue(secondCard.value))>=0){
                    if(getCardValue(firstCard.value).compareTo(getCardValue(thirdCard.value))>=0){
                        getCardValue(firstCard.value)
                    } else{
                        getCardValue(thirdCard.value)
                    }
                } else{
                    if(getCardValue(secondCard.value).compareTo(getCardValue(thirdCard.value))>=0){
                        getCardValue(secondCard.value)
                    } else{
                        getCardValue(thirdCard.value)
                    }
                }
            }
        }
    }

    /**
     * Eine Funktion, die eine Liste aus Double zurückgibt.
     *
     */
    fun calculateEndGame():List<Double>{
        val game = rootService.currentGame
        val endList:MutableList<Double> = mutableListOf()
        val playerSize = game?.getPlayersSize()
        if (game != null) {
            for (i in 0 until playerSize!!) {
                endList.add(handCardsCalculator(game.players[i].hand))
            }
        }
        return endList
    }

    /**
     * Die Funktion berechnet am Ende des Spiels die Maximumpunkte von den Spielern.
     * @return Maximum Double
     */
    fun calculateWinner(): Double? {
        val game = rootService.currentGame
        val endList:MutableList<Double> = mutableListOf()
        val playerSize = game?.getPlayersSize()
        if (game != null) {
            for (i in 0 until playerSize!!) {
                endList.add(handCardsCalculator(game.players[i].hand))
            }

        }
        return endList.maxOrNull()
    }

    /**
     * Hier wird der Gewinnersname ausgegeben.
     * @return String, der Name von dem Gewinner
     */
    fun calculateWinnerName():String?{
        val game = rootService.currentGame
        checkNotNull(game)
        val endList:MutableList<Double> = mutableListOf()
        val playerSize = game.getPlayersSize()
        for(i in 0 until playerSize){
            endList.add(handCardsCalculator(game.players[i].hand))
        }
        val max = endList.indices.maxByOrNull { endList[it] } ?: -1
        return game.players[max].playerName
    }

    /**
     * alle Karten wird upgedated
     */
    fun nextTurn(){
        onAllRefreshables { refreshAfterNextTurn() }
    }

}