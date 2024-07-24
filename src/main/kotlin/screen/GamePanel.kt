import components.PositionComponent
import core.GameLogic
import core.controller.KeyboardListener
import core.entity.Entity
import java.awt.Color
import javax.swing.JPanel
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D

class GamePanel (val fps: Int, val gameLogic: GameLogic) : JPanel(), Runnable {
    companion object {
        private const val TILE_SIZE: Int = 16
        private const val SCALE_FACTOR: Int = 3
        private const val RENDER_TILE_SIZE: Int = TILE_SIZE * SCALE_FACTOR
        private const val MAX_SCREEN_COL = 16
        private const val MAX_SCREEN_ROW = 16
        private const val SCREEN_WIDTH = RENDER_TILE_SIZE * MAX_SCREEN_COL
        private const val SCREEN_HEIGHT = RENDER_TILE_SIZE * MAX_SCREEN_ROW
    }

    private var running: Boolean = false

    init {
        preferredSize = Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)
        background = Color.black
        isDoubleBuffered = true
        isFocusable = true
        requestFocus()
        requestFocusInWindow()
    }

    fun start() {
        if (!running) {
            running = true
            val thread = Thread(this)
            thread.start()
        }
    }

    fun stop() {
        running = false
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        render(g)
    }

    private fun render(g: Graphics) {
        val g2 = g as Graphics2D
        clear(g2)
        for (entity in gameLogic.gameObjectsList) {
            val entityPosComponent = entity.componentManager.getComponentByName("positionComponent") as PositionComponent
            val pos = entityPosComponent.getPosition()
            g2.fillRect(pos.x.toInt(), pos.y.toInt(), 22,22)  // Llena el fondo con el color blanco
        }
        g2.dispose()
    }

    private fun clear(g2: Graphics2D) {
        g2.color = Color.white
    }

    fun addKeyboardListener(k: KeyboardListener) {
        addKeyListener(k)
    }

    fun resize() {
    }

    override fun run() {
        // Calcular el intervalo de dibujo en nanosegundos
        val drawInterval = 1_000_000_000L / fps
        var nextDrawTime = System.nanoTime()

        while (running) {
            try {
                repaint()

                val now = System.nanoTime()
                var remainingTime = nextDrawTime - now

                if (remainingTime < 0) {
                    remainingTime = 0
                }

                Thread.sleep(remainingTime / 1_000_000L) // Convertir nanosegundos a milisegundos

                nextDrawTime += drawInterval

            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
                break
            }
        }
    }

}
