package deus.away.engine.core.systems.logic

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.InputAdapter
import deus.away.engine.core.interfaces.IDrawable
import deus.away.engine.core.interfaces.IDrawableNode
import deus.away.engine.core.systems.controller.KeyboardListener
import deus.away.engine.core.systems.saving.SaveManager
import deus.away.engine.core.systems.scene.Scene

class Logic(
    private val keyboardListener: KeyboardListener,
    private val currentScene: Scene
) : ApplicationListener {

    private val saveManager = SaveManager()
    private val spriteBatch = SpriteBatch()
    private var running: Boolean = false

    init {
        // Setup your scene or other initial configurations here if needed
    }

    override fun create() {
        Gdx.input.inputProcessor = object : InputAdapter() {
            override fun keyDown(keycode: Int): Boolean {
                keyboardListener.keyDown.emit(keycode)
                return true
            }

            override fun keyUp(keycode: Int): Boolean {
                keyboardListener.keyUp.emit(keycode)
                return true
            }
        }
    }

    override fun render() {
        if (running) {
            val deltaTime = Gdx.graphics.deltaTime
            update(deltaTime)
            currentScene.draw(spriteBatch)
        }
    }

    override fun resize(width: Int, height: Int) {
        // Handle screen resizing if necessary
    }

    override fun pause() {
        // Handle game pause state if needed
    }

    override fun resume() {
        // Handle game resume state if needed
    }

    override fun dispose() {
        save()
        spriteBatch.dispose()
        currentScene.dispose()
    }

    fun start() {
        if (!running) {
            running = true
            Gdx.app.postRunnable { create() }
        }
    }

    fun stop() {
        running = false
        dispose()
        Gdx.app.exit() // Graceful shutdown of the LibGDX application
    }

    fun save() {
        saveManager.printScene(currentScene)
        saveManager.saveScene(currentScene)
    }

    private fun update(dt: Float) {
        currentScene.update(dt)
    }
}
