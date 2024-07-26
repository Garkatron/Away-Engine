package core

import core.engine.controller.KeyboardListener
import entities.PlayerEntity

class GameLogic (keyboardListener: KeyboardListener) {

    private var running: Boolean = false

    private val player = PlayerEntity(keyboardListener)

    val gameObjectsList: ArrayList<GameObject> = arrayListOf(
        player
    )


//    fun start() {
//        if (!running) {
//            running = true
//            val thread = Thread(this)
//            thread.start()
//        }
//    }

    fun stop() {
        running = false
    }


    fun update(dt: Float) {
        for (obj in gameObjectsList) {
            obj.update(dt)
        }
    }


}
