package core.engine.image

import core.engine.maths.Vector2i
import java.awt.image.BufferedImage

object SpriteSplitter {

    fun extractAllTilesFromFile(image: BufferedImage, width: Int, height: Int): List<BufferedImage> {
        val tiles = mutableListOf<BufferedImage>()
        val columns = image.width / width
        val rows = image.height / height

        for (y in 0 until rows) {
            for (x in 0 until columns) {
                tiles.add(
                    image.getSubimage(x * width, y * height, width, height)
                )
            }
        }
        return tiles
    }

    fun extractTilesFromFile(image: BufferedImage, width: Int, height: Int, columns: Int, rows: Int): List<BufferedImage> {

        val _columns = image.width / width
        val _rows = image.height / height

        if (rows > _rows || columns > _columns) {
            throw IllegalArgumentException("Rows or Columns are bigger than the image")
        }

        val tiles = mutableListOf<BufferedImage>()
        for (y in 0 until rows) {
            for (x in 0 until columns) {
                tiles.add(
                    image.getSubimage(x * width, y * height, width, height)
                )
            }
        }
        return tiles
    }

    fun extractSpecificTilesFromFile(image: BufferedImage, coords: List<Vector2i>, width: Int, height: Int, columns: Int, rows: Int): List<BufferedImage> {

        val _columns = image.width / width
        val _rows = image.height / height

        if (rows > _rows || columns > _columns) {
            throw IllegalArgumentException("Rows or Columns are bigger than the image")
        }

        val tiles = mutableListOf<BufferedImage>()
        for (pos in coords){
            tiles.add(
                image.getSubimage(pos.x * width, pos.y * height, width, height)
            )
        }

        return tiles
    }
}
