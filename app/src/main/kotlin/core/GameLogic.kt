package core

import core.engine.controller.KeyboardListener
import core.engine.media.SourceLoader
import core.engine.media.SpriteSplitter
import core.engine.media.image.ImageAnimations
import core.nodes.AnimatedSprite
import core.nodes.AudioStreamPlayer
import core.nodes.Sprite
import core.systems.debug.DebugLogic
import core.systems.node.Node
import core.systems.node.Node2D
import core.systems.scene.Scene
import nodes.Player

class GameLogic (keyboardListener: KeyboardListener) {

    private var running: Boolean = false


    var currentScene = Scene().apply {
        addNode(
            Node2D("Node2D").apply {
                addChildren(
                    Player(keyboardListener = keyboardListener),
                )

            }
        )
    }

    fun update(dt: Float) {
        currentScene.update(dt)
    }


}
