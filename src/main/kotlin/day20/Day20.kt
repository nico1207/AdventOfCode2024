package day20

import Vec
import day10.dirs
import find
import get
import readInput
import java.util.*

fun main() {
    val input = readInput(20, false).lines()
    val start = input.find('S')
    val end = input.find('E')

    val dijkstraMap = input.getDijkstraMap(start, end)
    val path = dijkstraMap.getPath(start, end)
    val longestPath = path.size

    val cheats = mutableSetOf<Pair<Vec, Vec>>()
    val howMany = mutableMapOf<Int, Int>()
    for ((i, step) in path.withIndex()) {
        for (dir in dirs) {
            val newPos = step + dir
            if (input[newPos] == '#') {
                val skippedPos = step + dir * 2
                if (skippedPos.x < 0 || skippedPos.y < 0 || skippedPos.x >= input[0].length || skippedPos.y >= input.size || input[skippedPos] == '#') continue
                val skippedPath = dijkstraMap.getPathLength(skippedPos, end)
                val totalLength = i + skippedPath + 2
                if (totalLength < longestPath && !cheats.contains(newPos to skippedPos)) {
                    cheats.add(newPos to skippedPos)
                    val saved = longestPath - totalLength
                    howMany[saved] = howMany.getOrDefault(saved, 0) + 1
                }
            }
        }
    }

    println(howMany.entries.filter { it.key >= 100 }.sumOf { it.value })
}

data class State(val pos: Vec, val dist: Long)