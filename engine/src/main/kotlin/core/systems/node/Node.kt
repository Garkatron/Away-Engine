package core.systems.node

import core.engine.signal.Signal
import core.interfaces.IUpdatable

abstract class Node(val name: String = "Node") : IUpdatable {
    private val children: MutableList<Node> = mutableListOf()
    var parent: Node? = null
        private set

    // Signals
    val onChildAdded = Signal<Node>()
    val onChildRemoved = Signal<Node>()

    open fun addChild(node: Node) {
        if (node.parent != null) {
            println("The node is already a child of another node.")
            return
        }
        if (!children.any { it == node }) {
            children.add(node)
            node.parent = this

            onChildAdded.emit(node)

        } else {
            println("Node already exists as a child.")
        }
    }

    fun getChild(index: Int): Node {
        return children[index]
    }

    open fun removeChild(node: Node) {
        children.remove(node)

        onChildRemoved.emit(node)
    }

    fun removeChild(index: Int) {
        if (index in 0 until children.size) {
            children.removeAt(index)
        } else {
            println("Index out of bounds.")
        }
    }

    fun clearChildren() {
        children.clear()
    }

    fun getChildrenCount(): Int {
        return children.size
    }

    fun getChildren(): List<Node> {
        return children.toList() // Returns an immutable copy of the children list
    }

    fun getParent() {

    }
}
