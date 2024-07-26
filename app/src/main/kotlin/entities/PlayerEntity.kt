package entities

import components.*
import core.engine.controller.KeyboardListener
import core.engine.entity.Entity
import core.engine.media.image.ImageAnimations
import core.engine.media.SourceLoader
import core.engine.media.SpriteSplitter
import core.engine.maths.Vector2
import core.engine.maths.Vector2i

class PlayerEntity(keyboardListener: KeyboardListener) : Entity() {

    private val playerImage = SourceLoader.loadImageResource("/assets/player/player.png")

    // Componentes del jugador
    private val positionComponent = PositionComponent(Vector2(0f, 0f), "positionComponent")
    private val stateMachineComponent = StateMachineComponent("stateMachineComponent",
        mutableListOf(
            "IDLE_UP",
            "IDLE_DOWN",
            "IDLE_LEFT",
            "IDLE_RIGHT",

            "IDLE_DOWN_LEFT",
            "IDLE_UP_LEFT",
            "IDLE_DOWN_RIGHT",
            "IDLE_UP_RIGHT",

            "WALK_DOWN",
            "WALK_UP",
            "WALK_RIGHT",
            "WALK_LEFT",

            "WALK_DOWN_RIGHT",
            "WALK_UP_RIGHT",
            "WALK_DOWN_LEFT",
            "WALK_UP_LEFT",
        )
    )
    private val controllerComponent = ControllerComponent(keyboardListener, positionComponent, stateMachineComponent,"controllerComponent")

    // Animaciones del jugador
    private val playerAnimations = ImageAnimations(isLoop = true, animationSpeed = 0.15f).apply {

        if (playerImage != null) {
            // Función auxiliar para añadir animaciones
            fun addDirectionAnimations(anim: String, startY: Int, columns: Int) {
                val directions = listOf(
                    "DOWN",
                    "DOWN_LEFT",
                    "LEFT",
                    "UP_LEFT",
                    "UP",
                    "UP_RIGHT",
                    "RIGHT",
                    "DOWN_RIGHT"
                )
                directions.forEachIndexed { index, name ->
                    val animationName = "$anim$name"
                    println(animationName)
                    // Crear la lista de Vector2i
                    val vectorList = List(columns) { col ->
                        Vector2i(col, startY + index)
                    }

                    addAnimationFromCanvas(
                        animationName,
                        SpriteSplitter.extractSpecificTilesFromFile(
                            playerImage,
                            vectorList,
                            16, 16, 4, startY + index
                        ).toMutableList()
                    )
                }

            }

            // Añadir animaciones de WALK
            addDirectionAnimations("WALK_", 0,4)

            // Añadir animaciones de IDLE
            addDirectionAnimations("IDLE_", 0,1)

        }
    }


    private val animatedSpriteComponent = AnimatedSpriteComponent(
        name = "animatedSpriteComponent",
        defaultAnimation = "WALK_DOWN",
        imageAnimations = playerAnimations
    )

    init {
        // Agregar componentes al administrador de componentes
        componentManager.apply {
            addComponent(animatedSpriteComponent)
            addComponent(positionComponent)
            addComponent(controllerComponent)
            addComponent(stateMachineComponent)
        }

        stateMachineComponent.onStateChange.connect {
            currentState ->
            animatedSpriteComponent.animate(currentState)
            println(currentState)
        }


    }

}
