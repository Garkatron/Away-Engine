package components

import GamePanel.Companion.RENDER_TILE_SIZE
import core.GameObject
import core.engine.component.Component
import core.engine.image.AnimatedSprite
import core.engine.image.ImageAnimations
import core.engine.interfaces.IDrawable
import java.awt.Graphics2D

class AnimatedSpriteComponent(name: String, imageAnimations: ImageAnimations, defaultAnimation: String) : Component<GameObject>(name),
    IDrawable {

    private var animatedSprite: AnimatedSprite = AnimatedSprite(imageAnimations, defaultAnimation)

    fun animate(key: String) {
        animatedSprite.setAnimation(key)
        animatedSprite.animate()
    }

    override fun update(deltaTime: Float) {
        animatedSprite.update()
    }

    override fun draw(g2: Graphics2D) {
        val entityPosComponent = ref.componentManager.getComponentByName("positionComponent") as PositionComponent
        val pos = entityPosComponent.position
        g2.drawImage(animatedSprite.currentImage, pos.x.toInt(), pos.y.toInt(), RENDER_TILE_SIZE,RENDER_TILE_SIZE,null)
    }
}