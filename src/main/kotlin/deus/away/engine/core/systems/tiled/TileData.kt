package deus.away.engine.core.systems.tiled

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import deus.away.engine.core.maths.Vector2i
import core.systems.collision.Collision

data class TileData(val id: Int, val position: Vector2i, val image: Texture, val collision: Collision?)