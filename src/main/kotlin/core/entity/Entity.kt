package core.entity

import core.GameObject
import core.component.ComponentManager
import core.signal.Signal

class Entity : GameObject() {
    val onDead = Signal<Unit>()
    val onHurt = Signal<Unit>()
    val isDead: Boolean = false

    override fun update(dt: Float) {
        componentManager.update(dt)
    }
}