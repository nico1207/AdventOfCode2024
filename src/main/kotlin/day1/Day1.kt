package day1

import readInput
import kotlin.math.abs

fun main() {
    val input = readInput(1).lines().map { it.split("   ").map { it.toInt() } }
    val listA = input.map {it[0]}.sorted()
    val listB = input.map {it[1]}.sorted()

    // Part 1
    val sum = listA.zip(listB).sumOf { abs(it.first - it.second) }
    println(sum)

    // Part 2
    val sum2 = listA.sumOf { a -> listB.count { it == a } * a }
    println(sum2)
}