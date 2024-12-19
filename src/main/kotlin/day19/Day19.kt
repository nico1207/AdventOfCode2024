package day19

import readInput

fun main() {
    val input = readInput(19, false).lines()
    val patterns = input[0].split(", ")
    val designs = input.drop(2)

    println(designs.count { it.patternCombinations(patterns) > 0 })
    println(designs.sumOf { it.patternCombinations(patterns) })
}

val combinations = mutableMapOf<String, Long>()

private fun String.patternCombinations(patterns: List<String>): Long {
    if (this in combinations) return combinations[this]!!
    if (this.isEmpty()) return 1
    val possiblePatterns = patterns.filter { this.startsWith(it) }
    val sum = possiblePatterns.sumOf { this.drop(it.length).patternCombinations(patterns) }
    combinations[this] = sum
    return sum
}