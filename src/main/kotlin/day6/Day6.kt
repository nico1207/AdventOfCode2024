package day6

import readInput

fun List<String>.walkPath(startPos: Vec): Set<Vec> {
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
            return setOf()
        }
        pastStates += pos to dir
    }

    return visited
}

fun main() {
    val input = readInput(6, false).lines()
    var guardPositionY = input.indexOfFirst { it.contains("^") }
    var guardPositionX = input[guardPositionY].indexOf("^")
    val guardPos = Vec(guardPositionX, guardPositionY)

    val pathPositions = input.walkPath(guardPos)
    println(pathPositions.size)

    // Try all positions along path, if they cause a loop
    var obstructions = 0
    for (pos in pathPositions) {
        val char = input[pos.y][pos.x]
        if (char != '.') continue
        val inputCopy = input.toMutableList()
        inputCopy[pos.y] = StringBuilder(inputCopy[pos.y]).also { it.setCharAt(pos.x, '#') }.toString()
        if (inputCopy.walkPath(guardPos).isEmpty()) obstructions++
    }

    println(obstructions)
}

data class Vec(val x: Int, val y: Int)