package test

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import deus.away.engine.core.nodes.Sprite
import deus.away.engine.core.systems.logic.Logic
import deus.away.engine.core.systems.controller.KeyboardListener
import deus.away.engine.core.systems.node.Node2D
import deus.away.engine.core.systems.scene.Scene

class TGame : Game() {

    private lateinit var logic: Logic
    private lateinit var scene: Scene
    private lateinit var keyboardListener: KeyboardListener

    override fun create() {
        // Inicializa los recursos del juego
        initialize()

        // Configura la pantalla principal del juego
        setScreen(GameScreen(scene))
    }

    private fun initialize() {
        // Crear instancias de KeyboardListener, Scene y Logic
        keyboardListener = KeyboardListener() // Asegúrate de implementar la lógica del teclado

        scene = Scene().apply {
            addNode(
                Node2D().apply {
                    addChildren(
                        Sprite(path = "assets/player.png", twidth = 16, theight = 16)
                    )
                }
            )
        }

        // Configura tu escena aquí
        logic = Logic(keyboardListener, scene)

        // Inicia la lógica del juego
        logic.start()
    }

    override fun dispose() {
        // Libera todos los recursos
        logic.stop() // Asegúrate de detener la lógica del juego de manera limpia
        super.dispose()
    }
}

class GameScreen(private val scene: Scene) : ScreenAdapter() {
    private lateinit var spriteBatch: SpriteBatch

    override fun show() {
        spriteBatch = SpriteBatch()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        spriteBatch.begin()
        scene.draw(spriteBatch) // Renderiza la escena usando SpriteBatch
        spriteBatch.end()
    }

    override fun resize(width: Int, height: Int) {
        // Maneja el cambio de tamaño de la ventana si es necesario
    }

    override fun hide() {
        spriteBatch.dispose()
    }
}
