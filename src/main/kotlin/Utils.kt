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

    infix fun liesIn(grid: List<String>): Boolean {
        return y in grid.indices && x in grid[y].indices
    }
}

fun for2D(grid: List<String>, func: (Int, Int) -> Unit) {
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            func(x, y)
        }
    }
}
