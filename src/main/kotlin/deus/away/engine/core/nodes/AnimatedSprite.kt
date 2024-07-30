package deus.away.engine.core.nodes

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import deus.away.engine.core.media.image.ImageAnimations
import deus.away.engine.core.systems.debug.DebugLogic
import deus.away.engine.core.systems.node.Node2D
import deus.away.engine.core.systems.saving.Keep
import deus.away.engine.core.systems.signal.Signal
import java.awt.image.BufferedImage

class AnimatedSprite(
    name: String = "AnimatedSprite",
    private val imageAnimations: ImageAnimations,
    @Keep("defaultAnimation")
    private val defaultAnimation: String,

    @Keep("twidth") val twidth: Int, @Keep("theight") val theight: Int
) : Node2D(name) {

    @Keep("animSpeed")
    var animSpeed: Float = imageAnimations.animationSpeed

    @Keep("currentAnimation")
    var currentAnimation: String = defaultAnimation

    @Keep("currentSpriteIndex")
    private var currentSpriteIndex: Float = 0f
    var currentImage: Texture? = null

    @Keep("isAnimating")
    var isAnimating: Boolean = false

    // Signals
    var onFinished = Signal<String>()
    var onStart = Signal<String>()
    var onFrameChange = Signal<Int>()

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
            onFrameChange.emit(currentSpriteIndex.toInt())
            DebugLogic.debugPrintln("Current frame index: $currentSpriteIndex")

        }
    }

    fun isImagesLoaded(): Boolean {
        return imageAnimations.getAnimation(currentAnimation)?.isNotEmpty() == true
    }

    override fun draw(batch: SpriteBatch) {
        super.draw(batch)
        if (currentImage!=null) {
            batch.draw(currentImage, globalPosition.x, globalPosition.y, twidth.toFloat(), theight.toFloat())
        } else {
            println("Texture not loaded")
        }
    }
}
