package core

import components.PositionComponent
import core.controller.KeyboardListener
import core.entity.Entity
import core.maths.Vector2
import entities.PlayerEntity
import java.awt.event.KeyListener

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
