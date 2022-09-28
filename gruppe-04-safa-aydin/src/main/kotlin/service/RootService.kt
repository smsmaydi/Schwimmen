package service
import entity.Game
import view.Refreshable

/**
 * RootService
 */
class RootService {
    val playerActionService = PlayerActionService(this)
    val gameService = GameService(this)

    var currentGame: Game? = null

    fun addRefreshable(newRefreshable: Refreshable) {
        gameService.addRefreshable(newRefreshable)
        playerActionService.addRefreshable(newRefreshable)
    }

    fun addRefreshables(vararg newRefreshables: Refreshable) {
        newRefreshables.forEach { addRefreshable(it) }
    }

}