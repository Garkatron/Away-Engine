package core.image

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.IOException

class Sprite(path: String) {

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

    fun isImageLoaded(): Boolean {
        return this::image.isInitialized
    }
}
