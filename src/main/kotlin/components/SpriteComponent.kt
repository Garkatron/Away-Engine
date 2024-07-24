package components

import GamePanel.Companion.RENDER_TILE_SIZE
import core.GameObject
import core.interfaces.IDrawable
import core.component.Component
import core.image.Sprite
import java.awt.Color
import java.awt.Graphics2D

class SpriteComponent(name: String, path: String) : Component<GameObject>(name), IDrawable {

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