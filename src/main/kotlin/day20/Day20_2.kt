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

    val possibleCheatsForStep = mutableMapOf<Vec, MutableList<Vec>>()
    for (step in path) {
        for (otherStep in path) {
            if (step == otherStep) continue
            val manhattan = step.manhattan(otherStep)
            if (manhattan <= 20) {
                possibleCheatsForStep.computeIfAbsent(step) { mutableListOf() }.add(otherStep)
            }
        }
    }

    val cheats = mutableSetOf<Pair<Vec, Vec>>()
    val howMany = mutableMapOf<Int, Int>()
    for ((i, step) in path.withIndex()) {
        for (cheatPos in possibleCheatsForStep[step]!!) {
            val skippedPath = dijkstraMap.getPathLength(cheatPos, end)
            val totalLength = i + skippedPath + step.manhattan(cheatPos)
            if (totalLength < longestPath && !cheats.contains(step to cheatPos)) {
                cheats.add(step to cheatPos)
                val saved = longestPath - totalLength
                howMany[saved] = howMany.getOrDefault(saved, 0) + 1
            }
        }
    }

    println(howMany.entries.filter { it.key >= 100 }.sumOf { it.value })
}

fun List<String>.getDijkstraMap(start: Vec, end: Vec): Map<Vec, Vec> {
    val queue = PriorityQueue<State>(compareBy {it.dist})
    val distMap = mutableMapOf(
        start to 0L
    )
    val prevMap = mutableMapOf<Vec, Vec>()
    queue.offer(State(start, 0))
    while(queue.isNotEmpty()) {
        val cur = queue.poll()
        if (cur.pos == end) break
        for (dir in dirs) {
            val newPos = cur.pos + dir
            if (this[newPos] == '#') continue
            val dist = distMap[cur.pos]!! + 1L
            if (dist < (distMap[newPos] ?: Long.MAX_VALUE)) {
                distMap[newPos] = dist
                prevMap[newPos] = cur.pos
                queue.add(State(newPos, dist))
            }
        }
    }

    return prevMap
}

fun Map<Vec, Vec>.getPath(start: Vec, end: Vec): List<Vec> {
    val path = mutableListOf<Vec>()
    var cur = end
    while (cur != start) {
        path.add(cur)
        cur = this[cur]!!
    }
    path.add(start)
    return path.reversed()
}

fun Map<Vec, Vec>.getPathLength(start: Vec, end: Vec): Int {
    var pathLength = 1
    var cur = end
    while (cur != start) {
        pathLength++
        cur = this[cur]!!
    }
    return pathLength
}