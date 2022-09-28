package entity

/**
 * Erstellt eine Karte mit dem Attributes type und value
 * @param type Der Typ von Spielkarten. Darf nicht leer sein.
 * @param value Die Zahl von Spielkarten. Darf nicht leer sein.
 */
data class Card(var type: CardType, var value: CardValue) {

    override fun toString() = "$type$value"


}