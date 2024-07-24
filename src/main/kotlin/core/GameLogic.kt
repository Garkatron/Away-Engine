package core

import components.PositionComponent
import core.controller.KeyboardListener
import core.entity.Entity
import core.maths.Vector2
import java.awt.event.KeyListener

class GameLogic (val fps: Int, keyboardListener: KeyboardListener) : Runnable {

    private var running: Boolean = false

    private val player = Entity().apply {
        componentManager.addComponent(
            PositionComponent(Vector2(0f,0f),"positionComponent")
        )
    }

    val gameObjectsList: ArrayList<GameObject> = arrayListOf(
        player
    )

    init {

        keyboardListener.keyDown.connect { keyCode ->
            println(keyCode)
        }
        keyboardListener.keyUp.connect { keyCode ->
            println(keyCode)
        }
        keyboardListener.keyPressed.connect { keyChar ->
            println(keyChar)
        }
    }

    fun start() {
        if (!running) {
            running = true
            val thread = Thread(this)
            thread.start()
        }
    }

    fun stop() {
        running = false
    }


    private fun update(dt: Float) {
        for (obj in gameObjectsList) {
            obj.update(dt)
        }
    }

    override fun run() {
        val fps = 60
        val drawInterval = 1_000_000_000L / fps
        var nextUpdateTime = System.nanoTime()
        var lastUpdateTime = nextUpdateTime
        var deltaTime: Float

        while (running) {
            try {
                val now = System.nanoTime()

                deltaTime = (now - lastUpdateTime) / 1_000_000_000.0f
                lastUpdateTime = now

                update(deltaTime)

                var remainingTime = nextUpdateTime - now

                if (remainingTime < 0) {
                    remainingTime = 0
                }

                Thread.sleep(remainingTime / 1_000_000L) // Convertir nanosegundos a milisegundos

                nextUpdateTime += drawInterval

            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
                break
            }
        }
    }

}
