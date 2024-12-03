package day3

import readInput

fun main() {
    val input = readInput(3, false)

    val part1 = Regex("""mul\((\d{1,3}),(\d{1,3})\)""").findAll(input).sumOf {
        it.groups[1]!!.value.toInt() * it.groups[2]!!.value.toInt()
    }
    println(part1)

    var enabled = true
    val part2 = Regex("""(mul\((\d{1,3}),(\d{1,3})\))|(do\(\))|(don't\(\))""").findAll(input).sumOf {
        if (it.value == "do()") enabled = true
        if (it.value == "don't()") enabled = false
        if (it.value.contains("mul") && enabled) {
            it.groups[2]!!.value.toInt() * it.groups[3]!!.value.toInt()
        } else {
            0
        }
    }
    println(part2)
}
