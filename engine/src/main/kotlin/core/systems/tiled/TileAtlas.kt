package core.systems.tiled

import core.engine.media.SpriteSplitter
import core.maths.Vector2i
import core.systems.collision.Collision
import java.awt.image.BufferedImage

class TileAtlas(val tileWidth: Int, val tileHeight: Int, var atlas: BufferedImage) {

    var scale: Int = 1

    var tilesDataMap = mutableListOf<TileData>()

    fun setTileAt(id: Int, atlas_position: Vector2i, collision: Collision = Collision.Square) {
        tilesDataMap.add(
            TileData(id, Vector2i(0,0), SpriteSplitter.extractSpecificTileFromFile(atlas,atlas_position,tileWidth,tileHeight), collision)
        )
    }

}