package deus.away.engine.core.systems.node.nodes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import deus.away.engine.core.systems.node.Node2D
import deus.away.engine.core.systems.saving.Keep

class Sprite(
    name: String = "Sprite2D",
    @Keep("path") val path: String,
    @Keep("twidth") val twidth: Int,
    @Keep("theight") val theight: Int
) : Node2D(name) {

    private lateinit var texture: Texture

    init {
        loadTexture(path)
    }


    private fun loadTexture(path: String) {
        try {
            texture = Texture(Gdx.files.internal("assets/player.png"))
        } catch (e: Exception) {
            println("Failed to load texture from path '$path': ${e.message}")
            // Handle the case where loading the texture fails
        }
    }

    override fun draw(batch: SpriteBatch) {
        if (::texture.isInitialized) {
            batch.draw(texture, position.x, position.y, twidth.toFloat(), theight.toFloat())
        } else {
            println("Texture not loaded")
        }
    }

    override fun dispose() {
        if (::texture.isInitialized) {
            texture.dispose()
        }
    }
}
