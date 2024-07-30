import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.GdxRuntimeException
import deus.away.engine.core.maths.Vector2i

object SpriteSplitter {

    /**
     * Extrae todos los tiles de una textura en función del ancho y alto del tile.
     */
    fun extractAllTilesFromTexture(texture: Texture, width: Int, height: Int): List<Texture> {
        val tiles = mutableListOf<Texture>()
        val columns = texture.width / width
        val rows = texture.height / height

        for (y in 0 until rows) {
            for (x in 0 until columns) {
                val region = TextureRegion(texture, x * width, y * height, width, height)
                tiles.add(regionToTexture(region))
            }
        }
        return tiles
    }

    /**
     * Extrae un número específico de tiles de una textura según las columnas y filas especificadas.
     */
    fun extractTilesFromTexture(
        texture: Texture,
        width: Int,
        height: Int,
        columns: Int,
        rows: Int
    ): List<Texture> {

        val _columns = texture.width / width
        val _rows = texture.height / height

        if (rows > _rows || columns > _columns) {
            throw IllegalArgumentException("Rows or Columns are bigger than the texture")
        }

        val tiles = mutableListOf<Texture>()
        for (y in 0 until rows) {
            for (x in 0 until columns) {
                val region = TextureRegion(texture, x * width, y * height, width, height)
                tiles.add(regionToTexture(region))
            }
        }
        return tiles
    }

    /**
     * Extrae tiles específicos de una textura basados en una lista de coordenadas.
     */
    fun extractSpecificTilesFromTexture(
        texture: Texture,
        coords: List<Vector2i>,
        width: Int,
        height: Int
    ): List<Texture> {

        val _columns = texture.width / width
        val _rows = texture.height / height

        if (coords.any { it.x >= _columns || it.y >= _rows }) {
            throw IllegalArgumentException("Coordinates are out of bounds")
        }

        val tiles = mutableListOf<Texture>()
        for (pos in coords) {
            val region = TextureRegion(texture, pos.x * width, pos.y * height, width, height)
            tiles.add(regionToTexture(region))
        }

        return tiles
    }

    /**
     * Extrae un tile específico de una textura basándose en una coordenada específica.
     */
    fun extractSpecificTileFromTexture(
        texture: Texture,
        coord: Vector2i,
        tileWidth: Int,
        tileHeight: Int
    ): Texture {

        val columns = texture.width / tileWidth
        val rows = texture.height / tileHeight

        if (coord.y >= rows || coord.x >= columns) {
            throw IllegalArgumentException("Coordinates are out of bounds")
        }

        val region = TextureRegion(texture, coord.x * tileWidth, coord.y * tileHeight, tileWidth, tileHeight)
        return regionToTexture(region)
    }

    /**
     * Convierte un TextureRegion en un Texture.
     */
    private fun regionToTexture(region: TextureRegion): Texture {
        // Crea un Pixmap del tamaño de la región
        val pixmap = Pixmap(region.regionWidth, region.regionHeight, Pixmap.Format.RGBA8888)

        // Copia los datos de la región al Pixmap
        pixmap.drawPixmap(region.texture.getTextureData().consumePixmap(),
            -region.regionX, -region.regionY,
            region.regionWidth, region.regionHeight,
            0, 0,
            region.regionWidth, region.regionHeight
        )

        // Crea una nueva textura desde el Pixmap
        val texture = Texture(pixmap)

        // Libera el Pixmap, ya que los datos están ahora en la Textura
        pixmap.dispose()

        return texture
    }
}
