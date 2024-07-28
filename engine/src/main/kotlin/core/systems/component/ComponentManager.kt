import core.engine.signal.Signal
import core.systems.node.Node

class ComponentManager(name: String) : Node(name) {

    val onRemovecomponent = Signal<String>()
    val onDeactivatecomponent = Signal<String>()
    val onActivatecomponent = Signal<String>()
    val onAddComponent = Signal<Node>()

    fun search(nodeName: String): Component? {
        return getChildren().find { it -> it.name == nodeName } as? Component
    }

    fun deactivate(nodeName: String) {
        search(nodeName)?.setActive(false)
        onDeactivatecomponent.emit(nodeName)
    }

    fun activate(nodeName: String) {
        search(nodeName)?.setActive(true)
        onActivatecomponent.emit(nodeName)
    }

    fun remove(nodeName: String) {
        search(nodeName)?.let { removeChild(it) }
        onRemovecomponent.emit(nodeName)
    }

    fun addComponent(component: Component) {
        addChild(component)
        onAddComponent.emit(component)
    }

    fun getActiveComponents(): List<Component> {
        return getChildren().filterIsInstance<Component>().filter { it.isActive() }
    }

}
