package day14

import Vec
import readInput

fun main() {
    val input = readInput(14, false).lines().map { it.split(" ").let { Robot(it[0].substringAfter("=").split(",").let { Vec(it[0].toInt(), it[1].toInt()) }, it[1].substringAfter("=").split(",").let { Vec(it[0].toInt(), it[1].toInt()) }) } }
    val size = Vec(input.maxOf { it.pos.x } + 1, input.maxOf { it.pos.y } + 1)

    val quadrant1 = mutableListOf<Robot>()
    val quadrant2 = mutableListOf<Robot>()
    val quadrant3 = mutableListOf<Robot>()
    val quadrant4 = mutableListOf<Robot>()
    input.forEach {
        it.pos = (it.pos + it.vel * 100) % size
        if (it.pos.x < size.x / 2 && it.pos.y < size.y / 2) quadrant1.add(it)
        if (it.pos.x > size.x / 2 && it.pos.y < size.y / 2) quadrant2.add(it)
        if (it.pos.x < size.x / 2 && it.pos.y > size.y / 2) quadrant3.add(it)
        if (it.pos.x > size.x / 2 && it.pos.y > size.y / 2) quadrant4.add(it)
    }

    println(quadrant1.size * quadrant2.size * quadrant3.size * quadrant4.size)
}

internal data class Robot(var pos: Vec, var vel: Vec)