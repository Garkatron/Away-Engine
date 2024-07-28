package core.nodes

import core.engine.maths.Vector2
import core.engine.media.image.ImageAnimations
import core.engine.signal.Signal
import core.systems.debug.DebugLogic
import core.systems.node.Node2D
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class AnimatedSprite(
    name: String = "AnimatedSprite",
    private val imageAnimations: ImageAnimations,
    private val defaultAnimation: String,
    val twidth: Int, val theight: Int
) : Node2D(name) {

    var animSpeed: Float = imageAnimations.animationSpeed
    var currentAnimation: String = defaultAnimation
    private var currentSpriteIndex: Float = 0f
    var currentImage: BufferedImage? = null
    var isAnimating: Boolean = false

    // Signals
    var onFinished = Signal<String>()
    var onStart = Signal<String>()

    init {
        if (imageAnimations.getAnimation(defaultAnimation).isNullOrEmpty()) {
            throw IllegalArgumentException("Default animation '$defaultAnimation' is not available.")
        }
        currentImage = imageAnimations.getAnimation(defaultAnimation)?.first()
        animate()
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

    override fun update(dt: Float) {
        super.update(dt)
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
            DebugLogic.debugPrintln("Current frame index: $currentSpriteIndex")

        }
    }

    fun isImagesLoaded(): Boolean {
        return imageAnimations.getAnimation(currentAnimation)?.isNotEmpty() == true
    }

    override fun draw(g2: Graphics2D) {
        super.draw(g2)
        DebugLogic.debugPrintln("Drawing sprite at: ${globalPosition.x}, ${globalPosition.y}")
        g2.drawImage(currentImage, globalPosition.x.toInt(), globalPosition.y.toInt(), twidth, theight, null)
    }
}
