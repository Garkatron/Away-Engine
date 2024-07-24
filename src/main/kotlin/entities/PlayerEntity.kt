package entities

import components.ControllerComponent
import components.PositionComponent
import core.controller.KeyboardListener
import core.entity.Entity
import core.maths.Vector2

class PlayerEntity (keyboardListener: KeyboardListener) : Entity() {

    private val positionComponent = PositionComponent(Vector2(0f,0f),"positionComponent")
    private val controllerComponent = ControllerComponent(keyboardListener, positionComponent, "controllerComponent")

    init {
        componentManager.addComponent(
            positionComponent
        )
        componentManager.addComponent(
            controllerComponent
        )

    }
}