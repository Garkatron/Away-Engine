package core.engine.`object`

import core.engine.component.ComponentManager

abstract class GameObject {
    val componentManager = ComponentManager(this)
    abstract fun update(dt: Float)
}
