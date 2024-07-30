package deus.away.engine.core.interfaces

import com.badlogic.gdx.graphics.g2d.SpriteBatch

interface IDrawable {
    fun draw(batch: SpriteBatch)
    fun dispose()
}