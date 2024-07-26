package app

import GamePanel
import core.GameLogic
import core.engine.controller.KeyboardListener
import screen.Window

fun main() {
    val myScreen = Window(title = "Mi Ventana", width = 800, height = 600)
    val keyboardListener = KeyboardListener()
    val gameLogic = GameLogic(keyboardListener)
    val gamePanel = GamePanel(60, gameLogic)
    myScreen.add(gamePanel)
    gamePanel.addKeyboardListener(keyboardListener)
    myScreen.show()
    gamePanel.start()
}
