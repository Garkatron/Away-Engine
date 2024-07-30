package core.systems.collision

import deus.away.engine.core.maths.Vector2

sealed class Collision(val offset: Vector2, val size: Vector2) {
    object Square : Collision(Vector2(0f, 0f), Vector2(1f, 1f))
    object Rectangle : Collision(Vector2(0f, 0f), Vector2(2f, 1f))

    class Custom(offset: Vector2, size: Vector2) : Collision(offset, size)
}
