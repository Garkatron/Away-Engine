import core.GameLogic
import core.engine.controller.KeyboardListener
import core.engine.interfaces.IDrawable
import core.systems.debug.DebugLogic
import java.awt.Color
import javax.swing.JPanel
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D

class GamePanel(val fps: Int, val gameLogic: GameLogic) : JPanel(), Runnable {
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
    private lateinit var gameThread: Thread

    init {
        preferredSize = Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)
        background = Color.black
        isDoubleBuffered = true
        isFocusable = true
        requestFocus()
        requestFocusInWindow()
        DebugLogic.isDebug = false
    }

    fun start() {
        if (!running) {
            running = true
            gameThread = Thread(this)
            gameThread.start()
        }
    }

    fun stop() {
        running = false
        try {
            gameThread.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D
        clear(g2)

        DebugLogic.debugPrintln("paintComponent called") // Para verificar que el método se está llamando

        render(g2)
    }

    private fun render(g2: Graphics2D) {
        if (gameLogic.currentScene.nodes.isEmpty()) {
            DebugLogic.debugPrintln("No nodes to render")
        } else {
            DebugLogic.debugPrintln("Rendering nodes:")
        }

        for (node in gameLogic.currentScene.nodes) {
            if (node is IDrawable) {
                node.draw(g2)
                DebugLogic.debugPrintln("Drawing node: ${node.javaClass.name}")
            } else {
                DebugLogic.debugPrintln("Node is not drawable: ${node.javaClass.name}")
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

    override fun run() {
        val drawInterval = 1_000_000_000L / fps // Intervalo de dibujo basado en FPS
        val updateInterval = 1_000_000_000L / 60 // Intervalo de actualización basado en 60 actualizaciones por segundo
        var nextDrawTime = System.nanoTime()
        var nextUpdateTime = System.nanoTime()
        var lastUpdateTime = System.nanoTime()

        while (running) {
            try {
                val now = System.nanoTime()

                // Calcular deltaTime
                val deltaTime = (now - lastUpdateTime) / 1_000_000_000.0f // Convertir nanosegundos a segundos

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
