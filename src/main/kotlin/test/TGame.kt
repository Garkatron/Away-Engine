package test

import com.badlogic.gdx.Game
import deus.away.engine.core.systems.controller.KeyboardListener
import deus.away.engine.core.systems.logic.Logic
import deus.away.engine.core.systems.node.Node2D
import deus.away.engine.core.systems.node.nodes.Sprite
import deus.away.engine.core.systems.node.scene.Scene
import deus.away.engine.core.systems.node.scene.SceneManager

class TGame : Game() {
    private lateinit var logic: Logic
    private lateinit var sceneManager: SceneManager
    private lateinit var keyboardListener: KeyboardListener

    override fun create() {
        println("Creating TGame...")
        // Inicializa los recursos del juego
        initialize()

        // Configura la pantalla principal del juego
        setScreen(GameScreen(sceneManager, logic))
    }

    private fun initialize() {
        // Crear instancias de KeyboardListener, Scene y Logic
        keyboardListener = KeyboardListener()

        // Configura el SceneManager con la escena inicial
        sceneManager = SceneManager(
            Scene().apply {
                name = "Scene" // Asegúrate de que el nombre coincida con el usado en setCurrent
                addNode(
                    Node2D().apply {
                        addChildren(
                            Sprite(path = "assets/player.png", twidth = 16, theight = 16)
                        )
                    }
                )
            }
        ).apply {
            setCurrent("Scene") // Asegúrate de que el nombre sea correcto
        }

        // Configura tu lógica del juego aquí
        logic = Logic(keyboardListener, sceneManager)

        // Inicia la lógica del juego
        logic.start()
    }

    override fun dispose() {
        println("Disposing TGame...")
        // Libera todos los recursos
        logic.stop() // Asegúrate de detener la lógica del juego de manera limpia
        super.dispose()
    }
}