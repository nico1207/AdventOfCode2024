package day13

import Vec
import readInput

fun main() {
    val input = readInput(13, false).lines().windowed(3, 4).map { Machine(
        a = it[0].split(",").let { Vec(it[0].substringAfter("+").toInt(), it[1].substringAfter("+").toInt()) },
        b = it[1].split(",").let { Vec(it[0].substringAfter("+").toInt(), it[1].substringAfter("+").toInt()) },
        prize = it[2].split(",").let { Vec(it[0].substringAfter("=").toInt(), it[1].substringAfter("=").toInt()) },
    ) }

    val tokens = input.sumOf {
        val a = it.a.x.toDouble()
        val b = it.b.x.toDouble()
        val c = it.prize.x.toDouble() + 10000000000000
        val d = it.a.y.toDouble()
        val e = it.b.y.toDouble()
        val f = it.prize.y.toDouble() + 10000000000000
        val x = (b*f-e*c)/(b*d-e*a)
        val y = (c-a*x)/b

        if (x % 1 <= 0.01 && y % 1 <= 0.01) {
            return@sumOf x.toLong() * 3 + y.toLong() * 1
        }

        return@sumOf 0L
    }

    println(tokens)
}

private data class Machine(val a: Vec, val b: Vec, val prize: Vec)