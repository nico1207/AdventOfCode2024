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
}