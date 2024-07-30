package deus.away.engine.core.media.image

import deus.away.engine.core.media.SourceLoader
import java.awt.image.BufferedImage
import java.io.IOException

data class ImageAnimations(
    private val animations: MutableMap<String, MutableList<BufferedImage>> = mutableMapOf(),
    val animationSpeed: Float,
    val isLoop: Boolean
) {

    fun addAnimation(name: String, paths: Array<String>) {
        val images = mutableListOf<BufferedImage>()
        for (path in paths) {
            try {
                val image =
                    SourceLoader.loadImageResource(path) ?: throw IOException("Failed to read image from path '$path'.")
                images.add(image)
            } catch (e: IOException) {
                println("Error loading image '$path': ${e.message}")
            }
        }
        animations[name] = images
    }

    fun addAnimationFromCanvas(name: String, images: MutableList<BufferedImage>) {
        animations[name] = images
    }

    fun removeAnimation(name: String) {
        animations.remove(name)
    }

    fun getAnimation(name: String): List<BufferedImage>? {
        return animations[name]
    }

}

