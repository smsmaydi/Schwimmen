package service
import entity.Game
import entity.Player
import entity.Card
import entity.CardType
import entity.CardValue
import org.junit.jupiter.api.Assertions
import kotlin.test.*

class GameTest {
    /**
     * Verschieden Players wurde hergestellt
     */
    private val playerName:String = "SAFA"
    private val playerName2:String = "TILL"
    private val playerName3:String = "JACOB"
    private val playerName4:String = "AMR"

    /**
     * verschiedene Karten hergestellt
     */
    private val aceOfSpades = Card(CardType.SPADE, CardValue.ACE)
    private val jackOfClubs = Card(CardType.CLUB, CardValue.JACK)
    private val queenOfHearts = Card(CardType.HEART, CardValue.QUEEN)


    private var middleCards: ArrayDeque<Card> = ArrayDeque()
    private var handCards: ArrayDeque<Card> = ArrayDeque()

    /**
     * Karten in die Mitte und auf die Hand hergestellt
     */
    init {
        middleCards.add(aceOfSpades)
        middleCards.add(jackOfClubs)
        middleCards.add(queenOfHearts)

        handCards.add(aceOfSpades)
        handCards.add(jackOfClubs)
        handCards.add(queenOfHearts)



    }

    /**
     * Verschieden Players wurde hergestellt
     */

    private val playerOne: Player = Player(playerName, handCards )
    private val playerTwo: Player = Player(playerName2, handCards)
    private val playerThree: Player = Player(playerName3, handCards)
    private val playerFour: Player = Player(playerName4, handCards)


    /**
     * Verschiedene Spieler Varianten wurde hergestellt
     */
    private val gameOne: Game = Game(playerOne, playerTwo,playerThree,null)


    /**
     * Testiert, ob Game und Spielern funktioniert
     */
    @Test
    fun gameTest(){
        Assertions.assertNotNull(gameOne)
        Assertions.assertNotNull(playerOne)
        Assertions.assertNotNull(playerTwo)
        Assertions.assertNotNull(playerThree)
        Assertions.assertNotNull(playerFour)
    }





}