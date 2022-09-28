package entity

/**
 * Hier sind die Spielstatus
 */
enum class GameState {
    ACTIVE,
    FINISHED;

    /**
     * > The function `toString()` returns a string representation of the enum constant
     */
    override fun toString()=
        when(this){
            ACTIVE -> "ACTIVE"
            FINISHED -> "FINISHED"
        }
}