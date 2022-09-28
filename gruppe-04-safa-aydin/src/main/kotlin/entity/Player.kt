package entity

/**
 * @param playerName Stellt der Spielername dar
 * @param hand Stellt die Karte dar, der $playerName in seiner Hand hat
 */
class Player(
    val playerName: String?,
    var hand: MutableList<Card>
              ) {

    /**
     *  Hier wird gecheckt, ob jemand gepasst oder geklopft hat.
     */
    var passed:Boolean = false
    var tapped:Boolean = false


    override fun toString(): String = "$playerName: has in hand $hand "

}