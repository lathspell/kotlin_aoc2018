package day1

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Part2Test {

    private fun solve(input: List<Int>): Int {
        val found = mutableSetOf(0)
        var acc: Int = 0
        while (true) {
            input.map {
                acc += it
                if (!found.add(acc)) {
                    return acc
                }
            }
        }
    }

    @Test
    fun testExamples() {
        assertEquals(2, solve(listOf(1, -2, 3, 1, 1, -2)))
        assertEquals(0, solve(listOf(1, -1)))
        assertEquals(10, solve(listOf(3, 3, 4, -2, -4)))
        assertEquals(5, solve(listOf(-6, 3, 8, 5, -6)))
        assertEquals(14, solve(listOf(7, 7, -2, -7, -4)))
    }

    @Test
    fun testChallenge() {
        val input = File("src/test/kotlin/day1/input.txt").readLines().map { it.toInt() }
        assertEquals(69074, solve(input))
    }
}