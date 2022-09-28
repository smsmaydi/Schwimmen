package view
import entity.Game
import entity.Player
import entity.Card
import entity.CardType
import entity.CardValue

/**
 *  Refreshables
 *
 */
interface Refreshable {
    /**
     * Erneuern nach dem neuen Spiel
     */
    fun refreshAfterStartNewGame():Unit{}

    /**
     * Erneuern nach dem Ende des Spiels
     */
    fun refreshAfterEndGame():Unit{}

    /**
     * Erneuern nach dem Kartenaustausch
     */
    fun refreshAfterSwappingCards():Unit{}

    /**
     * Erneuern nach Karten gezogen wird
     */
    fun refreshAfterCardsDrawn():Unit{}

    /**
     * Ernuern nach dem Turn
     */
    fun refreshAfterTurn():Unit{}

    /**
     * Erneuern nach dem nächsten Turn
     */
    fun refreshAfterNextTurn():Unit{}
}