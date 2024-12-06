package day6

import readInput

fun List<String>.walkPath(startPos: Vec): Int {
    var pos = startPos
    var dir = Vec(0, -1)
    val visited = mutableSetOf(pos)
    val pastStates = mutableSetOf(pos to dir)

    while (true) {
        val nextPos = Vec(pos.x + dir.x, pos.y + dir.y)
        if (nextPos.y !in this.indices || nextPos.x !in this[nextPos.y].indices) { break }
        val char = this[nextPos.y][nextPos.x]
        if (char == '#') {
            dir = Vec(-dir.y, dir.x)
        } else {
            pos = nextPos
            visited += pos
        }
        if (pastStates.contains(pos to dir)) {
            return -1
        }
        pastStates += pos to dir
    }

    return visited.size
}

fun main() {
    val input = readInput(6, false).lines()
    var guardPositionY = input.indexOfFirst { it.contains("^") }
    var guardPositionX = input[guardPositionY].indexOf("^")
    val guardPos = Vec(guardPositionX, guardPositionY)

    println(input.walkPath(guardPos))

    // Bruteforce loop obstructions
    // Could be optimized by saving the path from Part 1 and trying all positions along this path
    var obstructions = 0
    for (y in input.indices) {
        for (x in input[y].indices) {
            val char = input[y][x]
            if (char != '.') continue
            val inputCopy = input.toMutableList()
            inputCopy[y] = StringBuilder(inputCopy[y]).also { it.setCharAt(x, '#') }.toString()
            if (inputCopy.walkPath(guardPos) == -1) obstructions++
        }
    }

    println(obstructions)
}

data class Vec(val x: Int, val y: Int)