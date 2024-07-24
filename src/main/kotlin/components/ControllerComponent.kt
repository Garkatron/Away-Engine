package components

import core.GameObject
import core.component.Component
import core.controller.KeyboardListener
import core.maths.Vector2
import java.awt.event.KeyEvent

class ControllerComponent(
    keyboardListener: KeyboardListener,
    private val positionComponent: PositionComponent,
    name: String
) : Component<GameObject>(name) {

    private val keysPressed = mutableSetOf<Int>()

    init {
        keyboardListener.keyDown.connect { keyCode ->
            keysPressed.add(keyCode)
        }
        keyboardListener.keyUp.connect { keyCode ->
            keysPressed.remove(keyCode)
        }
        // No need to handle keyPressed in this context
    }

    override fun update(deltaTime: Float) {
        val movementSpeed = 300f * deltaTime

        if (keysPressed.contains(KeyEvent.VK_W)) {
            positionComponent.position.y -= movementSpeed
        }
        if (keysPressed.contains(KeyEvent.VK_S)) {
            positionComponent.position.y += movementSpeed
        }
        if (keysPressed.contains(KeyEvent.VK_A)) {
            positionComponent.position.x -= movementSpeed
        }
        if (keysPressed.contains(KeyEvent.VK_D)) {
            positionComponent.position.x += movementSpeed
        }

    }
}
