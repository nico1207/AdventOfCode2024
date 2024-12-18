package day18

import Vec
import day10.dirs
import readInput
import java.util.PriorityQueue

fun main() {
    val input = readInput(18, false).lines().map { it.split(",") }.map { (x,y) -> Vec(x.toInt(),y.toInt()) }
    val dimensions = Vec(input.maxOf { it.x }, input.maxOf { it.y })

    val path = input.take(1024).toSet().findCheapestPath(Vec(0,0), dimensions)
    println(path?.size?.minus(1))

    for (i in 1..input.size) {
        val path = input.take(i).toSet().findCheapestPath(Vec(0,0), dimensions)
        if (path == null) {
            println("No path for $i which is ${input[i-1]}")
            break
        }
    }
}

fun Set<Vec>.findCheapestPath(start: Vec, end: Vec): List<Vec>? {
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
            if (this.contains(newPos) || newPos.x < 0 || newPos.y < 0 || newPos.x > end.x || newPos.y > end.y) continue
            val dist = distMap[cur.pos]!! + 1L
            if (dist < (distMap[newPos] ?: Long.MAX_VALUE)) {
                distMap[newPos] = dist
                prevMap[newPos] = cur.pos
                queue.add(State(newPos, dist))
            }
        }
    }

    if (!prevMap.contains(end)) return null

    val pathList = mutableListOf<Vec>()
    var cur = end
    while (true) {
        pathList.add(cur)
        if (cur == start) break
        cur = prevMap[cur]!!
    }
    return pathList
}

private data class State(val pos: Vec, val dist: Long)