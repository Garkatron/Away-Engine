package nodes
import core.engine.controller.KeyboardListener
import core.engine.maths.Vector2i
import core.engine.media.SourceLoader
import core.engine.media.SpriteSplitter
import core.engine.media.image.ImageAnimations
import core.nodes.AnimatedSprite
import core.systems.node.Node2D

class Player(keyboardListener: KeyboardListener) : Node2D() {

    private val playerImage = SourceLoader.loadImageResource("/assets/player/player.png")

    private val playerAnimations = ImageAnimations(isLoop = true, animationSpeed = 0.15f).apply {

        if (playerImage != null) {
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

    init {
        addChild(
            AnimationManager(keyboardListener = keyboardListener).apply {
                addChild(
                    AnimatedSprite(imageAnimations = playerAnimations, twidth = 16, theight = 16, defaultAnimation = "IDLE_DOWN")
                )
            }
        )
        addChild(
            StateMachine()
        )
        addChild(
            Controller(keyboardListener, stateMachine = getChildren()[1] as StateMachine)
        )

    }


}
