package components

import GamePanel.Companion.RENDER_TILE_SIZE
import core.engine.`object`.GameObject
import core.engine.interfaces.IDrawable
import core.engine.component.Component
import core.engine.media.image.Sprite
import java.awt.Graphics2D

open class SpriteComponent(name: String, path: String) : Component<GameObject>(name), IDrawable {

    val sprite: Sprite = Sprite(path)

    init {

    }
    override fun update(deltaTime: Float) {

    }

    override fun draw(g2: Graphics2D) {
        val entityPosComponent = ref.componentManager.getComponentByName("positionComponent") as PositionComponent
        val pos = entityPosComponent.position
        g2.drawImage(sprite.image, pos.x.toInt(), pos.y.toInt(), RENDER_TILE_SIZE,RENDER_TILE_SIZE,null)
    }
}