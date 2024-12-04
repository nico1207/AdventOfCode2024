package day4

import readInput

val offsets1 = listOf(
    listOf(Pair(0,0), Pair(-1,0), Pair(-2,0), Pair(-3,0)),
    listOf(Pair(0,0), Pair(1,0), Pair(2,0), Pair(3,0)),
    listOf(Pair(0,0), Pair(0,-1), Pair(0,-2), Pair(0,-3)),
    listOf(Pair(0,0), Pair(0,1), Pair(0,2), Pair(0,3)),
    listOf(Pair(0,0), Pair(-1,-1), Pair(-2,-2), Pair(-3,-3)),
    listOf(Pair(0,0), Pair(1,-1), Pair(2,-2), Pair(3,-3)),
    listOf(Pair(0,0), Pair(-1,1), Pair(-2,2), Pair(-3,3)),
    listOf(Pair(0,0), Pair(1,1), Pair(2,2), Pair(3,3))
)

val offsets2 = listOf(
    listOf(Pair(0,0), Pair(-1,-1), Pair(1,1), Pair(-1,1), Pair(1,-1)),
    listOf(Pair(0,0), Pair(1,1), Pair(-1,-1), Pair(1,-1), Pair(-1,1)),
    listOf(Pair(0,0), Pair(-1,-1), Pair(1,1), Pair(1,-1), Pair(-1,1)),
    listOf(Pair(0,0), Pair(1,1), Pair(-1,-1), Pair(-1,1), Pair(1,-1)),
)

fun  List<String>.findMatches(word: String, offsets: List<List<Pair<Int, Int>>>): Int {
    var matches = 0

    for (y in this.indices) {
        for (x in this[y].indices) {
            direction@ for (offsetList in offsets) {
                for (offsetIndex in offsetList.indices) {
                    val xOff = x + offsetList[offsetIndex].first
                    val yOff = y + offsetList[offsetIndex].second
                    if (yOff !in this.indices || xOff !in this[yOff].indices) continue@direction
                    val char = this[yOff][xOff]
                    if (char != word[offsetIndex]) continue@direction
                    if (offsetIndex == offsetList.size-1) matches++
                }
            }
        }
    }

    return matches
}

fun main() {
    val input = readInput(4, false).lines()

    println(input.findMatches("XMAS", offsets1))
    println(input.findMatches("AMSMS", offsets2))
}
