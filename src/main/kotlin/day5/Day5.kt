package day5

import readInput

fun main() {
    val input = readInput(5, false).lines()
    val rules = input.takeWhile { it.isNotEmpty() }.map { it.split("|").map { it.toInt() } }.map { it[0] to it[1] }
    val updates = input.takeLastWhile { it.isNotEmpty() }.map { it.split(",").map { it.toInt() } }

    val correctlyOrdered = updates.filter { update ->
        rules.all { (a, b) ->
            val aIndex = update.indexOf(a)
            val bIndex = update.indexOf(b)
            if (aIndex == -1 || bIndex == -1) {
                true
            } else {
                update.indexOf(a) < update.indexOf(b)
            }
        }
    }

    println(correctlyOrdered.sumOf { it[it.size/2] })

    val incorrectlyOrdered = updates - correctlyOrdered.toSet()

    val sorted = incorrectlyOrdered.map { update ->
        update.sortedWith { a, b ->
            val rule = rules.find { (ruleA, ruleB) -> (ruleA == a && ruleB == b) || (ruleA == b && ruleB == a)}
            if (rule == null) {
                0
            } else {
                if (rule.first == a) { -1 } else { 1 }
            }
        }
    }

    println(sorted.sumOf { it[it.size/2] })
}
