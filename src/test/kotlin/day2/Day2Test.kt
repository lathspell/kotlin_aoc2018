package day2

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class Day2Test {

    private fun hasSame(input: String, wanted: Int) = input.groupBy { it }.any { it.value.size == wanted }

    private fun checksum(input: List<String>) = input.count { hasSame(it, 2) } * input.count { hasSame(it, 3) }

    @Test
    fun testExamples() {
        assertEquals(false, hasSame("abcde", 2))
        assertEquals(true, hasSame("bababc", 2))
        assertEquals(true, hasSame("bababc", 3))
        assertEquals(true, hasSame("abbcde", 2))
        assertEquals(false, hasSame("abbcde", 3))
    }

    @Test
    fun testChallenge() {
        val input = File("src/test/kotlin/day2/input.txt").readLines()
        assertEquals(6448, checksum(input))
    }
}