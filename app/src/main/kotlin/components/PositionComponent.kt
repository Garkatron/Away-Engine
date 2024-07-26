package components

import core.engine.`object`.GameObject
import core.engine.component.Component
import core.engine.maths.Vector2

class PositionComponent(initialPos: Vector2, name: String) : Component<GameObject>(name) {

    // Private property for position
    var position: Vector2 = initialPos

    // Method to move the position by a delta vector
    fun move(delta: Vector2) {
        position += delta
    }

    // Override the update method from the Component class
    override fun update(deltaTime: Float) {


    }
}