package core.engine.component

import core.GameObject


abstract class Component<T>( val name: String) {
    lateinit var ref: GameObject
    abstract fun update(deltaTime: Float)

    open fun initialize() {

    }
}
