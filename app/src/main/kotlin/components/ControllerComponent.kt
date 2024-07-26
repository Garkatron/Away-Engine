package components

import core.engine.`object`.GameObject
import core.engine.component.Component
import core.engine.controller.KeyboardListener
import core.engine.maths.Vector2
import java.awt.event.KeyEvent

class ControllerComponent(
    keyboardListener: KeyboardListener,
    private val positionComponent: PositionComponent,
    private val stateMachineComponent: StateMachineComponent,
    name: String
) : Component<GameObject>(name) {

    val keysPressed = mutableSetOf<Int>()

    val directionMappings = mapOf(
        KeyEvent.VK_W to "UP",
        KeyEvent.VK_S to "DOWN",
        KeyEvent.VK_A to "LEFT",
        KeyEvent.VK_D to "RIGHT"
    )

    init {
        keyboardListener.keyDown.connect { keyCode ->
            keysPressed.add(keyCode)
            handleKeyPress(keyCode)
        }
        keyboardListener.keyUp.connect { keyCode ->
            keysPressed.remove(keyCode)
            handleKeyRelease(keyCode)
        }
        // No need to handle keyPressed in this context
    }

    private fun handleKeyPress(keyCode: Int) {
    }

    private fun handleKeyRelease(keyCode: Int) {
        if (keysPressed.isEmpty()) {
            stateMachineComponent.changeState("IDLE_"+stateMachineComponent.getCurrentState().substringAfter("_"))
        }
    }

    override fun update(deltaTime: Float) {
        val movementSpeed = 200f * deltaTime
        val movementVector = Vector2(0f, 0f)

        val pressedDirections = directionMappings.filter { keysPressed.contains(it.key) }
            .map { it.value }
            .sortedBy {
                when (it) {
                    "UP" -> 1
                    "DOWN" -> 2
                    "LEFT" -> 3
                    "RIGHT" -> 4
                    else -> 5
                }
            }

        val baseState = if (pressedDirections.isNotEmpty()) "WALK_${pressedDirections.joinToString("_")}" else "IDLE"

        stateMachineComponent.changeState(baseState)

        movementVector.set(
            when {
                pressedDirections.contains("LEFT") && pressedDirections.contains("RIGHT") -> 0f
                pressedDirections.contains("RIGHT") -> movementSpeed
                pressedDirections.contains("LEFT") -> -movementSpeed
                else -> 0f
            },
            when {
                pressedDirections.contains("UP") && pressedDirections.contains("DOWN") -> 0f
                pressedDirections.contains("UP") -> -movementSpeed
                pressedDirections.contains("DOWN") -> movementSpeed
                else -> 0f
            }
        )

        if (movementVector.magnitude() != 0f) { // Evita la normalización de un vector cero
            movementVector.normalize()
        }

        positionComponent.position += movementVector
    }


}
