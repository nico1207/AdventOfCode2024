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
}

fun for2D(grid: List<String>, func: (Vec) -> Unit) {
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            func(Vec(x,y))
        }
    }
}
