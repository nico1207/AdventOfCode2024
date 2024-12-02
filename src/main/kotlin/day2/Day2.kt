package day2

import readInput
import kotlin.math.abs

fun main() {
    val input = readInput(2).lines().map { it.split(" ").map { it.toInt() } }

    val part1 = input
        .map { it.windowed(2, 1).map { (a,b) -> b-a } }
        .count { it.all { it in 1..3 } || it.all { it in -3..-1  } }
    println(part1)

    val part2 = input
        .count { report ->  (report.indices + -1)
            .map { report.filterIndexed { ix, _ -> ix != it }}
            .map { it.windowed(2, 1).map { (a,b) -> b-a } }
            .any { it.all { it in 1..3 } || it.all { it in -3..-1  } }
        }
    println(part2)
}
