package view
import tools.aqua.bgw.core.BoardGameApplication
import service.RootService

/**
 * Main.kt wird hier aufrufen und das Spiel wird hier gearbeitet.
 */
class SchwimmenApplication:BoardGameApplication("Schwimmen"),Refreshable {
    private val rootService = RootService()
    private val cards = rootService.currentGame?.drawPile
    private val newGameMenuScene = NewGameMenuScene(rootService).apply {
        quitButton.onMouseClicked = {
            exit()
        }
    }
    private val nextTurn = NextTurnMenuScene(rootService).apply{
        startTurnButton.onMouseClicked = {
            rootService.gameService.nextTurn()
            hideMenuScene()

        }
    }
    private val gameFinishedMenuScene = GameFinishedMenuScene(rootService).apply {
        newGameButton.onMouseClicked = {
            this@SchwimmenApplication.showMenuScene(newGameMenuScene)
        }
        quitButton.onMouseClicked = {
            exit()
        }
    }

    private val gameScene = SchwimmenGameScene(rootService)
    init {
        rootService.addRefreshables(
            this,
            gameScene,
            newGameMenuScene,
            nextTurn,
            gameFinishedMenuScene
        )



        if (cards != null) {
            rootService.gameService.startGame(listOf("Safa","Till","Jakob","Amr"),cards)
        }
        this.showGameScene(gameScene)
        this.showMenuScene(newGameMenuScene,0)

    }

    override fun refreshAfterStartNewGame() {
        this.hideMenuScene()
    }

    override fun refreshAfterEndGame() {
        this.showMenuScene(gameFinishedMenuScene)
    }

    override fun refreshAfterNextTurn() {
        this.showMenuScene(nextTurn)
    }


}