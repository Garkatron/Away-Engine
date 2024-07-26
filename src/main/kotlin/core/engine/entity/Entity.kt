package core.engine.entity

import core.GameObject
import core.engine.signal.Signal

open class Entity : GameObject() {
    val onDead = Signal<Unit>()
    val onHurt = Signal<Unit>()
    val isDead: Boolean = false

    override fun update(dt: Float) {
        componentManager.update(dt)
    }
}