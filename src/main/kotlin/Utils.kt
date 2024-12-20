import kotlin.math.abs

fun readInput(day: Int, test: Boolean = false): String = object {}.javaClass.getResource("/Day$day/input${if (test) "_test" else ""}.txt")?.readText()!!

data class Vec(val x: Int, val y: Int) {
    operator fun plus(other: Vec): Vec {
        return Vec(x + other.x, y + other.y)
    }

    operator fun minus(other: Vec): Vec {
        return Vec(x - other.x, y - other.y)
    }

    operator fun times(num: Int): Vec {
        return Vec(x * num, y * num)
    }

    operator fun rem(other: Vec): Vec {
        return Vec(Math.floorMod(x, other.x), Math.floorMod(y, other.y))
    }

    infix fun liesIn(grid: List<String>): Boolean {
        return y in grid.indices && x in grid[y].indices
    }

    fun manhattan(other: Vec): Int {
        return abs(x - other.x) + abs(y - other.y)
    }
}

fun for2D(grid: List<String>, func: (Vec) -> Unit) {
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            func(Vec(x,y))
        }
    }
}

fun MutableList<String>.set(pos: Vec, char: Char) {
    this[pos.y] = StringBuilder(this[pos.y]).also { it.setCharAt(pos.x, char) }.toString()
}

operator fun List<String>.get(pos: Vec): Char {
    return this[pos.y][pos.x]
}

fun List<String>.find(char: Char): Vec {
    val y = this.indexOfFirst { it.contains(char) }
    val x = this[y].indexOf(char)
    return Vec(x, y)
}