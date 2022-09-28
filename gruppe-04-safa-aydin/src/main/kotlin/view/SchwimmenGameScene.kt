package view

import service.RootService
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.components.gamecomponentviews.CardView
import entity.*
import service.CardImageLoader

import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import tools.aqua.bgw.components.uicomponents.Button


/**
 * Die Spielscene, wo sich die alle Karten und die Buttons, die eigene Funktionen haben, befinden.
 */
class SchwimmenGameScene(private val rootService: RootService):BoardGameScene(1920,1080),Refreshable {
    val game = rootService.currentGame
    private val cardImageLoader = CardImageLoader()
    private var playerCardSelected = false
    private var selectedPlayerCard = 0
    private val activePlayerCards = arrayOf(
        CardView(height=200,width = 130, posX = 695, posY = 850,
            front = ImageVisual(cardImageLoader.frontImageFor(CardType.DIAMOND,CardValue.ACE)),back = ImageVisual(cardImageLoader.backImage)),
        CardView(height=200,width = 130, posX = 895, posY = 850,
            front = ImageVisual(cardImageLoader.frontImageFor(CardType.DIAMOND,CardValue.ACE)),back = ImageVisual(cardImageLoader.backImage)),
        CardView(height=200,width = 130, posX = 1095, posY = 850,
            front = ImageVisual(cardImageLoader.frontImageFor(CardType.DIAMOND,CardValue.ACE)),back = ImageVisual(cardImageLoader.backImage))
    )

    private var middleCardSelected = false
    private var selectedMiddleCard = 0
    private val middleCards = arrayOf(
        CardView(height = 200, width = 130, posX = 695, posY = 450,
            front = ImageVisual(cardImageLoader.frontImageFor(CardType.DIAMOND, CardValue.ACE)),
            back = ImageVisual(cardImageLoader.backImage)),

        CardView(height = 200, width = 130, posX = 895, posY = 450,
            front = ImageVisual(cardImageLoader.frontImageFor(CardType.DIAMOND, CardValue.ACE)),
            back = ImageVisual(cardImageLoader.backImage)),

        CardView(height = 200, width = 130, posX = 1095, posY = 450,
            front = ImageVisual(cardImageLoader.frontImageFor(CardType.DIAMOND, CardValue.ACE)),
            back = ImageVisual(cardImageLoader.backImage))
    )

    private val cardStack = CardView(height = 200, width = 130,posX = 1395, posY = 580,
    front = ImageVisual(cardImageLoader.frontImageFor(CardType.DIAMOND,CardValue.ACE)),
    back = ImageVisual(cardImageLoader.backImage)
        )
    private val tappButton = Button(
        width = 140, height = 35, posX = 400, posY = 850, text = "tapp"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        onMouseClicked = {
            rootService.currentGame?.let {
                rootService.playerActionService.tapp()
            }
        }
    }

