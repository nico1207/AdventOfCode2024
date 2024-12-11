package day11

import readInput

fun main() {
    val input = readInput(11, false).split(" ").map { it.toLong() }

    var stoneCounts = input.associateWith { 1L }

    repeat(75) {
        val newCounts = mutableMapOf<Long, Long>()
        stoneCounts.forEach { (number, count) ->
            when {
                number == 0L -> newCounts.merge(1L, count, Long::plus)
                number.toString().length % 2 == 0 -> {
                    val numStr = number.toString()
                    val firstHalf = numStr.take(numStr.length / 2).toLong()
                    val secondHalf = numStr.takeLast(numStr.length / 2).toLong()

                    newCounts.merge(firstHalf, count, Long::plus)
                    newCounts.merge(secondHalf, count, Long::plus)
                }
                else -> newCounts.merge(number * 2024, count, Long::plus)
            }
        }
        stoneCounts = newCounts
    }

    println(stoneCounts.values.sum())
}