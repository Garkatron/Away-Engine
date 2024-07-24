package core.image

import java.awt.image.BufferedImage
import java.io.IOException
import javax.imageio.ImageIO

class AnimatedSprite(private val paths: Array<String>) {

    private val images = mutableListOf<BufferedImage>()

    init {
        loadImages(paths)
    }

    private fun loadImages(paths: Array<String>) {
        for (path in paths) {
            try {
                val resource = this::class.java.getResourceAsStream(path)
                    ?: throw IllegalArgumentException("Path '$path' does not exist.")
                val image = ImageIO.read(resource)
                    ?: throw IOException("Failed to read image from path '$path'.")
                images.add(image)
            } catch (e: IllegalArgumentException) {
                println(e.message)
                // Handle the case where the path does not exist
            } catch (e: IOException) {
                println(e.message)
                // Handle the case where reading the image fails
            }
        }
    }

    fun isImagesLoaded(): Boolean {
        return images.size == paths.size
    }
}
