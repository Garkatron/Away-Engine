import components.PositionComponent
import core.GameLogic
import core.controller.KeyboardListener
import core.entity.Entity
import core.maths.Vector2
import screen.Window

fun main() {
    val myScreen = Window(title = "Mi Ventana", width = 800, height = 600)
    val keyboardListener = KeyboardListener()
    val gameLogic = GameLogic(60, keyboardListener)
    val gamePanel = GamePanel(60, gameLogic)
    myScreen.add(gamePanel)
    gamePanel.addKeyboardListener(keyboardListener)
    myScreen.show()

    gameLogic.start()
    gamePanel.start()
}
