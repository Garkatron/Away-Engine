package core.systems.node

import core.engine.interfaces.IDrawable
import core.engine.maths.Vector2
import core.interfaces.IUpdatable
import java.awt.Graphics2D

open class Node2D(name: String = "Node2D") : Node(name), IUpdatable, IDrawable {

    private var updatingPosition = false
    private var updatingGlobalPosition = false

    var position: Vector2 = Vector2(0f, 0f)
        set(value) {
            if (!updatingPosition) {
                field = value
                updateGlobalPosition()
            }
        }

    var globalPosition: Vector2 = Vector2(0f, 0f)
        set(value) {
            if (!updatingGlobalPosition) {
                field = value
                updateLocalPosition()
                // Propagar la actualización de la posición global a los hijos
                for (child in getChildren()) {
                    if (child is Node2D) {
                        child.updateGlobalPosition()
                    }
                }
            }
        }

    override fun update(dt: Float) {
        getChildren().forEach { (it as IUpdatable).update(dt) }
    }

    private fun updateGlobalPosition() {
        updatingGlobalPosition = true
        globalPosition = if (parent is Node2D) {
            (parent as Node2D).globalPosition + position
        } else {
            position
        }
        updatingGlobalPosition = false
        // Propagar la actualización de la posición global a los hijos
        for (child in getChildren()) {
            if (child is Node2D) {
                child.updateGlobalPosition()
            }
        }
    }

    private fun updateLocalPosition() {
        updatingPosition = true
        position = if (parent is Node2D) {
            globalPosition - (parent as Node2D).globalPosition
        } else {
            globalPosition
        }
        updatingPosition = false
    }

    override fun addChild(node: Node) {
        super.addChild(node)
        if (node is Node2D) {
            node.updateGlobalPosition()
        }
    }

    override fun removeChild(node: Node) {
        super.removeChild(node)
        if (node is Node2D) {
            node.updateGlobalPosition()
        }
    }

    override fun draw(g2: Graphics2D) {
        //println("Drawing Node2D: $name at position $globalPosition")
        for (node in getChildren()) {
            if (node is IDrawable) {
                node.draw(g2)
            }
        }
    }
}
