package core.systems.tiled

import core.maths.Vector2i
import core.systems.collision.Collision
import java.awt.image.BufferedImage

data class TileData(val id: Int,val position: Vector2i, val image: BufferedImage, val collision: Collision?)