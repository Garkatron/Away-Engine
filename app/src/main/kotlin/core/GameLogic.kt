package core

import core.engine.controller.KeyboardListener
import core.engine.media.SourceLoader
import core.engine.`object`.GameObject
import core.media.audio.WAVPlayer
import entities.PlayerEntity
import java.nio.file.Paths
import javax.naming.ldap.SortKey

class GameLogic (keyboardListener: KeyboardListener) {

    private var running: Boolean = false

    private val player = PlayerEntity(keyboardListener)

    val gameObjectsList: ArrayList<GameObject> = arrayListOf(
        player
    )


    fun update(dt: Float) {
        for (obj in gameObjectsList) {
            obj.update(dt)
        }
    }


}
