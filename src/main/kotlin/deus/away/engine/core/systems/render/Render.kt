package deus.away.engine.core.systems.render

import deus.away.engine.core.systems.controller.KeyboardListener
import deus.away.engine.core.interfaces.IDrawable
import deus.away.engine.core.systems.debug.DebugLogic
import deus.away.engine.core.systems.scene.Scene
import java.awt.Color
import javax.swing.JPanel
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D

class Render(val fps: Int) : JPanel() {

    companion object {
        private const val TILE_SIZE: Int = 16
        private const val SCALE_FACTOR: Int = 3
        const val RENDER_TILE_SIZE: Int = TILE_SIZE * SCALE_FACTOR
        private const val MAX_SCREEN_COL = 16
        private const val MAX_SCREEN_ROW = 16
        private const val SCREEN_WIDTH = RENDER_TILE_SIZE * MAX_SCREEN_COL
        private const val SCREEN_HEIGHT = RENDER_TILE_SIZE * MAX_SCREEN_ROW
    }

    var currentScene: Scene? = null


    init {
        preferredSize = Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)
        background = Color.black
        isDoubleBuffered = true
        isFocusable = true
        requestFocus()
        requestFocusInWindow()

        DebugLogic.isDebug = false
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D
        clear(g2)
        render(g2)
    }

    private fun render(g2: Graphics2D) {
        currentScene?.let {
            if (it.nodes.isEmpty()) {
                DebugLogic.debugPrintln("No nodes to render")
            } else {

                DebugLogic.debugPrintln("Rendering nodes:")
                for (node in it.nodes) {
                    if (node is IDrawable) {
                        node.draw(g2)
                        DebugLogic.debugPrintln("Drawing node: ${node.javaClass.name}")
                    } else {
                        DebugLogic.debugPrintln("Node is not drawable: ${node.javaClass.name}")
                    }
                }
            }
        }
    }

    private fun clear(g2: Graphics2D) {
        g2.color = Color.black
        g2.fillRect(0, 0, width, height)
    }

    fun addKeyboardListener(k: KeyboardListener) {
        addKeyListener(k)
    }
}
