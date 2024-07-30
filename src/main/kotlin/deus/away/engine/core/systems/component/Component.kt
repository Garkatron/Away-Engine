import deus.away.engine.core.systems.node.Node
import deus.away.engine.core.systems.signal.Signal

class Component(name: String, private var ref: Node, val activity: (n: Node) -> Unit) : Node(name) {

    val onActivated = Signal<Unit>()
    val onDeactivated = Signal<Unit>()

    private var isActive = true

    fun setRef(node: Node) {
        ref = node
    }

    fun getRef(): Node = ref

    fun setActive(active: Boolean) {
        isActive = active
        if (active) {
            onActivated.emit(Unit)
        } else {
            onDeactivated.emit(Unit)
        }
    }

    fun isActive(): Boolean = isActive

    override fun update(dt: Float) {
        super.update(dt)
        if (isActive) {
            activity(ref)
        }
    }
}
