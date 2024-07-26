package core.engine.image

import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.io.InputStream
import javax.imageio.ImageIO

object SourceLoader {

    fun loadImage(path: String): BufferedImage {
        return ImageIO.read(File(path))
    }

    fun loadImageResource(path: String): BufferedImage? {
        return try {
            // Load the resource as an InputStream
            val resourceStream: InputStream? = this::class.java.getResourceAsStream(path)
            if (resourceStream != null) {
                // Read the image from the InputStream
                ImageIO.read(resourceStream)
            } else {
                println("Resource not found: $path")
                null
            }
        } catch (e: IOException) {
            println("Error reading image from path '$path': ${e.message}")
            null
        }
    }
}