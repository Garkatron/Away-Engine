package nodes

import core.engine.controller.KeyboardListener
import core.engine.maths.Vector2
import core.systems.node.Node
import core.systems.node.Node2D
import core.systems.debug.DebugLogic
import java.awt.event.KeyEvent

class Controller(
    keyboardListener: KeyboardListener,
    private val stateMachine: StateMachine,
    name: String = "Controller"
) : Node(name) {

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
        DebugLogic.debugPrintln("Controller initialized.")
    }

    private fun handleKeyPress(keyCode: Int) {
        DebugLogic.debugPrintln("Key pressed: $keyCode")
    }

    private fun handleKeyRelease(keyCode: Int) {
        DebugLogic.debugPrintln("Key released: $keyCode")
        if (keysPressed.isEmpty()) {
            val newState = "IDLE_" + stateMachine.getCurrentState().substringAfter("_")
            stateMachine.changeState(newState)
            DebugLogic.debugPrintln("State changed to: $newState")
        }
    }

    override fun update(dt: Float) {
        val movementSpeed = 200f * dt
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
        stateMachine.changeState(baseState)

        DebugLogic.debugPrintln("Base state: $baseState")

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

        if (movementVector.magnitude() != 0f) {
            movementVector.normalize()
        }

        DebugLogic.debugPrintln("Movement vector: $movementVector")

        val player = parent as? Node2D
        if (player != null) {
            player.position += movementVector
            DebugLogic.debugPrintln("New position: ${player.globalPosition}")
        } else {
            DebugLogic.debugPrintln("Parent is not a Node2D, position update failed.")
        }
    }
}
