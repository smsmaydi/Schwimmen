package entity

/**
 * In dieser Klasse werden Spieler definiert.
 *
 * Erste zwei Spieler dürfen nicht null sein, aber die andere Spieler dürfen null sein.
 * Deshalb ist das Spiel für 2-4 Personen.
 * @param player1 Erster Spieler
 * @param player2 Zweiter Spieler
 * @param player3 Dritter Spieler (Optional, kann null sein)
 * @param player4 Vierter Spieler (Optional, kann null sein)
 */
data class Game(val player1: Player, val player2: Player,val player3:Player?, val player4: Player?) {
    /**
     * Game state wird aktiv eingeschaltet
     */

    var stateOfGame : GameState = GameState.ACTIVE

    /**
     * Pass zähler
     */
    var passCount:Int = 0

    /**
     * Array für die Karte in der Mitte
     */
    var middleCards: MutableList<Card> = mutableListOf()

    /**
     * Array für den Kartenstapel
     */

    var drawPile: MutableList<Card> = mutableListOf()

    /**
     * Array für verschiedene Spieler
     */
    var players: MutableList<Player>  = mutableListOf()

    /**
     * @return Zahl von Spieler
     */
    fun getPlayersSize():Int{
        return players.size
    }



    /**
     * In diesem Constructor werden die Spieler in den $players list hinzugefügt
     */
    init {
        this.players.add(player1)
        this.players.add(player2)
        player3?.apply{players += player3}
        player4?.apply{players += player4}
    }

    /**
     * Indexzahl für den aktiven Spieler
     */
    var activePlayerIndex: Int = 0
    var activePlayer:Player = players[0]

    /**
     * Game State + Current Player + Karten in der Mitte + Anzahl von Stapelkarten gerechnet
     */
    override fun toString():String{
        var result = ""
        result += "Game state is $stateOfGame \n"
        result += "Current player: ${players[activePlayerIndex]} \n"

        if(middleCards.size > 0 ) this.drawPile.forEach{card -> result += "$card \n"}
        else result += ""

        result += "${drawPile.size} left in the pile.\n"

        return result
    }
}