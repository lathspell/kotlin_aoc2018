package day1

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Test {

    private fun solve(input: List<Int>): Int = input.fold(0) { acc, it -> acc + it }

    @Test
    fun testExamples() {
        assertEquals(3, solve(listOf(1, -2, 3, 1)))
    }

    @Test
    fun testChallenge() {
        val input = File("src/test/kotlin/day1/input.txt").readLines().map { it.toInt() }
        assertEquals(510, solve(input))
    }
}