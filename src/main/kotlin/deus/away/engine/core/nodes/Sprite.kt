package deus.away.engine.core.nodes

import deus.away.engine.core.systems.node.Node2D
import deus.away.engine.core.systems.saving.Keep
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.IOException
import javax.imageio.ImageIO

class Sprite(
    name: String = "Sprite2D",
    @Keep("path") val path: String,
    @Keep("twidth") val twidth: Int,
    @Keep("theight") val theight: Int
) : Node2D(name) {

    lateinit var image: BufferedImage

    init {
        loadImage(path)
    }

    private fun loadImage(path: String) {
        try {
            val resource = this::class.java.getResourceAsStream(path)
                ?: throw IllegalArgumentException("Path '$path' does not exist.")
            image = ImageIO.read(resource)
                ?: throw IOException("Failed to read image from path '$path'.")
        } catch (e: IllegalArgumentException) {
            println(e.message)
            // Handle the case where the path does not exist
        } catch (e: IOException) {
            println(e.message)
            // Handle the case where reading the image fails
        }
    }

    override fun draw(g2: Graphics2D) {
        if (this::image.isInitialized) {
            g2.drawImage(image, globalPosition.x.toInt(), globalPosition.y.toInt(), twidth, theight, null)
        } else {
            println("Image not loaded")
        }
    }
}
