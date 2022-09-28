package service
import entity.*
import kotlin.test.*

/**
 * Test für Serviceclass
 */
class ServiceTest {

    /**
     * Eine Liste aus 32 Karten
     */
    private val cards= mutableListOf(
        Card(CardType.SPADE, CardValue.EIGHT),
        Card(CardType.DIAMOND,CardValue.JACK),
        Card(CardType.HEART,CardValue.SEVEN),

        Card(CardType.SPADE,CardValue.SEVEN),
        Card(CardType.CLUB,CardValue.SEVEN),
        Card(CardType.SPADE,CardValue.JACK),

        Card(CardType.HEART,CardValue.EIGHT),
        Card(CardType.DIAMOND,CardValue.QUEEN),
        Card(CardType.CLUB,CardValue.JACK),

        Card(CardType.CLUB,CardValue.KING),
        Card(CardType.DIAMOND,CardValue.EIGHT),
        Card(CardType.CLUB,CardValue.EIGHT),

        Card(CardType.CLUB,CardValue.ACE),
        Card(CardType.DIAMOND,CardValue.KING),
        Card(CardType.HEART,CardValue.QUEEN),

        Card(CardType.HEART,CardValue.JACK),
        Card(CardType.HEART,CardValue.NINE),
        Card(CardType.SPADE,CardValue.NINE),

        Card(CardType.SPADE,CardValue.QUEEN),
        Card(CardType.SPADE,CardValue.ACE),
        Card(CardType.CLUB,CardValue.TEN),
        Card(CardType.HEART,CardValue.KING),
        Card(CardType.HEART,CardValue.TEN),
        Card(CardType.DIAMOND,CardValue.NINE),
        Card(CardType.DIAMOND,CardValue.ACE),
        Card(CardType.CLUB,CardValue.QUEEN),
        Card(CardType.HEART,CardValue.ACE),
        Card(CardType.CLUB,CardValue.NINE),
        Card(CardType.DIAMOND,CardValue.SEVEN),
        Card(CardType.SPADE,CardValue.TEN),
        Card(CardType.DIAMOND,CardValue.TEN),
        Card(CardType.SPADE,CardValue.KING)
    )


    /**
     * Hier gibt es fünf verschiedene Kartenliste. Die Summe von den Karten sind
     * über den Variablen
     */
    // 20 Punkte
    private val handCardsOne = mutableListOf(
        Card(CardType.SPADE,CardValue.TEN),
        Card(CardType.DIAMOND,CardValue.TEN),
        Card(CardType.SPADE,CardValue.KING))
    // 30.5
    private val handCardsTwo = mutableListOf(
        Card(CardType.SPADE,CardValue.TEN),
        Card(CardType.DIAMOND,CardValue.TEN),
        Card(CardType.CLUB,CardValue.TEN))
    //30
    private val handCardsThree = mutableListOf(
        Card(CardType.SPADE,CardValue.TEN),
        Card(CardType.SPADE,CardValue.QUEEN),
        Card(CardType.SPADE,CardValue.KING))
    //31
    private val handCardsFour = mutableListOf(
        Card(CardType.SPADE,CardValue.ACE),
        Card(CardType.SPADE,CardValue.QUEEN),
        Card(CardType.SPADE,CardValue.KING))
    //10
    private val handCardsFive = mutableListOf(
        Card(CardType.SPADE,CardValue.EIGHT),
        Card(CardType.CLUB,CardValue.EIGHT),
        Card(CardType.DIAMOND,CardValue.TEN))

    /**
     * Hier gibt es ein Spielbespiel aus vier Personen und mit deren eigenen Namen und
     * verschiedene Karten in der Hand aus oben initializerte Variablen
     */
    //Game One
    private val gameOne = Game(Player("Safa",handCardsOne),
        Player("Till",handCardsTwo),
        Player("Amr",handCardsThree),
        Player("Jacob",handCardsFour)
        )

    /**
     * Hier wird mit falschen Eingaben für Spiel getestet.
     */

