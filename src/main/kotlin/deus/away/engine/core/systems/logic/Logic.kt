package deus.away.engine.core.systems.logic

import deus.away.engine.core.systems.controller.KeyboardListener
import deus.away.engine.core.systems.signal.Signal
import deus.away.engine.core.systems.render.Render
import deus.away.engine.core.systems.render.Window
import deus.away.engine.core.systems.saving.SaveManager
import deus.away.engine.core.systems.scene.Scene
import java.awt.event.WindowEvent
import kotlin.system.exitProcess

class Logic(val keyboardListener: KeyboardListener, val window: Window, val gamePanel: Render, val currentScene: Scene) : Runnable {

    private val saveManager = SaveManager()
    private var running: Boolean = false
    private lateinit var gameThread: Thread
    val onExit = Signal<WindowEvent>()
    val closeOperation = {
        save()
        stop()
    }
    private val fps = gamePanel.fps


    init {
        gamePanel.currentScene = currentScene
    }


    fun save() {
        saveManager.printScene(currentScene)
        saveManager.saveScene(currentScene)
    }

    fun update(dt: Float) {
        currentScene.update(dt)
    }

    fun start() {
        if (!running) {
            running = true
            gameThread = Thread(this)
            gameThread.start()
            window.show()
        }
    }

    fun stop() {
        running = false
        try {
            gameThread.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        exitProcess(0)
    }


    override fun run() {
        val drawInterval = 1_000_000_000L / fps
        val updateInterval = 1_000_000_000L / 60
        var nextDrawTime = System.nanoTime()
        var nextUpdateTime = System.nanoTime()
        var lastUpdateTime = System.nanoTime()

        while (running) {
            try {
                val now = System.nanoTime()

                val deltaTime = (now - lastUpdateTime) / 1_000_000_000.0f

                if (now - nextUpdateTime >= updateInterval) {
                    update(deltaTime)
                    nextUpdateTime += updateInterval
                    lastUpdateTime = now
                }

                if (now - nextDrawTime >= drawInterval) {
                    gamePanel.repaint()
                    nextDrawTime += drawInterval
                }

                var remainingTime = nextDrawTime - System.nanoTime()
                if (remainingTime < 0) {
                    remainingTime = 0
                }
                Thread.sleep(remainingTime / 1_000_000L)

            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
                break
            }
        }
    }
}
