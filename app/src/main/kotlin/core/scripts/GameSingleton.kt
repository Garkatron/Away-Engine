package core.scripts

import core.engine.signal.Signal

object GameSingleton {

    val onLose = Signal<Pair<String,Int>>()

    // Game properties
    var score: Int = 0

    fun lose(message: String) {
        onLose.emit(Pair(message, score))
    }

}