    @Test
    fun startGameWithWrongArguments(){
        val mc = RootService()
        val onePlayerGame = listOf("Safa")
        val fivePlayerGame = listOf("Till","Safa","Jakob","Amr","al")
        val fourPlayerGame = listOf("Safa","Till","Amr","Jakob")
        assertFailsWith<java.lang.IllegalStateException> { mc.gameService.startGame(onePlayerGame,cards)}
        assertFailsWith<java.lang.IllegalStateException> { mc.gameService.startGame(fivePlayerGame,cards)}
        assertNotNull(mc.gameService.startGame(fourPlayerGame,cards))
    }

    /**
     * Hier wird getestet, ob in dem Kartenstapel drei oder mehr Karten gibt.
     */
    @Test
    fun areThereAnyThreeCardsToDrawTest(){
        val mc = RootService()

        mc.gameService.startGame(listOf("Safa","Till","Amr","Jakob"),cards)

        assertTrue(mc.gameService.areThereAnyThreeCardsToDraw())



        assertTrue(mc.gameService.areThereAnyThreeCardsToDraw())
    }

    /**
     * Hier wird getestet, ob das Spiel ohne den Karten laufen kann.
     */
    @Test
    fun startGameTest(){
        val mc = RootService()
        //assertNull(mc.currentGame)
        val playerNames = listOf("Safa","Till","Amr","Jacob")
        mc.gameService.startGame(playerNames,cards)
        assertNotNull(mc.currentGame)

        //mc.gameService.startGame(listOf("Safa","Till","Amr","Jacob"))
        //assertNotNull(mc.currentGame)
    }

    /**
     * Hier wird die Kartenlisten berechnet und getestet.
     */
    @Test
    fun handCardsCalculatorTest(){
        val mc = RootService()
        var points = mc.gameService.handCardsCalculator(handCardsOne)
        assertEquals(20.0, points)


        points = mc.gameService.handCardsCalculator(handCardsTwo)
        assertEquals(30.5, points)

        points = mc.gameService.handCardsCalculator(handCardsThree)
        assertEquals(30.0, points)

        points = mc.gameService.handCardsCalculator(handCardsFour)
        assertEquals(31.0, points)

        points = mc.gameService.handCardsCalculator(handCardsFive)
        assertEquals(10.0, points)
    }

    /**
     * Gewinner Punkt Test
     */
    @Test
    fun calculateWinnerTest(){
        val mc =RootService()
        mc.currentGame = gameOne
        val maxPunkt = mc.gameService.calculateWinner()
        assertEquals(31.0,maxPunkt)
        assertNotEquals(30.5,maxPunkt)
        assertNotEquals(30.0,maxPunkt)
        assertNotEquals(20.0,maxPunkt)
    }
    @Test
    fun calculateWinnerNameTest(){
        val mc = RootService()
        mc.currentGame = gameOne
        val maxName = mc.gameService.calculateWinnerName()
        assertEquals("Jacob",maxName)
        assertNotEquals("Amr",maxName)
        assertNotEquals("Till",maxName)
        assertNotEquals("Safa",maxName)
    }

    /**
     * Hier wird das Ende des Spiels die Punkte berechnet und getestet.
     */
    @Test
    fun calculateEndGameTest(){
        val mc = RootService()
        mc.currentGame = gameOne
        //20, 30.5, 30, 31
        val endList = mutableListOf(20.0,30.5,30.0,31.0)
        assertEquals(endList,mc.gameService.calculateEndGame())
    }

    /**
     * Hier wird der nächste Spieler getestet.
     */
    @Test
    fun nextActivePlayerTest(){
        val mc = RootService()
        mc.currentGame = gameOne

        assertEquals(1,mc.playerActionService.nextActivePlayer())
        assertEquals(2,mc.playerActionService.nextActivePlayer())
        assertEquals(3,mc.playerActionService.nextActivePlayer())
        assertEquals(0,mc.playerActionService.nextActivePlayer())
        mc.playerActionService.nextActivePlayer()
        assertEquals(2,mc.playerActionService.nextActivePlayer())
    }

