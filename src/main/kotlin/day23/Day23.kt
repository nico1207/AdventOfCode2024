package day23

import readInput

fun main() {
    val input = readInput(23, false).lines().map { it.split("-").let { (a, b) -> a to b } }
    val connectionsA = input.groupBy { it.first }.mapValues { it.value.unzip().second }
    val connectionsB = input.groupBy { it.second }.mapValues { it.value.unzip().first }
    val connections = connectionsA.mapValues { it.value + (connectionsB[it.key] ?: emptyList()) }.mapValues { it.value.toSet() }
    val loops = connections.keys.flatMap { getLoops(setOf(setOf(it)), connections) }.toSet()
}

var maxSize = 0
var memo = mutableMapOf<Set<String>, Set<Set<String>>>()

fun getLoops(cur: Set<Set<String>>, connections: Map<String, Set<String>>): Set<Set<String>> {
    return cur.flatMap { l ->
        if (memo.containsKey(l)) return@flatMap memo[l]!!
        if (l.size > maxSize) {
            maxSize = l.size
            println(l.sorted().joinToString(","))
        }
        //if (l.size >= 3) return@flatMap listOf(l)
        val conns = l.map { connections[it]!! }.reduce(Set<String>::intersect)
        val ret = mutableSetOf<Set<String>>()
        if (conns.isEmpty()) return@flatMap setOf(l)
        for (conn in conns) {
            if (l.contains(conn)) {
                ret.add(l)
            } else {
                ret.addAll(getLoops(setOf(l + conn), connections))
            }
        }
        memo[l] = ret
        ret
    }.toSet()
}