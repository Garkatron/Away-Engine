import components.PositionComponent
import components.SpriteComponent
import core.GameLogic
import core.GameObject
import core.component.Component
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
        const val RENDER_TILE_SIZE: Int = TILE_SIZE * SCALE_FACTOR
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
        val g2 = g as Graphics2D
        clear(g2) // Asegúrate de limpiar el fondo antes de dibujar
        render(g2)
    }

    private fun render(g2: Graphics2D) {
        for (entity in gameLogic.gameObjectsList) {
            var spriteComponent: SpriteComponent? = entity.componentManager.getComponentByName("spriteComponent") as SpriteComponent
            spriteComponent.let {
                it?.draw(g2)
            }

        }
    }

    private fun clear(g2: Graphics2D) {
        g2.color = Color.black // Establecer color de fondo
        g2.fillRect(0, 0, width, height) // Limpiar el área completa del panel
    }

    fun addKeyboardListener(k: KeyboardListener) {
        addKeyListener(k)
    }

    fun resize() {
    }

    override fun run() {
        // Calcular el intervalo de dibujo en nanosegundos
        val drawInterval = 1_000_000_000L / fps // Intervalo de dibujo basado en FPS
        val updateInterval = 1_000_000_000L / 60 // Intervalo de actualización basado en 60 actualizaciones por segundo
        var nextDrawTime = System.nanoTime()
        var nextUpdateTime = System.nanoTime()
        var lastUpdateTime = System.nanoTime()

        while (running) {
            try {
                val now = System.nanoTime()

                // Calcular deltaTime
                val deltaTime: Float =
                    ((now - lastUpdateTime) / 1_000_000_000.0 ).toFloat()// Convertir nanosegundos a segundos

                // Actualizar la lógica del juego si ha pasado el intervalo de actualización
                if (now - nextUpdateTime >= updateInterval) {
                    gameLogic.update(deltaTime) // Llama al método de actualización de la lógica del juego con deltaTime
                    nextUpdateTime += updateInterval
                    lastUpdateTime = now
                }

                // Renderizar si ha pasado el intervalo de dibujo
                if (now - nextDrawTime >= drawInterval) {
                    repaint() // Solicitar repaint del panel
                    nextDrawTime += drawInterval
                }

                // Controlar el tiempo de espera para mantener el ciclo en equilibrio
                var remainingTime = nextDrawTime - System.nanoTime()
                if (remainingTime < 0) {
                    remainingTime = 0
                }
                Thread.sleep(remainingTime / 1_000_000L) // Convertir nanosegundos a milisegundos

            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
                break
            }
        }
    }

}
