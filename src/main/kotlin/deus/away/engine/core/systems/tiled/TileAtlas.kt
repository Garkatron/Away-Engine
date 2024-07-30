package deus.away.engine.core.systems.tiled

import com.badlogic.gdx.graphics.Texture
import deus.away.engine.core.maths.Vector2i
import core.systems.collision.Collision

class TileAtlas(val tileWidth: Int, val tileHeight: Int, var atlas: Texture) {

    var scale: Int = 1

    var tilesDataMap = mutableListOf<TileData>()

    fun setTileAt(id: Int, atlas_position: Vector2i, collision: Collision = Collision.Square) {
        tilesDataMap.add(
            TileData(id, Vector2i(0,0), SpriteSplitter.extractSpecificTileFromTexture(atlas,atlas_position,tileWidth,tileHeight), collision)
        )
    }

}