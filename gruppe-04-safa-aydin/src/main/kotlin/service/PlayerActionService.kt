package service
import entity.Card
import entity.GameState


/**
 * Hier wird das Logik von dem Spieleraktionen erstellt
 */
class PlayerActionService(private val rootService: RootService): AbstractRefreshingService() {

    /**
     * in dieser Funktion wird der nächste Index von Spieler gerechnet.
     *
     * wenn active Index größer als players.size ist, wird es zurück zu '0' eingesetzt.
     * Sonst wird der Index um 1 erhöht.
     * @return Integer, der nächste Index von Spieler
     */
    var activePlayerIndex=0

    /**
     * Hier wird berechnet der nächste Index von Spieler.
     * @return gibt index von dem nächsten Spieler zurück.
     */
    fun nextActivePlayer():Int{
        val game = rootService.currentGame

        val playersSize = game!!.getPlayersSize()
       if(activePlayerIndex == playersSize-1){
            activePlayerIndex = 0
        }else{
            activePlayerIndex++
        }
        return activePlayerIndex
    }

    /**
     * Nächste Spieler wird dran
     */
    fun switchActivePlayer(){
        val game = rootService.currentGame
        checkNotNull(game)
        val nextPlayerIndex = nextActivePlayer()
        game.activePlayer = game.players[nextPlayerIndex]
        game.activePlayerIndex = nextPlayerIndex
        onAllRefreshables { refreshAfterNextTurn() }

    }

    /**
     * Alle Karten werden ausgetauscht.
     */
    fun swapAllCards(){
        val game = rootService.currentGame
        val temp: MutableList<Card> = game!!.middleCards
        game.middleCards = game.players[activePlayerIndex].hand
        game.players[activePlayerIndex].hand = temp
        game.passCount = 0

        if(tappCounter > 0){
            tappCounter++
        }else if(tappCounter == game.players.size){
            GameState.FINISHED
            onAllRefreshables { refreshAfterEndGame() }
        }

        switchActivePlayer()
        onAllRefreshables {  refreshAfterSwappingCards()}
    }



    /**
     * Eine Karte aus der Hand und eine Karte aus der Mitte werden ausgetauscht
     * @param handCard Karte in der Hand und eine Karte in der Mitte
     */
    fun swapACard(handCard: Card, middleCard : Card){

        val game = rootService.currentGame
        checkNotNull(game)
        game.activePlayer.hand.remove(handCard)
        game.middleCards.remove(middleCard)

        game.activePlayer.hand.add(middleCard)
        game.middleCards.add(handCard)

        game.passCount = 0

        if(tappCounter > 0){
            tappCounter++
        }else if(tappCounter == game.players.size){
            GameState.FINISHED
            onAllRefreshables { refreshAfterEndGame() }
        }

        switchActivePlayer()
        onAllRefreshables { refreshAfterSwappingCards() }
    }

    /**
     * Man guckt hier, ob der nächste Spieler geklopft hat.
     * Wenn ja ist, gibt es 'true' zurück
     * sonst 'false' zurück
     */
    fun peekNextPlayer():Boolean{
        val game = rootService.currentGame
        return game!!.players[nextActivePlayer()].tapped
    }
    private var tappCounter = 0

    /**
     * Eine Funktion für das Klopfen
     * Erst wird tapped 'true' eingesetzt. Danach wird es geguckt ob der nächste
     * Spieler schon gekloppft hat. Wenn ja wird das Spiel beendet. Sonst ist der
     * nächste Spieler dran.
     *
     */
    fun tapp(){
        val game = rootService.currentGame
        checkNotNull(game)
        val activePlayer = game.players[activePlayerIndex]
        activePlayer.tapped = true

        if(tappCounter==0){
            tappCounter++
            game.passCount = 0
            this.switchActivePlayer()
            println("after switch() ${game.players[activePlayerIndex]}")
            onAllRefreshables { refreshAfterNextTurn() }
        }

        else if(tappCounter == game.players.size-1){
            GameState.FINISHED
            onAllRefreshables { refreshAfterEndGame() }
        }
        else{
            tappCounter++
            this.switchActivePlayer()
            println("after switch() ${game.players[activePlayerIndex]}")
            onAllRefreshables { refreshAfterNextTurn() }
        }

    }

    /**
     * Hier wird gepasst und die passCount um 1 erhöht.
     * Es wird erst kontrolliert, ob der Spieler letzte dran ist. Danach wird es kontrolliert, ob vorher alle Spieler
     * nacheinander gepasst haben. Wenn es ja ist, wird nochmal kontrolliert, ob es noch drei Karten in der Mitte gibt.
     * Wenn es gibt, werden in die Mitte neue drei Karten hinzugefügt. Sonst ist das Spiel beendet.
     */
    fun pass(){
        val game = rootService.currentGame
        val activePlayer = game!!.players[activePlayerIndex]
        activePlayer.passed = true

        println("arttirilmadan önce ${game.passCount}")



        if (game.passCount == game.getPlayersSize()-1) {
            if (rootService.gameService.areThereAnyThreeCardsToDraw()) {
                rootService.gameService.changeMiddleCards()
                game.passCount = 0
                this.switchActivePlayer()
                onAllRefreshables {refreshAfterNextTurn()  }
            }
            else {
                GameState.FINISHED
                onAllRefreshables { refreshAfterEndGame() }
            // game finished , calculate end game
            }
        } else if( tappCounter == game.getPlayersSize()-1) {
            GameState.FINISHED
            onAllRefreshables { refreshAfterEndGame() }
        }else if(tappCounter > 0){
            tappCounter++
            this.switchActivePlayer()
            onAllRefreshables { refreshAfterNextTurn() }
        }else {
            tappCounter = 0
            game.passCount++
            this.switchActivePlayer()
            onAllRefreshables { refreshAfterNextTurn() }
        }


        println("${activePlayer.playerName} +${activePlayer.tapped}")



    }

    /**
     * Hier wird gecheckt, ob das Spiel schon fertig ist.
     */
    fun isGameEnded():Boolean{
        val game = rootService.currentGame
        return if (game != null) {
            game.stateOfGame == GameState.FINISHED
        }else{
            false
        }

    }

}