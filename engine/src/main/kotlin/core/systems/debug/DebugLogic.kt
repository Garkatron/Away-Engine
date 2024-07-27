package core.systems.debug

object DebugLogic {

    var isDebug = false

    fun executeIfDebug(block: () -> Unit) {
        if (isDebug) {
            block()
        }
    }

    fun debugPrintln(message: String) {
        if (isDebug) {
            println(message)
        }
    }

}
