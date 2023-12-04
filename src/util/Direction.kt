@file:Suppress("unused")

package util

sealed interface Direction2 {
    val dx: Int
    val dy: Int
}

operator fun Point.plus(direction: Direction2) = Point(x + direction.dx, y + direction.dy)
operator fun Point.minus(direction: Direction2) = Point(x - direction.dx, y - direction.dy)

operator fun Direction2.times(n: Int) = Point(dx * n, dy * n)

fun Point.pathSequence(d: Direction2) = sequence {
    var p = this@pathSequence
    while (true) {
        p += d
        yield(p)
    }
}

enum class Direction2NonDiagonal(override val dx: Int, override val dy: Int) : Direction2 {
    Right(1, 0),
    Up(0, -1),
    Left(-1, 0),
    Down(0, 1),
    ;
    fun opposite() = when (this) {
        Right -> Left
        Up -> Down
        Left -> Right
        Down -> Up
    }
    fun rotatedLeft() = when (this) {
        Right -> Up
        Up -> Left
        Left -> Down
        Down -> Right
    }
    fun rotatedRight() = when (this) {
        Right -> Down
        Up -> Right
        Left -> Up
        Down -> Left
    }
}

enum class Direction2IncludingDiagonal(override val dx: Int, override val dy: Int) : Direction2 {
    Right(1, 0),
    UpRight(1, -1),
    Up(0, -1),
    UpLeft(-1, -1),
    Left(-1, 0),
    DownLeft(-1, 1),
    Down(0, 1),
    DownRight(1, 1),
}
