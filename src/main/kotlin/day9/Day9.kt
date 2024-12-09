package day9

import readInput

fun main() {
    val input = readInput(9, false)

    val disk = mutableListOf<Long>()
    var currFileIndex = 0L

    for ((i, n) in input.mapIndexed { i, c -> i to c.digitToInt() }) {
        if (i % 2 == 0) {
            for (j in 0..<n) { disk += currFileIndex }
            currFileIndex++
        } else {
            for (j in 0..<n) { disk += -1 }
        }
    }

    val disk1 = disk.toMutableList().apply { compact() }
    println(disk1.withIndex().filter { it.value >= 0 }.sumOf { it.index * it.value })

    val disk2 = disk.toMutableList().apply { compact2() }
    println(disk2.withIndex().filter { it.value >= 0 }.sumOf { it.index * it.value })
}

fun MutableList<Long>.compact() {
    var iR = this.size - 1
    var iL = this.indexOfFirst { it == -1L }

    while (iL <= iR) {
        this[iL] = this[iR]
        this[iR] = -1
        while (this[iL] != -1L) iL++
        while (this[iR] == -1L) iR--
    }
}

fun MutableList<Long>.compact2() {
    var currNum = this.max()
    val sizes = this.groupingBy { it }.eachCount()

    while (currNum >= 0) {
        val numIndex = this.indexOf(currNum)
        val reqSize = sizes[currNum]!!
        val firstSlot = this.asSequence().take(numIndex).withIndex().windowed(reqSize).firstOrNull { it.all { it.value == -1L } }
        if (firstSlot != null) {
            for (i in 0..<reqSize) {
                this[firstSlot[i].index] = this[numIndex+i]
                this[numIndex+i] = -1
            }
        }
        currNum--
    }
}
