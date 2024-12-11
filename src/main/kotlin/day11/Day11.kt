package day11

import readInput

fun main() {
    val input = readInput(11, false).split(" ").map { it.toLong() }

    var map = input.associateWith { 1L }

    for (i in 0..<75) {
        val newMap = mutableMapOf<Long, Long>()
        for ((n, v) in map) {
            val nStr = n.toString()
            if (n == 0L) {
                newMap[1] = newMap.getOrDefault(1, 0) + v
                continue
            }
            if (nStr.length % 2 == 0) {
                val a = nStr.take(nStr.length / 2).toLong()
                val b = nStr.takeLast(nStr.length / 2).toLong()
                newMap[a] = newMap.getOrDefault(a, 0L) + v
                newMap[b] = newMap.getOrDefault(b, 0L) + v
                continue
            }
            newMap[n * 2024L] = newMap.getOrDefault(n * 2024, 0) + v
        }
        map = newMap
    }

    println(map.values.sum())
}