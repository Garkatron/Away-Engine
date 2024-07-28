package nodes

import core.engine.controller.KeyboardListener
import core.engine.maths.Vector2
import core.engine.media.SourceLoader
import core.engine.media.SpriteSplitter
import core.engine.media.image.ImageAnimations
import core.maths.Vector2i
import core.nodes.AnimatedSprite
import core.systems.node.Node2D
import core.systems.debug.DebugLogic

class Player(name: String = "Player", keyboardListener: KeyboardListener) : Node2D(name) {

    private val playerImage = SourceLoader.loadImageResource("/assets/player/player.png")

    private val playerAnimations = ImageAnimations(isLoop = true, animationSpeed = 0.15f).apply {

        if (playerImage != null) {
            fun addDirectionAnimations(anim: String, startY: Int, columns: Int) {
                val directions = listOf(
                    "DOWN", "DOWN_LEFT", "LEFT", "UP_LEFT", "UP", "UP_RIGHT", "RIGHT", "DOWN_RIGHT"
                )
                directions.forEachIndexed { index, name ->
                    val animationName = "$anim$name"
                    val vectorList = List(columns) { col -> Vector2i(col, startY + index) }
                    addAnimationFromCanvas(
                        animationName,
                        SpriteSplitter.extractSpecificTilesFromFile(
                            playerImage, vectorList, 16, 16, 4, startY + index
                        ).toMutableList()
                    )
                }
            }

            // Add WALK animations
            addDirectionAnimations("WALK_", 0, 4)
            // Add IDLE animations
            addDirectionAnimations("IDLE_", 0, 1)
        } else {
            DebugLogic.debugPrintln("Player image is null.")
        }
    }

    init {
        DebugLogic.debugPrintln("Initializing Player.")
        val animatedSprite = AnimatedSprite(imageAnimations = playerAnimations, twidth = 64, theight = 64, defaultAnimation = "IDLE_DOWN")
        animatedSprite.position = Vector2(0f, 0f) // Inicializa la posición del sprite
        addChild(animatedSprite)
        DebugLogic.debugPrintln("Added AnimatedSprite child to Player.")

        val stateMachine = StateMachine()
        stateMachine.addStates(
            "IDLE_UP",
            "IDLE_UP_LEFT",
            "IDLE_UP_RIGHT",
            "IDLE_RIGHT",
            "IDLE_LEFT",
            "IDLE_DOWN_LEFT",
            "IDLE_DOWN_RIGHT",

            "WALK_UP",
            "WALK_DOWN",
            "WALK_UP_LEFT",
            "WALK_LEFT",
            "WALK_RIGHT",
            "WALK_UP_RIGHT",
            "WALK_DOWN_LEFT",
            "WALK_DOWN_RIGHT",

        )
        addChild(stateMachine)
        DebugLogic.debugPrintln("Added StateMachine child to Player.")

        stateMachine.onStateChange.connect { anim ->
            DebugLogic.debugPrintln("State changed to: $anim")
            (getChildren().find { it is AnimatedSprite } as AnimatedSprite).setAnimation(anim)
        }

        val controller = Controller(keyboardListener, stateMachine = stateMachine)
        addChild(controller)
        DebugLogic.debugPrintln("Added Controller child to Player.")
    }

}
