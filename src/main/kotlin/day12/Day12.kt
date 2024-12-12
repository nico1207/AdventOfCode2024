package day12

import Vec
import day10.offsets
import for2D
import readInput

fun main() {
    val input = readInput(12, false).lines()

    val regions = mutableListOf<MutableSet<Vec>>()

    for2D(input) { pos ->
        val char = input[pos.y][pos.x]
        val neighbourRegions = offsets.mapNotNull {
            val offPos = pos + it
            if (!(offPos liesIn input)) return@mapNotNull null
            val neighbour = input[offPos.y][offPos.x]
            if (neighbour != char) return@mapNotNull null
            return@mapNotNull regions.firstOrNull { it.contains(offPos) }
        }.distinct()
        if (neighbourRegions.isEmpty()) {
            regions.add(mutableSetOf(pos))
        } else {
            val first = neighbourRegions.first()
            for (other in neighbourRegions.drop(1)) { // Merge touching regions
                first.addAll(other)
                regions.remove(other)
            }
            first.add(pos)
        }
    }

    val part1 = regions.sumOf { region ->
        val perimeter = region.sumOf { pos ->
            offsets.filter {
                val offPos = pos + it
                !region.contains(offPos)
            }.size
        }
        region.size * perimeter
    }

    println(part1)

    val part2 = regions.sumOf { region ->
        val sides = mutableMapOf<Vec, MutableList<MutableSet<Vec>>>()
        region.forEach { pos ->
            // I don't even know what this code does anymore, at some point I started changing random operations until the result was correct
            offsets.forEach {
                val offPos = pos + it
                if (!region.contains(offPos)) {
                    val sideVec = Vec(offPos.x * it.x, offPos.y * it.y)
                    val side = sides.getOrPut(sideVec) { mutableListOf() }
                    side.firstOrNull { offsets.any { off -> it.contains(offPos + off) } }?.add(offPos) ?: side.add(mutableSetOf(offPos))
                }
            }
        }
        region.size * sides.values.sumOf { it.size }
    }

    println(part2)
}