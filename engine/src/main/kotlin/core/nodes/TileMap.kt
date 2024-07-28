package core.nodes

import core.maths.Vector2i
import core.systems.node.Node2D
import core.systems.tiled.TileAtlas
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import kotlin.math.atan

class TileMap(name: String, var atlas: TileAtlas) : Node2D(name) {

    private val tiles = mutableMapOf<Vector2i, BufferedImage>()

    fun setCell(id: Int, position: Vector2i) {
        val tile = atlas.tilesDataMap.find { it.id == id }
        if (tile != null) {
            tiles[position] = tile.image
        }
    }

    override fun draw(g2: Graphics2D) {
        super.draw(g2)
        tiles.forEach { (position, image) ->
            g2.drawImage(
                image,
                position.x * atlas.tileWidth * atlas.scale,
                position.y * atlas.tileHeight * atlas.scale,
                atlas.tileWidth * atlas.scale,
                atlas.tileHeight * atlas.scale,
                null
            )
        }
    }
}
