package view

import service.RootService
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual

/**
 * Endescene, wo man die Spielername mit punkte sehen kann und der Gewinner wird hier gezeigt.
 */
class GameFinishedMenuScene(private val rootService:RootService):MenuScene(800,800),Refreshable {
    val game = rootService.currentGame
    private val headlineLabel = Label(
        width = 700, height = 50, posX = 100, posY = 20,
        text = "",
        font = Font(size = 32)
    )

    private val underline = Label(
        width =700, height = 50, posX = 100, posY = 60,
        text="-----------------------------------------------------------------------------------------------"
    )
    private val player1NameLabel = Label(
        width = 300, height = 50,
        posX = 250, posY = 125,
        text = "",font = Font(size = 25)
    )
    private val player2NameLabel = Label(
        width = 300, height = 50,
        posX = 250, posY = 170,
        text = "",font = Font(size = 25)
    )
    private val player3NameLabel = Label(
        width = 300, height = 50,
        posX = 250, posY = 215,
        text = "",font = Font(size = 25)
    )
    private val player4NameLabel = Label(
        width = 300, height = 50,
        posX = 250, posY = 260,
        text = "",font = Font(size = 25)
    )

    val quitButton = Button(
        width = 140, height = 35,
        posX = 50, posY = 310,
        text = "Quit"
    ).apply{
        visual=ColorVisual(221,136,136)
    }

    val newGameButton = Button(
        width = 140, height = 35,
        posX = 210, posY = 310,
        text = "Start"
    ).apply{
        visual = ColorVisual(136,221,136)
    }

    init {
        opacity = .9
        background = ColorVisual(108,168,59)
        addComponents(
            headlineLabel,underline,player1NameLabel,player2NameLabel,player3NameLabel,player4NameLabel,
        )
    }

    /**
     * Spieler namen mit Punkten und der Gewinner wird mit der Funktion gezeigt.
     */
    override fun refreshAfterEndGame() {
        val game = rootService.currentGame
        checkNotNull(game){"There is no game"}
        headlineLabel.text = "Winner is: ${rootService.gameService.calculateWinnerName()} with ${rootService.gameService.calculateWinner()} points"
        player1NameLabel.text = "${game.player1.playerName} -> ${rootService.gameService.handCardsCalculator(game.player1.hand)}"
        player2NameLabel.text = "${game.player2.playerName} -> ${rootService.gameService.handCardsCalculator(game.player2.hand)}"
        if(game.players.size == 3){
            player3NameLabel.text = "${game.player3?.playerName} -> ${game.player3?.let {
                rootService.gameService.handCardsCalculator(
                    it.hand)
            }}"
            player4NameLabel.text = ""

        }else if(game.players.size ==4){
            player3NameLabel.text = "${game.player3?.playerName} -> ${game.player3?.let {
                rootService.gameService.handCardsCalculator(
                    it.hand)
            }}"
            player4NameLabel.text = "${game.player4?.playerName} -> ${game.player4?.let {
                rootService.gameService.handCardsCalculator(
                    it.hand)
            }}"
        }else if(game.players.size <3){
            player3NameLabel.text = ""
            player4NameLabel.text = ""
        }

    }

}