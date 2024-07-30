package test

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import deus.away.engine.core.systems.logic.Logic
import deus.away.engine.core.systems.node.scene.SceneManager

class GameScreen(
    private val sceneManager: SceneManager,
    private val logic: Logic
) : ScreenAdapter() {
    private lateinit var spriteBatch: SpriteBatch

    override fun show() {
        spriteBatch = SpriteBatch()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        logic.render() // Llama al método render de Logic
    }

    override fun resize(width: Int, height: Int) {
        logic.resize(width, height)
    }

    override fun hide() {
        spriteBatch.dispose()

    }
}
