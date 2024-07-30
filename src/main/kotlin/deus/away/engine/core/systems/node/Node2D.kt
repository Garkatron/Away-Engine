package deus.away.engine.core.systems.node

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import deus.away.engine.core.interfaces.IDrawableNode
import deus.away.engine.core.maths.Vector2
import deus.away.engine.core.systems.node.saving.Keep

open class Node2D(name: String = "Node2D") : Node(name), IDrawableNode {

    @Keep("position")
    var position: Vector2 = Vector2(0f, 0f)
        set(value) {
            field = value
            updateGlobalPosition()
        }

    @Keep("globalPosition")
    var globalPosition: Vector2 = Vector2(0f, 0f)
        private set

    fun updateGlobalPosition() {
        globalPosition = if (parent is Node2D) {
            (parent as Node2D).globalPosition + position
        } else {
            position
        }
        // Update the global position of all children
        for (child in getChildren()) {
            if (child is Node2D) {
                child.updateGlobalPosition()
            }
        }
    }

    override fun addChild(node: Node) {
        super.addChild(node)
        if (node is Node2D) {
            node.updateGlobalPosition()
        }
    }

    override fun removeChild(node: Node) {
        super.removeChild(node)
        // No need to update position here unless the node's global position
        // is used after removal, which typically isn't the case.
    }

    override fun draw(batch: SpriteBatch) {
        // Draw current node's visuals here if needed
        for (node in getChildren()) {
            if (node is IDrawableNode) {
                node.draw(batch)
            }
        }
    }

    override fun dispose() {

    }

    override fun disposeChildren() {
        getChildren().forEach { n ->
            if (n is IDrawableNode) {
                n.disposeChildren()
                n.dispose()
            }

        }
    }

}
