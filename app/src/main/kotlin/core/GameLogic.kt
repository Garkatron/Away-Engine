package core

import core.engine.controller.KeyboardListener
import core.engine.media.SourceLoader
import core.maths.Vector2i
import core.nodes.Sprite
import core.nodes.TileMap
import core.systems.collision.Collision
import core.systems.node.Node2D
import core.systems.scene.Scene
import core.systems.tiled.TileAtlas
import nodes.Player
import java.awt.image.BufferedImage

class GameLogic(keyboardListener: KeyboardListener) {

    private var running: Boolean = false

    private val atlas: BufferedImage? = SourceLoader.loadImageResource("/assets/map/test.png")
    private val tileAtlas: TileAtlas? = atlas?.let {
        TileAtlas(16, 16, it).apply {
            scale = 4
            setTileAt(0, Vector2i(0, 0), Collision.Square)
        }
    }
    val currentScene: Scene = Scene().apply {
        val rootNode = Node2D("RootNode")
        tileAtlas?.let {
            val tileMap = TileMap("TileMap", it).apply {
                setCell(0,Vector2i(0,0))
                setCell(0, Vector2i(1,0))
                setCell(0, Vector2i(2,0))
                setCell(0, Vector2i(3,0))
                setCell(0, Vector2i(4,0))
                setCell(0, Vector2i(5,0))
                setCell(0, Vector2i(6,0))
                setCell(0, Vector2i(7,0))

                setCell(0,Vector2i(0,1))
                setCell(0, Vector2i(1,1))
                setCell(0, Vector2i(2,1))
                setCell(0, Vector2i(3,1))
                setCell(0, Vector2i(4,1))
                setCell(0, Vector2i(5,1))
                setCell(0, Vector2i(6,1))
                setCell(0, Vector2i(7,1))

                setCell(0,Vector2i(0,2))
                setCell(0, Vector2i(1,2))
                setCell(0, Vector2i(2,2))
                setCell(0, Vector2i(3,2))
                setCell(0, Vector2i(4,2))
                setCell(0, Vector2i(5,2))
                setCell(0, Vector2i(6,2))
                setCell(0, Vector2i(7,2))

                setCell(0,Vector2i(0,3))
                setCell(0, Vector2i(1,3))
                setCell(0, Vector2i(2,3))
                setCell(0, Vector2i(3,3))
                setCell(0, Vector2i(4,3))
                setCell(0, Vector2i(5,3))
                setCell(0, Vector2i(6,3))
                setCell(0, Vector2i(7,3))
            }
            rootNode.addChildren(
                tileMap,
                Player(keyboardListener = keyboardListener),
            )
        }
        addNode(rootNode)
    }

    fun update(dt: Float) {
        currentScene.update(dt)
    }
}
