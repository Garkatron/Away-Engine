package deus.away.engine.core.systems.render

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import deus.away.engine.core.systems.controller.KeyboardListener
import deus.away.engine.core.interfaces.IDrawable
import deus.away.engine.core.systems.debug.DebugLogic
import deus.away.engine.core.systems.node.scene.Scene
import java.awt.Color
import java.awt.Graphics2D

class Render(val fps: Int) : Screen {

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
    private val batch = SpriteBatch()


    init {
        Gdx.graphics.setWindowedMode(SCREEN_WIDTH, SCREEN_HEIGHT)
        DebugLogic.isDebug = false
    }

    override fun show() {

    }

    override fun render(p0: Float) {
        Gdx.gl.glClearColor(0f,0f, 0F,1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.begin()
        currentScene?.let {
            if (it.nodes.isEmpty()) {
                DebugLogic.debugPrintln("No nodes to render")
            } else {

                DebugLogic.debugPrintln("Rendering nodes:")
                for (node in it.nodes) {
                    if (node is IDrawable) {
                        node.draw(batch)
                        DebugLogic.debugPrintln("Drawing node: ${node.javaClass.name}")
                    } else {
                        DebugLogic.debugPrintln("Node is not drawable: ${node.javaClass.name}")
                    }
                }
            }
        }
        batch.end()

    }


    override fun resize(width: Int, height: Int) {}
    override fun pause() {}
    override fun resume() {}
    override fun hide() {}
    override fun dispose() {
        batch.dispose()
    }
}
