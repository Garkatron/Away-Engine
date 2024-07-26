package components

import core.GameObject
import core.engine.component.Component
import core.engine.controller.KeyboardListener

class PlayerAnimationManagerComponent(
    name: String,
    private val animatedSpriteComponent: AnimatedSpriteComponent,
    private val keyboardListener: KeyboardListener
) : Component<GameObject>(name) {

    private val keysPressed = mutableSetOf<Int>()
    private val keyAnimationMap = mutableMapOf<Int, String>() // Map of key codes to animation names

    // Current animation being played
    private var currentAnimation: String = "DEFAULT"

    // Initialize key listener
    init {
        keyboardListener.keyDown.connect { keyCode ->
            keysPressed.add(keyCode)
            handleKeyPress(keyCode)
        }
        keyboardListener.keyUp.connect { keyCode ->
            keysPressed.remove(keyCode)
            handleKeyRelease(keyCode)
        }
    }

    // Method to set key-to-animation mappings
    fun setKeyAnimationMapping(keyCode: Int, animationName: String) {
        keyAnimationMap[keyCode] = animationName
    }

    private fun handleKeyPress(keyCode: Int) {
        val animationName = keyAnimationMap[keyCode]
        if (animationName != null && animationName != currentAnimation) {
            startAnimation(animationName)
        }
    }

    private fun handleKeyRelease(keyCode: Int) {
        if (keysPressed.isEmpty()) {
            startAnimation("DEFAULT")
        }
    }

    private fun startAnimation(animationName: String) {
        // Only animate if the animation is different from the current one
        if (animationName != currentAnimation) {
            animatedSpriteComponent.animate(animationName)
            currentAnimation = animationName
        }
    }

    override fun update(deltaTime: Float) {

    }
}
