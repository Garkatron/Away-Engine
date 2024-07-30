package deus.away.engine.core.systems.saving
import deus.away.engine.core.systems.node.Node
import deus.away.engine.core.systems.scene.Scene
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class SaveManager {
    fun saveAnnotatedValues(instance: Any): Map<String, Any?> {
        val savedValues = mutableMapOf<String, Pair<String, Any?>>()
        val kClass = instance::class

        kClass.memberProperties.forEach { property ->
            val annotation = property.findAnnotation<Keep>()
            if (annotation != null) {
                property.isAccessible = true
                val value = (property as? kotlin.reflect.KProperty1<Any, Any?>)?.get(instance)
                if (value != null) {
                    val valueType = value.javaClass.simpleName ?: "null"
                    savedValues[annotation.name] = Pair(valueType, value)
                }
            }

        }
        return savedValues

    }

        fun printNode(node: Node, prefix: String = "") {
            val GREEN = "\u001B[32m"
            val CYAN = "\u001B[36m"
            val YELLOW = "\u001B[33m"
            val RESET = "\u001B[0m"

            println("${prefix}${CYAN}${node.name}${RESET}")

            val nodeValues = saveAnnotatedValues(node)
            nodeValues.forEach { (key, value) ->
                println("${prefix}  ${YELLOW}$key:${RESET} $value")
            }

            if (node.getChildren().isNotEmpty()) {
                node.getChildren().forEach { child ->
                    printNode(child, "${prefix}---> ")
                }
            }
        }

        fun printScene(scene: Scene) {

            val sceneValues = saveAnnotatedValues(scene)
            sceneValues.forEach { (key, value) ->
                println("$key: $value")
            }

            scene.nodes.forEach { node ->
                printNode(node)
            }
        }

    fun saveNode(node: Node): String {
        val sb = StringBuilder()

        val parentName = node.parent?.let { "[${it::class.simpleName}]" } ?: ""
        val nodeName = "[${node::class.simpleName}]"
        sb.append("$parentName$nodeName\n")

        val nodeValues = saveAnnotatedValues(node)

        // Calcula el ancho máximo para las claves y los tipos
        val maxKeyLength = nodeValues.keys.maxOfOrNull { it.length+1 } ?: 0
        val maxTypeLength = nodeValues.values.maxOfOrNull { (it as Pair<String, Any>).first.length+1 } ?: 0

        nodeValues.forEach { (key, pair) ->
            val p = pair as Pair<String, Any>

            // Ajusta el espaciado entre la clave, el tipo y el valor
            val keyPart = key.padEnd(maxKeyLength)
            val typePart = p.first.padEnd(maxTypeLength)

            sb.append("$keyPart: $typePart = ${p.second}\n")
        }

        sb.append("\n")

        // Agregar los nodos hijos
        if (node.getChildren().isNotEmpty()) {
            node.getChildren().forEach { child ->
                sb.append(saveNode(child))
            }
        }

        return sb.toString()
    }



    fun saveScene(scene: Scene, prefix: String = "") {
            var data: StringBuilder = StringBuilder()

            data.append("[${scene::class.simpleName}]\n")
            val sceneValues = saveAnnotatedValues(scene)
            sceneValues.forEach { (key, pair) ->
                val p = pair as Pair<String, Any>

                data.append("$key: ${p.first} = ${p.second}\n")
            }

            scene.nodes.forEach { node ->
                data.append("\n${saveNode(node)}")
            }

            println("Data: ")
            println(data.toString())

        }


    }