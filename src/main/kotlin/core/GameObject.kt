package core

import core.component.ComponentManager

abstract class GameObject {
    val componentManager = ComponentManager(this)

    abstract fun update(dt: Float)
}
