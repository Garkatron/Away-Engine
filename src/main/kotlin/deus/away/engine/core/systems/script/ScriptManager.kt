package deus.away.engine.core.systems.script

import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException
import javax.script.Invocable

object ScriptManager {
    val engine: ScriptEngine? = ScriptEngineManager().getEngineByExtension("kts").also {
        if (it == null) {
            //println("Kotlin script engine could not be initialized.")
        } else {
            //println("Kotlin script engine initialized successfully.")
        }
    }

    fun executeScript(script: String) {
        try {
            //println("Executing script: $script")
            engine?.eval(script) ?: println("Script engine is not initialized.")
        } catch (e: ScriptException) {
            println("Script execution failed: ${e.message}")
            e.printStackTrace()
        }
    }

    fun putVariable(name: String, value: Any) {
        //println("Putting variable: $name with value: $value")
        engine?.put(name, value) ?: println("Script engine is not initialized.")
    }

    fun callMethod(methodName: String, vararg args: Any?) {
        try {
            if (engine is Invocable) {
                val invocable = engine as Invocable
                //println("Calling method: $methodName with arguments: ${args.joinToString()}")
                val result = invocable.invokeFunction(methodName, *args)
                println("Method result: $result")
            } else {
                println("Script engine is not invocable.")
            }
        } catch (e: Exception) {
            println("Method call failed: ${e.message}")
            e.printStackTrace()
        }
    }
}
