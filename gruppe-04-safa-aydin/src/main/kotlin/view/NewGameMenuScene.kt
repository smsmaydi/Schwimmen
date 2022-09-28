package view

import service.RootService
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual

/**
 * Das Menu, wo man die Spielernamen hinzugefügt wird.
 */
class NewGameMenuScene(private val rootService:RootService):MenuScene(400,400),Refreshable {
    private val headlineLabel = Label(
        width = 300, height = 50, posX = 50, posY = 50,
        text = "Start New Game",
        font = Font(size = 22)
    )
///////////////////P1/////////////////
    private val p1Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 125,
        text = "Player 1:"
    )
    private val p1Input:TextField = TextField(
        width = 200, height = 35, posX = 150, posY=125
    ).apply{
        onKeyTyped = {
            this.text.isBlank() || p2Input.text.isBlank()
        }
    }
    //////////P2//////////
    private val p2Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 170,
        text = "Player 2:"
    )
    private val p2Input:TextField = TextField(
        width = 200, height = 35, posX = 150, posY=170
    ).apply{
        onKeyTyped = {
            this.text.isBlank() || p1Input.text.isBlank()
        }
    }

    //////////P3//////////////
    private val p3Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 215,
        text = "Player 3:"
    )
    private val p3Input:TextField = TextField(
        width = 200, height = 35, posX = 150, posY=215
    ).apply{
        onKeyTyped = {
            this.text.isBlank() || p1Input.text.isBlank()
        }
    }

    /////////P4//////////////
    private val p4Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 260,
        text = "Player 4:"
    )
    private val p4Input:TextField = TextField(
        width = 200, height = 35, posX = 150, posY=260

    ).apply{
        onKeyTyped = {
            this.text.isBlank() || p1Input.text.isBlank()
        }
    }

    //////////BUTTONS/////////////
    val quitButton = Button(
        width = 140, height = 35,
        posX = 50, posY = 310,
        text = "Quit"
    ).apply{
        visual=ColorVisual(221,136,136)
    }

    private val startButton = Button(
        width = 140, height = 35,
        posX = 210, posY = 310,
        text = "Start"
    ).apply{
        visual = ColorVisual(136,221,136)
        onMouseClicked={
            if(p1Input.text.isNotBlank() && p2Input.text.isNotBlank() && p3Input.text.isNotBlank() && p4Input.text.isNotBlank()){
                rootService.gameService.startGame(listOf(p1Input.text.trim(),p2Input.text.trim(),p3Input.text.trim(),p4Input.text.trim()))
            }else if(p1Input.text.isNotBlank() && p2Input.text.isNotBlank() && p3Input.text.isNotBlank() && p4Input.text.isBlank()){
                rootService.gameService.startGame(listOf(p1Input.text.trim(),p2Input.text.trim(),p3Input.text.trim()))
            }else if(p1Input.text.isNotBlank() && p2Input.text.isNotBlank() && p3Input.text.isBlank()){
                rootService.gameService.startGame(listOf(p1Input.text.trim(),p2Input.text.trim()))
            }
        }
    }

    init{
        opacity = .9
        addComponents(headlineLabel,
            p1Label, p1Input,
            p2Label, p2Input,
            p3Label, p3Input,
            p4Label, p4Input,
            startButton, quitButton
        )
    }
}
