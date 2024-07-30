package deus.away.engine.core.systems.scene

import deus.away.engine.core.interfaces.IDrawable
import deus.away.engine.core.interfaces.IUpdatable
import deus.away.engine.core.systems.node.Node
import deus.away.engine.core.systems.saving.Keep
import java.awt.Graphics2D

class Scene(@Keep("name") val name: String = "Scene") : IDrawable, IUpdatable {

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
