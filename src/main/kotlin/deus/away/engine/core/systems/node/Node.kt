package deus.away.engine.core.systems.node
import deus.away.engine.core.interfaces.IUpdatable
import deus.away.engine.core.systems.node.saving.Keep
import deus.away.engine.core.systems.script.ScriptManager
import kotlinx.coroutines.*
import javax.script.ScriptException


open class Node(
    @Keep("name") val name: String = "Node",
) : IUpdatable {

    @Keep("script")
    var script: String = ""
    @Keep("children") private val children: MutableList<Node> = mutableListOf()

    var parent: Node? = null

    private val coroutineScope = CoroutineScope(Dispatchers.Default + Job())
    private var isFreed = false

    init {
        if (script.isNotEmpty()) {
            ScriptManager.executeScript(script)
            ScriptManager.callMethod("init")
        }

    }

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

        coroutineScope.launch {
            try {
                if (script.isNotEmpty())
                    ScriptManager.callMethod("update", dt)
            } catch (e: ScriptException) {
                println("Script execution failed: ${e.message}")
                e.printStackTrace()
            }
        }

        for (child in children) {
            (child as? IUpdatable)?.update(dt)
        }
    }

    fun free() {
        if (isFreed) return

        coroutineScope.launch {
            try {
                ScriptManager.callMethod("cleanup")
            } catch (e: ScriptException) {
                println("Script cleanup failed: ${e.message}")
                e.printStackTrace()
            }
        }

        for (child in children) {
            child.free()
        }

        children.clear()

        parent?.removeChild(this)

        coroutineScope.cancel()

        isFreed = true

        println("Freed node: $name")
    }
}