    /**
     * Hier wird alle Karten tauschen getestet.
     */
    @Test
    fun swapAllCardsTest(){
        val mc = RootService()
        mc.currentGame = gameOne
        mc.currentGame!!.middleCards = handCardsFive
        mc.playerActionService.swapAllCards()
        gameOne.player3?.let { assertEquals(handCardsThree, it.hand) }
    }

    /**
     * Hier wird eine Karte Tauschen getestet
     * ( noch nicht fertig)
     */
    @Test
    fun swapACardTest(){
        val mc = RootService()
        mc.currentGame = gameOne
        mc.currentGame!!.activePlayer = mc.currentGame!!.player1
        mc.currentGame!!.middleCards = handCardsFive
        val card:Card = gameOne.player1.hand[0]
        mc.playerActionService.swapACard(mc.currentGame!!.activePlayer.hand[0], mc.currentGame!!.middleCards[0])
        assert(mc.currentGame!!.middleCards.contains(card))
    }

    /**
     * Kartenzahl Test
     */
    @Test
    fun getCardValueTest(){
        val mc = RootService()
        val ace = mc.gameService.getCardValue(CardValue.ACE)
        val jack =  mc.gameService.getCardValue(CardValue.JACK)
        val seven = mc.gameService.getCardValue(CardValue.SEVEN)
        val nine = mc.gameService.getCardValue(CardValue.NINE)
        assertEquals(11.0,ace)
        assertEquals(10.0,jack)
        assertEquals(7.0,seven)
        assertNotEquals(10.0,nine)

    }

    /**
     * Aktivespielerwechseln Test
     */
    @Test
    fun switchActivePlayerTest(){
        val mc = RootService()
        mc.currentGame = gameOne

        //

        mc.playerActionService.switchActivePlayer()
        assertEquals(1,mc.playerActionService.activePlayerIndex)

        mc.playerActionService.switchActivePlayer()
        assertEquals(2,mc.playerActionService.activePlayerIndex)

        mc.playerActionService.switchActivePlayer()
        assertEquals(3,mc.playerActionService.activePlayerIndex)

        mc.playerActionService.switchActivePlayer()
        assertEquals(0,mc.playerActionService.activePlayerIndex)

    }

    /**
     * Hier wird getestet, ob der nächste Spieler vorher schon geklopft hat.
     */
    @Test
    fun peekNextPlayerTest(){
        val mc = RootService()
        mc.currentGame = gameOne
        gameOne.player1.tapped = true
        mc.playerActionService.activePlayerIndex = 3
        assertTrue(mc.playerActionService.peekNextPlayer())
    }

    /**
     * Hier wird die Funktion tapp() getestet.
     */
    @Test
    fun tappTest(){
        val mc = RootService()
        mc.currentGame = gameOne
        mc.playerActionService.activePlayerIndex = 0
        mc.playerActionService.tapp()
        assertTrue(gameOne.player1.tapped)
        assertFalse(gameOne.player2.tapped)

    }

    /**
     * Hier wird die Funktion pass() getestet.
     */
    @Test
    fun passTest(){
        val mc = RootService()
        mc.currentGame = gameOne
        mc.playerActionService.activePlayerIndex = 0
        mc.currentGame!!.passCount = 2

        mc.playerActionService.pass()

        assertEquals(3, mc.currentGame!!.passCount)
        assertTrue(gameOne.player1.passed)
    }

    /**
     * Hier wird getestet, ob das Spiel schon beendet ist.
     */
    @Test
    fun isGameEndedTest(){
        val mc = RootService()
        mc.currentGame = gameOne
        mc.currentGame!!.stateOfGame = GameState.FINISHED
        assertTrue(mc.playerActionService.isGameEnded())

        mc.currentGame!!.stateOfGame = GameState.ACTIVE
        assertFalse(mc.playerActionService.isGameEnded())
    }


}