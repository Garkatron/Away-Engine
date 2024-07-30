package deus.away.engine.core.nodes

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import deus.away.engine.core.maths.Vector2i
import deus.away.engine.core.systems.node.Node2D
import deus.away.engine.core.systems.tiled.TileAtlas
import java.awt.image.BufferedImage

class TileMap(name: String, var atlas: TileAtlas) : Node2D(name) {

    private val tiles = mutableMapOf<Vector2i, Texture>()

    fun setCell(id: Int, position: Vector2i) {
        val tile = atlas.tilesDataMap.find { it.id == id }
        if (tile != null) {
            tiles[position] = tile.image
        }
    }

    override fun draw(batch: SpriteBatch) {
        super.draw(batch)
        tiles.forEach { (position, image) ->
            batch.draw(
                image,
                (position.x * atlas.tileWidth * atlas.scale).toFloat(),
                (position.y * atlas.tileHeight * atlas.scale).toFloat(),
                (atlas.tileWidth * atlas.scale).toFloat(),
                (atlas.tileHeight * atlas.scale).toFloat(),
            )
        }
    }
}
