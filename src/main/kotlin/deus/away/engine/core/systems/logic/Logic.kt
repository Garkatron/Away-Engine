package deus.away.engine.core.systems.logic

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.InputAdapter
import deus.away.engine.core.systems.controller.KeyboardListener
import deus.away.engine.core.systems.node.saving.SaveManager
import deus.away.engine.core.systems.node.scene.SceneManager

class Logic(
    private val keyboardListener: KeyboardListener,
    private val sceneManager: SceneManager
) {
    private val saveManager = SaveManager()
    private val spriteBatch = SpriteBatch()
    private var running: Boolean = false

    init {
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
        println("Logic initialized.")
    }

    fun create() {
        println("Logic created.")
    }

    fun render() {
        if (running) {
            val deltaTime = Gdx.graphics.deltaTime
            println("Logic render() called with deltaTime: $deltaTime")
            update(deltaTime)
            renderScene()
        } else {
            println("Logic is not running. Render skipped.")
        }
    }

    fun resize(width: Int, height: Int) {
        // Handle screen resizing if necessary
    }

    fun pause() {
        // Handle game pause state if needed
    }

    fun resume() {
        // Handle game resume state if needed
    }

    fun dispose() {
        println("Logic dispose() called")
        save()
        sceneManager.currentScene?.dispose() // Ensure currentScene is not null

        spriteBatch.dispose()
    }

    fun start() {
        if (!running) {
            running = true
            println("Logic started")
            create() // Directly call create() instead of postRunnable
        }
    }

    fun stop() {
        running = false
        dispose()
        Gdx.app.exit() // Graceful shutdown of the LibGDX application
    }

    fun save() {
        sceneManager.currentScene?.let {
            saveManager.saveScene(it)
        }
    }

    private fun update(dt: Float) {
        println("Logic update() called with deltaTime: $dt")
        sceneManager.currentScene?.update(dt) ?: println("No current scene to update")
    }

    private fun renderScene() {
        println("Logic renderScene() called")
        sceneManager.currentScene?.let { scene ->
            spriteBatch.begin()
            scene.draw(spriteBatch)
            spriteBatch.end()
        } ?: println("No current scene to render")
    }
}