    private val passButton = Button(
        width = 140, height = 35, posX = 400, posY = 930, text = "pass"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        onMouseClicked = {
            rootService.currentGame?.let {
                rootService.playerActionService.pass()
            }
        }
    }
    private val swapACardButton = Button(
        width = 140, height = 35, posX = 1410, posY = 850, text = "Swap 1"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        onMouseClicked = {
            rootService.currentGame.let {
                val activeGame = rootService.currentGame
                checkNotNull(activeGame) { "No game started yet" }
                rootService.playerActionService.swapACard(
                    activeGame.activePlayer.hand[selectedPlayerCard],
                    activeGame.middleCards[selectedMiddleCard]
                )
            }
        }
    }
    private val swapAllCardsButton = Button(
        width = 140, height = 35, posX = 1410, posY = 930, text = "Swap all"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        onMouseClicked = {
            rootService.currentGame?.let {
                rootService.playerActionService.swapAllCards()
            }
        }
    }
    private val otherPlayerCards = arrayOf(
        CardView(height = 200, width = 130, posX = 50, posY = 180,
            front = ImageVisual(cardImageLoader.frontImageFor(CardType.DIAMOND, CardValue.ACE)),
            back = ImageVisual(cardImageLoader.backImage)),
        CardView(height = 200, width = 130, posX = 50, posY = 430,
            front = ImageVisual(cardImageLoader.frontImageFor(CardType.DIAMOND, CardValue.ACE)),
            back = ImageVisual(cardImageLoader.backImage)),
        CardView(height = 200, width = 130, posX = 50, posY = 680,
            front = ImageVisual(cardImageLoader.frontImageFor(CardType.DIAMOND, CardValue.ACE)),
            back = ImageVisual(cardImageLoader.backImage)),

        CardView(height = 200, width = 130, posX = 695, posY = 50,
            front = ImageVisual(cardImageLoader.frontImageFor(CardType.DIAMOND, CardValue.ACE)),
            back = ImageVisual(cardImageLoader.backImage)),
        CardView(height = 200, width = 130, posX = 895, posY = 50,
            front = ImageVisual(cardImageLoader.frontImageFor(CardType.DIAMOND, CardValue.ACE)),
            back = ImageVisual(cardImageLoader.backImage)),
        CardView(height = 200, width = 130, posX = 1095, posY = 50,
            front = ImageVisual(cardImageLoader.frontImageFor(CardType.DIAMOND, CardValue.ACE)),
            back = ImageVisual(cardImageLoader.backImage)),

        CardView(height = 200, width = 130, posX = 1700, posY = 180,
            front = ImageVisual(cardImageLoader.frontImageFor(CardType.DIAMOND, CardValue.ACE)),
            back = ImageVisual(cardImageLoader.backImage)),
        CardView(height = 200, width = 130, posX = 1700, posY = 430,
            front = ImageVisual(cardImageLoader.frontImageFor(CardType.DIAMOND, CardValue.ACE)),
            back = ImageVisual(cardImageLoader.backImage)),
        CardView(height = 200, width = 130, posX = 1700, posY = 680,
            front = ImageVisual(cardImageLoader.frontImageFor(CardType.DIAMOND, CardValue.ACE)),
            back = ImageVisual(cardImageLoader.backImage))
    )

    init {
        background = ColorVisual(108, 168, 59)

        addSelectionLogic()
        for(card in activePlayerCards){
            card.showFront()
            this.addComponents(card)
        }
        for(card in middleCards){
            card.showFront()
            this.addComponents(card)
        }
        this.addComponents(
            cardStack,
            tappButton, passButton, swapACardButton, swapAllCardsButton
        )
    }

    /**
     * @param type Typ von einer Karte
     * @param value Zahl von einer Karte
     * @param cardToUpdate eine Karte mit View
     * Die Funktion gibt ein View für eine Karte
     */
    private fun updateCard(type : CardType, value : CardValue, cardToUpdate : CardView){
        cardToUpdate.frontVisual = ImageVisual(cardImageLoader.frontImageFor(type, value))
    }

    /**
     * Updates alle Karten die in der Mitte stehen
     */
    private fun updateAllMiddleCards() {
        val activeGame = rootService.currentGame
        checkNotNull(activeGame) { "no game started yet" }
        updateCard(activeGame.middleCards[0].type, activeGame.middleCards[0].value, middleCards[0])
        updateCard(activeGame.middleCards[1].type, activeGame.middleCards[1].value, middleCards[1])
        updateCard(activeGame.middleCards[2].type, activeGame.middleCards[2].value, middleCards[2])
    }

    /**
     * Updates alle Karten, die man in der Hand hat.
     */
    private fun updateAllHandCards(){
        val activeGame = rootService.currentGame
        checkNotNull(activeGame){"no game started yet"}
        updateCard(activeGame.activePlayer.hand[0].type, activeGame.activePlayer.hand[0].value, activePlayerCards[0])
        updateCard(activeGame.activePlayer.hand[1].type, activeGame.activePlayer.hand[1].value, activePlayerCards[1])
        updateCard(activeGame.activePlayer.hand[2].type, activeGame.activePlayer.hand[2].value, activePlayerCards[2])
    }


