package service
import entity.Card
import entity.CardType
import entity.CardValue
import kotlin.test.*


class CardTest {
    private val aceOfSpades = Card(CardType.SPADE, CardValue.ACE)
    private val jackOfClubs = Card(CardType.CLUB, CardValue.JACK)
    private val queenOfHearts = Card(CardType.HEART, CardValue.QUEEN)
    private val jackOfDiamonds = Card(CardType.DIAMOND, CardValue.JACK)

    private val heartsChar = '\u2665' // ♥
    private val diamondsChar = '\u2666' // ♦
    private val spadesChar = '\u2660' // ♠
    private val clubsChar = '\u2663' // ♣

    @Test
    fun testToString() {
        assertEquals(spadesChar + "A", aceOfSpades.toString())
        assertEquals(clubsChar + "J", jackOfClubs.toString())
        assertEquals(heartsChar + "Q", queenOfHearts.toString())
        assertEquals(diamondsChar + "J", jackOfDiamonds.toString())
    }




}