package day7

import readInput

fun main() {
    val input = readInput(7, false).lines().map { it.split(":").let { it[0].toLong() to it[1].split(" ").filter { it.isNotEmpty() }.map { it.toLong() } } }

    val part1 = input.filter { (result, nums) -> canBeSolved(result, nums, listOf(Long::plus, Long::times)) }.sumOf { (result, _) -> result }
    println(part1)

    val part2 = input.filter { (result, nums) -> canBeSolved(result, nums, listOf(Long::plus, Long::times, ::concat)) }.sumOf { (result, _) -> result }
    println(part2)
}

fun canBeSolved(result: Long, nums: List<Long>, possibleOperators: List<Op>): Boolean {
    for (operators in operatorCombinations(nums.size - 1, possibleOperators)) {
        var acc = nums[0]
        for (i in operators.indices) {
            val operator = operators[i]
            val operand = nums[i + 1]
            acc = operator(acc, operand)
        }
        if (acc == result) return true
    }
    return false
}

fun operatorCombinations(length: Int, possibleOperators: List<Op>): OperatorList {
    if (length == 1) return possibleOperators.map { listOf(it) }

    val newOps = mutableListOf<List<Op>>()

    for (l in operatorCombinations(length - 1, possibleOperators)) {
        for (op in possibleOperators) {
            newOps += l + op
        }
    }

    return newOps
}

fun concat(a: Long, b: Long): Long {
    return (a.toString() + b.toString()).toLong()
}

typealias Op = (Long, Long) -> Long
typealias OperatorList = List<List<Op>>