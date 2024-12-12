package day10

import Vec
import for2D
import readInput

fun main() {
    val input = readInput(10, true).lines()

    val trailheads = mutableSetOf<Vec>()
    for2D(input) { (x, y) ->
        if (input[y][x] == '0') trailheads.add(Vec(x, y))
    }

    val part2 = trailheads.sumOf { th ->
        getRating(th, input)
    }

    println(part2)
}

fun getRating(pos: Vec, input: List<String>): Int {
    val currVal = input[pos.y][pos.x]
    if (currVal == '9') return 1

    return offsets.sumOf {
        val offPos = pos + it
        if (offPos liesIn input && input[offPos.y][offPos.x] == currVal + 1) {
            return@sumOf getRating(offPos, input)
        } else {
            return@sumOf 0
        }
    }
}