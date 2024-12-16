package day10

import Vec
import for2D
import readInput

val dirs = listOf(
    Vec(-1, 0), Vec(1, 0),  Vec(0, -1),  Vec(0, 1)
)

fun main() {
    val input = readInput(10, false).lines()

    val trailheads = mutableSetOf<Vec>()
    for2D(input) { (x, y) ->
        if (input[y][x] == '0') trailheads.add(Vec(x, y))
    }

    val part1 = trailheads.sumOf { th ->
        val reachablePositions = mutableMapOf('0' to mutableSetOf(th))
        for (i in '0'..'8') {
            val curr = reachablePositions[i] ?: continue
            reachablePositions[i+1] = mutableSetOf()
            for (pos in curr) {
                for (off in dirs) {
                    val offPos = pos + off
                    if (offPos liesIn input && input[offPos.y][offPos.x] == i+1) {
                        reachablePositions[i+1]!!.add(offPos)
                    }
                }
            }
        }
        reachablePositions['9']?.size ?: 0
    }

    println(part1)
}