    override fun refreshAfterCardsDrawn() {
        val activeGame = rootService.currentGame
        checkNotNull(activeGame)

        updateAllMiddleCards()

    }

    override fun refreshAfterStartNewGame() {
       updateAllHandCards()
       updateAllMiddleCards()
        addOtherPlayerCards()
    }
    override fun refreshAfterSwappingCards() {
        updateAllHandCards()
        updateAllMiddleCards()
    }

    override fun refreshAfterNextTurn() {
        updateAllHandCards()
        middleCards[selectedMiddleCard].posY = 450.0
        activePlayerCards[selectedPlayerCard].posY = 850.0
        middleCardSelected = false
        playerCardSelected = false
        swapACardButton.isDisabled = true
    }

    /**
     * Wenn man eine Karte klickt, wird posY von der Karte geändert
     * Wenn man eine Karte aus der Mitte und eine Karte aus der Hand auswählt, kann man eine Karte tauschen.
     */
    private fun addSelectionLogic(){
        swapACardButton.isDisabled = true
        activePlayerCards[0].apply {
            onMouseClicked = {
                this.posY = 800.0
                activePlayerCards[1].posY = 850.0
                activePlayerCards[2].posY = 850.0
                selectedPlayerCard = 0
                playerCardSelected = true
                swapACardButton.isDisabled = !middleCardSelected
            }
        }
        activePlayerCards[1].apply {
            onMouseClicked = {
                this.posY = 800.0
                activePlayerCards[0].posY = 850.0
                activePlayerCards[2].posY = 850.0
                selectedPlayerCard = 1
                playerCardSelected = true
                swapACardButton.isDisabled = !middleCardSelected
            }
        }
        activePlayerCards[2].apply {
            onMouseClicked = {
                this.posY = 800.0
                activePlayerCards[0].posY = 850.0
                activePlayerCards[1].posY = 850.0
                selectedPlayerCard = 2
                playerCardSelected = true
                swapACardButton.isDisabled = !middleCardSelected
            }
        }

        middleCards[0].apply {
            onMouseClicked = {
                this.posY = 400.0
                middleCards[1].posY = 450.0
                middleCards[2].posY = 450.0
                selectedMiddleCard = 0
                middleCardSelected = true
                swapACardButton.isDisabled = !playerCardSelected
            }
        }
        middleCards[1].apply {
            onMouseClicked = {
                this.posY = 400.0
                middleCards[0].posY = 450.0
                middleCards[2].posY = 450.0
                selectedMiddleCard = 1
                middleCardSelected = true
                swapACardButton.isDisabled = !playerCardSelected
            }
        }
        middleCards[2].apply {
            onMouseClicked = {
                this.posY = 400.0
                middleCards[0].posY = 450.0
                middleCards[1].posY = 450.0
                selectedMiddleCard = 2
                middleCardSelected = true
                swapACardButton.isDisabled = !playerCardSelected
            }
        }
    }

    /**
     * Fügt neue Spielernkarte in das Spiel hinzu.
     * Die Karten die an der rechte und linke Seite stehen, wird hier 90 grad umgedreht.
     */
    private fun addOtherPlayerCards(){
        val activeGame = rootService.currentGame
        checkNotNull(activeGame){"no game started yet"}



        for (i in 0 .. 8){
            this.removeComponents(otherPlayerCards[i])
        }
        for (i in 0 .. 2){
            otherPlayerCards[i].rotation = 90.0
        }
        for (i in 6 .. 8){
            otherPlayerCards[i].rotation = 90.0
        }

        val playersToAdd = (activeGame.players.size - 1) * 3 - 1
        for (addCardNr in 0 .. playersToAdd){
            //if (addCardNr >= 3)otherPlayerCards[addCardNr].rotate(90)
            this.addComponents(otherPlayerCards[addCardNr])
        }
    }
}

