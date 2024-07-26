package components

import core.GameObject
import core.engine.component.Component
import core.engine.signal.Signal

class StateMachineComponent(
    name: String,
    newStates: MutableList<String> = mutableListOf()  // Valor por defecto para newStates
) : Component<GameObject>(name) {

    val onStateChange: Signal<String> = Signal()
    private var currentState: String = "_"
    private val states = mutableListOf("_")

    init {
        if (newStates.isNotEmpty()) {
            newStates.forEach { element -> addState(element) }
        }
    }

    fun addState(key: String) {
        if (states.contains(key)) return
        states.add(key)
    }

    fun changeState(newState: String) {
        if (states.contains(newState)) {
           if (currentState!=newState){
               onStateChange.emit(newState)
               currentState = newState
           }
        }
    }

    fun getCurrentState(): String {
        return currentState
    }

    override fun update(deltaTime: Float) {

    }
}
