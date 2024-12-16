package day16

import Vec
import day10.dirs
import find
import get
import readInput
import java.util.*

fun main() {
    val input = readInput(16, false).lines()
    val start = input.find('S')
    val end = input.find('E')

    val paths = input.findCheapestPath(start, end)
    println(paths.first().cost)
    println(paths.flatMap { it.path }.distinct().size)
}

fun List<String>.findCheapestPath(start: Vec, end: Vec): Set<State> {
    val queue = PriorityQueue<State>()
    val visited = mutableMapOf<Pair<Vec, Vec>, Long>()
    dirs.forEach { dir ->
        queue.offer(State(start, dir, if (dir == Vec(1,0)) 0L else 1000L, listOf(start)))
    }
    var cheapest = 0L
    val cheapestPaths = mutableSetOf<State>()
    while (queue.isNotEmpty()) {
        val current = queue.poll()
        val stateKey = current.pos to current.dir
        if (visited.containsKey(stateKey) && visited[stateKey]!! < current.cost) {
            continue
        }
        visited[stateKey] = current.cost
        if (current.pos == end) {
            if (cheapest == 0L) {
                cheapest = current.cost
            }
            if (current.cost == cheapest) {
                cheapestPaths.add(current)
            }
            continue
        }
        if (this[current.pos] == '#') {
            continue
        }
        dirs.forEach { newDir ->
            val newPos = current.pos + newDir
            val newCost = current.cost + if (newDir == current.dir) 1L else 1001L
            if (cheapest != 0L && newCost > cheapest) return@forEach
            queue.offer(State(newPos, newDir, newCost, current.path + newPos))
        }
    }

    return cheapestPaths
}


data class State(
    val pos: Vec,
    val dir: Vec,
    val cost: Long,
    val path: List<Vec>
) : Comparable<State> {
    override fun compareTo(other: State) = cost.compareTo(other.cost)
}