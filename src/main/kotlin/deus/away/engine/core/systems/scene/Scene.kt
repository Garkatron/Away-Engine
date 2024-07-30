package deus.away.engine.core.systems.scene

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import deus.away.engine.core.interfaces.IDrawable
import deus.away.engine.core.interfaces.IDrawableNode
import deus.away.engine.core.interfaces.IUpdatable
import deus.away.engine.core.systems.node.Node
import deus.away.engine.core.systems.saving.Keep

class Scene(@Keep("name") val name: String = "Scene") : IDrawable, IUpdatable {

    val nodes: MutableList<Node> = mutableListOf()


    override fun draw(batch: SpriteBatch) {
        for (node in nodes) {
            if (node is IDrawableNode) {
                node.draw(batch)
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

    override fun dispose() {
        nodes.forEach { n->
            if (n is IDrawableNode) {
                n.disposeChildren()
                n.dispose()
            }
        }
    }

}
