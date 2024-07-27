package core.systems.scene

import core.engine.interfaces.IDrawable
import core.interfaces.IUpdatable
import core.systems.node.Node
import java.awt.Graphics2D

class Scene(name: String = "Scene") : IDrawable, IUpdatable {

    val nodes: MutableList<Node> = mutableListOf()

    override fun draw(g2: Graphics2D) {
        for (node in nodes) {
            if (node is IDrawable) {
                node.draw(g2)
            }
        }
    }

    override fun update(dt: Float) {
        for (node in nodes) {
            node.update(dt)
        }
    }

    fun addNode(node: Node) {
        nodes.add(node)
    }
}
