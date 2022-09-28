package service
import entity.Card
import entity.CardType
import entity.CardValue

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertSame
import kotlin.test.Test

class PlayerTest {
    private val playerName:String = "SAFA"
    private val aceOfSpades = Card(CardType.SPADE, CardValue.ACE)
    private val jackOfClubs = Card(CardType.CLUB, CardValue.JACK)
    private val queenOfHearts = Card(CardType.HEART, CardValue.QUEEN)


    private var playerHandOne : ArrayDeque<Card> = ArrayDeque(3)

    init {
        playerHandOne.add(aceOfSpades)
        playerHandOne.add(jackOfClubs)
        playerHandOne.add(queenOfHearts)

    }



    private var handSize = playerHandOne.size



    @Test
    fun playerParameterTest(){
        assertSame(handSize,3)
        assertNotNull(playerName)

    }

}