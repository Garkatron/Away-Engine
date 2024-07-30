package deus.away.engine.core.maths

data class Vector2i(var x: Int, var y: Int) {

    // Adds two vectors
    operator fun plus(other: Vector2): Vector2 {
        return Vector2(this.x + other.x, this.y + other.y)
    }

    // Subtracts two vectors
    operator fun minus(other: Vector2): Vector2 {
        return Vector2(this.x - other.x, this.y - other.y)
    }

    // Multiplies vector by a scalar
    operator fun times(scalar: Float): Vector2 {
        return Vector2(this.x * scalar, this.y * scalar)
    }

    // Divides vector by a scalar
    operator fun div(scalar: Float): Vector2 {
        if (scalar == 0f) throw ArithmeticException("Division by zero")
        return Vector2(this.x / scalar, this.y / scalar)
    }

    // Calculates the dot product of two vectors
    fun dot(other: Vector2): Float {
        return this.x * other.x + this.y * other.y
    }

    // Calculates the magnitude of the vector
    fun magnitude(): Float {
        return Math.sqrt((x * x + y * y).toDouble()).toFloat()
    }

    // Normalizes the vector (makes it unit length)
    fun normalize(): Any {
        val mag = magnitude()
        return if (mag != 0f) this / mag else this
    }

    // Linearly interpolates between this vector and another vector by a factor t
    fun lerp(other: Vector2, t: Float): Vector2 {
        return Vector2(
            this.x + (other.x - this.x) * t,
            this.y + (other.y - this.y) * t
        )
    }

    // Provides a string representation of the vector
    override fun toString(): String {
        return "Vector2(x=$x, y=$y)"
    }

    // Returns the length of the vector (same as magnitude)
    fun length(): Float {
        return magnitude()
    }
}
