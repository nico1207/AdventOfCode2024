package day8

import Vec
import readInput

fun main() {
    val input = readInput(8, false).lines()

    val antennas = mutableMapOf<Char, List<Vec>>()
    for (y in input.indices) {
        for (x in input[y].indices) {
            val char = input[y][x]
            if (char == '.') continue
            antennas[char] = antennas.getOrDefault(char, listOf()) + Vec(x, y)
        }
    }

    val antinodes = mutableSetOf<Vec>()
    val antinodes2 = mutableSetOf<Vec>()
    for (char in antennas.keys) {
        for (a in antennas[char]!!) {
            for (b in antennas[char]!!) {
                if (a == b) continue
                val diff = b - a
                antinodes.add(b + diff)
                antinodes.add(a - diff)
                for (d in 0..999) { // incredibly lazy solution, forgive me Lord Knuth
                    antinodes2.add(b + diff * d)
                    antinodes2.add(a - diff * d)
                }
            }
        }
    }

    // Part 1
    println(antinodes.count { it liesIn input })

    // Part 2
    println(antinodes2.count { it liesIn input })
}

infix fun Vec.liesIn(grid: List<String>): Boolean {
    return y in grid.indices && x in grid[y].indices
}
