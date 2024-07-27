package core

import core.engine.controller.KeyboardListener
import core.engine.maths.Vector2i
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

class GameLogic (keyboardListener: KeyboardListener) {


    init {
        DebugLogic.isDebug = true
    }

    private var running: Boolean = false



    var currentScene = Scene().apply {
        addNode(
            Node2D("Node2D").apply {

                addChild(
                    AudioStreamPlayer(path = "/assets/music/Retro Music - ABMU - ChipWave 01.wav")
                )
            }
        )
    }

    fun update(dt: Float) {
        currentScene.update(dt)
    }


}
