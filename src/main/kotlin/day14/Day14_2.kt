package day14

import Vec
import readInput

fun main() {
    val input = readInput(14, false).lines().map { it.split(" ").let { Robot(it[0].substringAfter("=").split(",").let { Vec(it[0].toInt(), it[1].toInt()) }, it[1].substringAfter("=").split(",").let { Vec(it[0].toInt(), it[1].toInt()) }) } }
    val size = Vec(input.maxOf { it.pos.x } + 1, input.maxOf { it.pos.y } + 1)

    var i = 0
    while (true) {
        input.forEach {
            it.pos = (it.pos + it.vel) % size
        }
        val countPerPos = input.groupingBy { it.pos }.eachCount()
        if (countPerPos.none { it.value > 1 }) {
            println("Seconds: ${i}")
            break
        }
        i++
    }
}