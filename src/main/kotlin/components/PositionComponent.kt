package components

import core.GameObject
import core.component.Component
import core.maths.Vector2

class PositionComponent(initialPos: Vector2, name: String) : Component<GameObject>(name) {

    // Private property for position
    private var position: Vector2 = initialPos

    // Public getter for position
    fun getPosition(): Vector2 = position

    // Public method to set position
    fun setPosition(newPosition: Vector2) {
        position = newPosition
    }

    // Method to move the position by a delta vector
    fun move(delta: Vector2) {
        position += delta
    }

    // Override the update method from the Component class
    override fun update(deltaTime: Float) {
        // TODO: Implement update logic based on deltaTime
        move(Vector2(3f,0f))
    }
}