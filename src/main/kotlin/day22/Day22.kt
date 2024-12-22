package day22

import readInput

fun main() {
    val input = readInput(22, false).lines().map { it.toLong() }

    val secretNumbers = input.map {
        (1..2000).runningFold(it) { acc, _ -> nextNumber(acc) }
    }

    // Part 1
    println(secretNumbers.sumOf { it.last() })

    // Part 2
    val digits = secretNumbers.map { it.map { it % 10 } }
    val diffs = digits.map { it.withIndex().windowed(2, 1).map { (a, b) -> b.index to b.value-a.value } }
    val quads = diffs.mapIndexed { i, it -> it.windowed(4, 1).asReversed().associate { (a, b, c, d) -> Quad(a.second, b.second, c.second, d.second) to digits[i][d.first] } }
    val allQuads = quads.flatMap { it.keys }.toSet()
    val highestValue = allQuads.maxByOrNull { quad -> quads.sumOf { it[quad] ?: 0 } }
    println(highestValue)
    println(quads.sumOf { it[highestValue] ?: 0 })
}

fun nextNumber(num: Long): Long {
    val a = num.mix(num * 64L).prune()
    val b = a.mix(a / 32L).prune()
    val c = b.mix(b * 2048).prune()
    return c
}

fun Long.mix(other: Long): Long {
    return this.xor(other)
}

fun Long.prune(): Long {
    return this % 16777216L
}

data class Quad(val a: Long, val b: Long, val c: Long, val d: Long)