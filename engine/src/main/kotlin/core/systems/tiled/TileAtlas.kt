package core.systems.tiled

import core.engine.media.SpriteSplitter
import core.maths.Vector2i
import core.systems.collision.Collision
import java.awt.image.BufferedImage

class TileAtlas(val tileWidth: Int, val tileHeight: Int, var atlas: BufferedImage) {

    var tiles = mutableListOf<TileData>()

    fun setTileAt(id: Int, position: Vector2i, collision: Collision = Collision.Square) {
        tiles.add(
            TileData(id, position, SpriteSplitter.extractSpecificTilesFromFile(atlas,
                mutableListOf(position),tileWidth,tileHeight,0,0)[0], collision)
        )
    }

}