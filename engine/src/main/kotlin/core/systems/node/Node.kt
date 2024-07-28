package core.systems.node

import core.interfaces.IUpdatable

open class Node(val name: String) : IUpdatable {
    private val children = mutableListOf<Node>()
    var parent: Node? = null

    fun getChildren(): List<Node> {
        return children
    }

    open fun addChildren(vararg nodes: Node) {
        for (node in nodes) {
            node.parent = this
            children.add(node)
            println("Added child: ${node.name} to parent: ${this.name}")
            (node as? Node2D)?.updateGlobalPosition()
        }
    }

    open fun addChild(node: Node) {
        node.parent = this
        children.add(node)
        println("Added child: ${node.name} to parent: ${this.name}")
        (node as? Node2D)?.updateGlobalPosition()
    }

    open fun removeChild(node: Node) {
        node.parent = null
        children.remove(node)
        println("Removed child: ${node.name} from parent: ${this.name}")
    }

    override fun update(dt: Float) {
        for (child in children) {
            (child as? IUpdatable)?.update(dt)
        }
    }
}
