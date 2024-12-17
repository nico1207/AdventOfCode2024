package day17

import readInput
import kotlin.math.pow
import kotlin.random.Random

fun main() {
    val input = readInput(17, false).lines()
    var a = input[0].substringAfter(": ").toLong()
    var b = input[1].substringAfter(": ").toLong()
    var c = input[2].substringAfter(": ").toLong()
    val program = input[4].substringAfter(": ").split(',').map { it.toLong() }
    val target = program.joinToString("").toLong()

    println(simulate(a, b, c, program).joinToString(","))

    a = 1

    // Find upper bound based on output length
    var upperBound: Long
    while (true) {
        val output = simulate(a, b, c, program)
        if (output.size <= program.size) {
            a *= 3
        } else {
            upperBound = a
            println(output)
            println(program)
            break
        }
    }

    // Find lower bound based on output length
    var lowerBound: Long
    while (true) {
        val output = simulate(a, b, c, program)
        if (output.size >= program.size) {
            a /= 2
        } else {
            lowerBound = a
            println(output)
            println(program)
            break
        }
    }

    println("Lower: $lowerBound")
    println("Upper: $upperBound")

    var goodGuess = 202322338131545

    // Probabilistic good guess finder
    /*
    while (true) {
        var r = Random.nextLong(lowerBound, upperBound)
        val output = simulate(r, b, c, program)
        if (output[output.size-1] == 0L &&
            output[output.size-2] == 3L &&
            output[output.size-3] == 5L &&
            output[output.size-4] == 5L &&
            output[output.size-5] == 5L &&
            output[output.size-6] == 4L &&
            output[output.size-7] == 4L &&
            output[output.size-8] == 1L) {
            if (r < goodGuess) {
                println("$output for $r")
                goodGuess = r
                upperBound = goodGuess
            }
        }
    }
    */

    // Proximity finder from good guess
    /*
    for (i in 0L..1000000000L) {
        val try1 = simulate(goodGuess + i, b, c, program)
        val try2 = simulate(goodGuess - i, b, c, program)

        if (try1 == program) {
            println("Up: ${goodGuess+i}")
        }
        if (try2 == program) {
            println("Down: ${goodGuess-i}")
        }
    }
    */
}

fun simulate(aInit: Long, bInit: Long, cInit: Long, program: List<Long>): List<Long> {
    var a = aInit
    var b = bInit
    var c = cInit
    var ptr = 0

    fun readCombo(v: Long): Long {
        return when (v) {
            in 0L..3L -> v
            4L -> a
            5L -> b
            6L -> c
            else -> throw Error("should never happen")
        }
    }

    val output = mutableListOf<Long>()

    while (true) {
        val operand = program.getOrNull(ptr+1) ?: 0
        when(program.getOrNull(ptr)) {
            null -> break
            0L -> { // adv
                val num = a
                val den = 2.0.pow(readCombo(operand).toInt())
                a = (num / den).toLong()
                ptr += 2
            }
            1L -> { // bxl
                b = b.xor(operand)
                ptr += 2
            }
            2L -> { // bst
                b = readCombo(operand) % 8
                ptr += 2
            }
            3L -> { // jnz
                if (a == 0L) {
                    ptr += 2
                } else {
                    ptr = operand.toInt()
                }
            }
            4L -> { // bxc
                b = b.xor(c)
                ptr += 2
            }
            5L -> { // out
                output.add(readCombo(operand) % 8)
                ptr += 2
            }
            6L -> { // bdv
                val num = a
                val den = 2.0.pow(readCombo(operand).toInt())
                b = (num / den).toLong()
                ptr += 2
            }
            7L -> { // cdv
                val num = a
                val den = 2.0.pow(readCombo(operand).toInt())
                c = (num / den).toLong()
                ptr += 2
            }
        }
    }

    return output
}