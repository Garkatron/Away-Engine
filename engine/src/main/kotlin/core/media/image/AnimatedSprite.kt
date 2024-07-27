package core.engine.media.image

import core.engine.signal.Signal
import java.awt.image.BufferedImage

class AnimatedSprite(
    private val imageAnimations: ImageAnimations,
    private val defaultAnimation: String,
) {

    var animSpeed: Float = imageAnimations.animationSpeed
    var currentAnimation: String = defaultAnimation
    private var currentSpriteIndex: Float = 0f
    var currentImage: BufferedImage? = null
    var isAnimating: Boolean = false
    var onFinished = Signal<String>()
    var onStart = Signal<String>()

    init {
        if (imageAnimations.getAnimation(defaultAnimation).isNullOrEmpty()) {
            throw IllegalArgumentException("Default animation '$defaultAnimation' is not available.")
        }
        currentImage = imageAnimations.getAnimation(defaultAnimation)?.first()
    }

    fun setAnimation(name: String) {
        if (imageAnimations.getAnimation(name).isNullOrEmpty()) {
            throw IllegalArgumentException("Animation '$name' is not available.")
        }
        currentAnimation = name
        currentSpriteIndex = 0f
        currentImage = imageAnimations.getAnimation(name)?.first()
    }

    fun animate() {
        isAnimating = true
        onStart.emit(currentAnimation)
    }

    fun update() {
        if (isAnimating) {
            val frames = imageAnimations.getAnimation(currentAnimation)
            if (frames.isNullOrEmpty()) return

            currentSpriteIndex += animSpeed
            if (currentSpriteIndex >= frames.size) {
                if (imageAnimations.isLoop) {
                    currentSpriteIndex = 0f
                    onFinished.emit(currentAnimation)

                } else {
                    currentSpriteIndex = (frames.size - 1).toFloat()
                    isAnimating = false
                    onFinished.emit(currentAnimation)
                }
            }

            currentImage = frames[currentSpriteIndex.toInt()]
        }
    }

    fun isImagesLoaded(): Boolean {
        return imageAnimations.getAnimation(currentAnimation)?.isNotEmpty() == true
    }
}
