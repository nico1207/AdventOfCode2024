package day21

import Vec
import readInput

val coordsA = mapOf(
    '7' to Vec(0, 0),
    '8' to Vec(1, 0),
    '9' to Vec(2, 0),
    '4' to Vec(0, 1),
    '5' to Vec(1, 1),
    '6' to Vec(2, 1),
    '1' to Vec(0, 2),
    '2' to Vec(1, 2),
    '3' to Vec(2, 2),
    '0' to Vec(1, 3),
    'A' to Vec(2, 3),
)

val coordsB = mapOf(
    '^' to Vec(1, 0),
    'A' to Vec(2, 0),
    '<' to Vec(0, 1),
    'v' to Vec(1, 1),
    '>' to Vec(2, 1),
)

fun main() {
    val input = readInput(21, false).lines()

    println(input.sumOf { line ->
        var a = numToDir(line)
        var segMap = a.removeSuffix("A").split("A").groupingBy { it }.eachCount().mapValues { it.value.toLong() }
        println(segMap)
        repeat(25) { _ ->
            val newMap = mutableMapOf<String, Long>()
            segMap.forEach { (k, v) ->
                val p = dirToDir(k + "A")
                p.removeSuffix("A").split("A").forEach { newMap[it] = newMap.getOrDefault(it, 0L) + v }
            }
            segMap = newMap
        }
        val len = segMap.entries.sumOf { (k,v) -> (k.length+1)*v }
        val v = len * line.dropLast(1).toInt()
        println("$line: $len * ${line.dropLast(1).toInt()} = $v")
        return@sumOf v
    })
}

fun numToDir(v: String): String {
    val output = StringBuilder()

    var pos = coordsA['A']!!
    for (char in v) {
        val end = coordsA[char]!!
        val diff = end - pos
        if (diff.x < 0 && (end.x != 0 || pos.y != 3)) {
            repeat(-diff.x) { output.append('<') }
        }
        if (diff.y < 0) {
            repeat(-diff.y) { output.append('^') }
        }
        if (diff.x > 0 && (pos.x == 0 && end.y == 3)) {
            repeat(diff.x) { output.append('>') }
        }
        if (diff.x < 0 && (end.x == 0 && pos.y == 3)) {
            repeat(-diff.x) { output.append('<') }
        }
        if (diff.y > 0) {
            repeat(diff.y) { output.append('v') }
        }
        if (diff.x > 0 && (pos.x != 0 || end.y != 3)) {
            repeat(diff.x) { output.append('>') }
        }
        output.append("A")
        pos = end
    }

    return output.toString()
}

fun dirToDir(v: String): String {
    val output = StringBuilder()

    var pos = coordsB['A']!!
    for (char in v) {
        if (char == ' ') continue
        val end = coordsB[char]!!
        val diff = end - pos
        if (diff.x < 0 && (end.x != 0 || pos.y != 0)) {
            repeat(-diff.x) { output.append('<') }
        }
        if (diff.x > 0 && (pos.x == 0 && end.y == 0)) {
            repeat(diff.x) { output.append('>') }
        }
        if (diff.y > 0) {
            repeat(diff.y) { output.append('v') }
        }
        if (diff.y < 0) {
            repeat(-diff.y) { output.append('^') }
        }
        if (diff.x < 0 && (end.x == 0 && pos.y == 0)) {
            repeat(-diff.x) { output.append('<') }
        }
        if (diff.x > 0 && (pos.x != 0 || end.y != 0)) {
            repeat(diff.x) { output.append('>') }
        }
        output.append("A")
        pos = end
    }

    return output.toString()
}
