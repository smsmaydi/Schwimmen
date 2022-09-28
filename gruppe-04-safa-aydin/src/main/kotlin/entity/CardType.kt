package entity

/**
 * Die Kartentypen sind hier.
 */
enum class CardType {

    CLUB,
    SPADE,
    HEART,
    DIAMOND,
    ;

    /**
     * provide a single character to represent this suit.
     * Returns one of: ♣/♠/♥/♦
     */
    override fun toString() = when(this) {
        CLUB -> "♣"
        SPADE -> "♠"
        HEART -> "♥"
        DIAMOND -> "♦"
    }
}