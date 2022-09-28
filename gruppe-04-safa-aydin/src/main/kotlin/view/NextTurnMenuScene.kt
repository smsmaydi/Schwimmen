package view

import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual

/**
 * nach einem Zug wird dieses Menu gezeigt und wenn man "Start turn" klickt, ist der nächste Spieler dran
 */
class NextTurnMenuScene(private val rootService: RootService) : MenuScene(960, 540), Refreshable {

    private val nextPlayerLabel = Label(
        width = 200, height = 50,
        posX = 380, posY = 190,
        text = "next Player:",
        font = Font(size = 22)
    )

    private val nextPlayerNameLabel = Label(
        width = 800, height = 50,
        posX = 80, posY = 240,
        text = "test",
        font = Font(size = 22)
    )

     val startTurnButton = Button(
        width = 100, height = 50,
        posX = 430, posY = 300,
        text = "Start Turn",
    ).apply {
        visual = ColorVisual(136, 221, 136)

    }

    init {
        opacity = .5
        background = ColorVisual(108, 168, 59)
        addComponents(
            nextPlayerLabel, nextPlayerNameLabel,
            startTurnButton
        )
    }

    /**
     * der nächste Spieler ist dran
     */
    override fun refreshAfterTurn() {
        val activeGame = rootService.currentGame
        checkNotNull(activeGame){"No game started yet"}
        nextPlayerNameLabel.text = "${activeGame.activePlayer.playerName} (Player nr.: " +
                "${activeGame.players.indexOf(activeGame.activePlayer) + 1})"
    }
}