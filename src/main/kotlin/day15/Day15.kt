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
    val map = input.takeWhile { it.isNotEmpty() }.toMutableList()
    val moves = input.takeLastWhile { it.isNotEmpty() }.joinToString("") { it }

    val robotY = map.indexOfFirst { it.contains("@") }
    val robotX = map[robotY].indexOf("@")
    var robotPos = Vec(robotX, robotY)

    moves.forEach { move ->
        val moveVec = moveVecs[move]!!
        val futurePos = robotPos + moveVec
        when (map[futurePos.y][futurePos.x]) {
            'O' -> {
                var boxPos = futurePos
                val affectedBoxes = mutableListOf(boxPos)
                var futureBoxPos = boxPos + moveVec
                while (true) {
                    when (map[futureBoxPos.y][futureBoxPos.x]) {
                        '#' -> return@forEach
                        'O' -> {
                            affectedBoxes.add(futureBoxPos)
                            boxPos = futureBoxPos
                            futureBoxPos = boxPos + moveVec
                        }
                        '.' -> {
                            map.set(robotPos, '.')
                            val first = affectedBoxes.first()
                            map.set(first, '@')
                            map.set(futureBoxPos, 'O')
                            robotPos = first
                            break
                        }
                    }
                }

            }
            '.' -> {
                map.set(robotPos, '.')
                robotPos = futurePos
                map.set(robotPos, '@')
            }
        }
    }

    var sum = 0
    for2D(map) { (x,y) ->
        if (map[y][x] == 'O') {
            sum += y * 100 + x
        }
    }
    println(sum)
}