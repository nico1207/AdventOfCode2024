package day15

import Vec
import for2D
import readInput
import set

private val moveVecs = mapOf(
    '<' to Vec(-1, 0),
    '>' to Vec(1, 0),
    '^' to Vec(0, -1),
    'v' to Vec(0, 1)
)

fun main() {
    val input = readInput(15, false).lines()
    val initialMap = input.takeWhile { it.isNotEmpty() }.toMutableList()
    val map = initialMap.map { it.map {
        when (it) {
            '#' -> "##"
            '@' -> "@."
            'O' -> "[]"
            else -> ".."
        }
    }.joinToString("") }.toMutableList()
    val moves = input.takeLastWhile { it.isNotEmpty() }.joinToString("") { it }

    val robotY = map.indexOfFirst { it.contains("@") }
    val robotX = map[robotY].indexOf("@")
    var robotPos = Vec(robotX, robotY)

    moves.forEach outer@ {  move ->
        val moveVec = moveVecs[move]!!
        val futurePos = robotPos + moveVec
        when (map[futurePos.y][futurePos.x]) {
            '[' -> {
                robotPos = moveBoxes(futurePos, moveVec, map, robotPos, Vec(1,0))
            }
            ']' -> {
                robotPos = moveBoxes(futurePos, moveVec, map, robotPos, Vec(-1, 0))
            }
            '.' -> {
                map.set(robotPos, '.')
                robotPos = futurePos
                map.set(robotPos, '@')
            }
        }
    }

    map.forEach { println(it) }

    var sum = 0
    for2D(map) { (x,y) ->
        if (map[y][x] == '[') {
            sum += y * 100 + x
        }
    }
    println(sum)

}

private fun moveBoxes(futurePos: Vec, moveVec: Vec, map: MutableList<String>, robotPos: Vec, boxWidthVec: Vec): Vec {
    var currRobotPos = robotPos
    val affectedBoxes = mutableListOf(futurePos, futurePos + boxWidthVec)
    var currentBoxes = listOf(futurePos, futurePos + boxWidthVec)

    while (true) {
        val newBoxes = mutableListOf<Vec>()
        currentBoxes.forEach { box ->
            val nextPos = box + moveVec
            if (affectedBoxes.contains(nextPos)) return@forEach
            when (map[nextPos.y][nextPos.x]) {
                '#' -> return currRobotPos
                '[' -> {
                    affectedBoxes.add(nextPos)
                    affectedBoxes.add(nextPos + Vec(1, 0))
                    newBoxes.add(nextPos)
                    newBoxes.add(nextPos + Vec(1, 0))
                }

                ']' -> {
                    affectedBoxes.add(nextPos)
                    affectedBoxes.add(nextPos + Vec(-1, 0))
                    newBoxes.add(nextPos)
                    newBoxes.add(nextPos + Vec(-1, 0))
                }
            }
        }
        if (newBoxes.isNotEmpty()) {
            currentBoxes = newBoxes
        } else {
            // No new boxes were found + there was no wall -> so move all affected boxes
            affectedBoxes.reversed().forEach { boxToMove ->
                val newPos = boxToMove + moveVec
                val char = map[boxToMove.y][boxToMove.x]
                map.set(newPos, char)
                map.set(boxToMove, '.')
            }
            map.set(currRobotPos, '.')
            currRobotPos = futurePos
            map.set(currRobotPos, '@')
            break
        }
    }
    return currRobotPos
}

