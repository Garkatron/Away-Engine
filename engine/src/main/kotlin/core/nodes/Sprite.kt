package core.nodes

import core.engine.interfaces.IDrawable
import core.systems.node.Node2D
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.IOException

class Sprite(name: String, path: String, val twidth: Int, val theight: Int) : Node2D(name) {

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
