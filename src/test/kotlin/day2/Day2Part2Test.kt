package day2

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class Day2Part2Test {

    private fun countDifferences(a: String, b: String) = a.indices.count { a[it] != b[it] }

    private fun findMatching(input: List<String>): Pair<String, String> {
        input.indices.forEach { a ->
            ((a+1) until input.size).forEach { b ->
                if (countDifferences(input[a], input[b]) == 1) {
                    return Pair(input[a], input[b])
                }
            }
        }
        throw RuntimeException("Nothing found!")
    }

    private fun solve(input: List<String>) = findMatching(input).run { first.filterIndexed { i, c -> c == second[i] } }

    @Test
    fun testExamples() {
        assertEquals(2, countDifferences("abcde", "axcye"))
        assertEquals(1, countDifferences("fghij", "fguij"))
        assertEquals(Pair("fghij", "fguij"), findMatching(listOf("abcde", "fghij", "klmno", "pqrst", "fguij", "axcye", "wvxyz")))
        assertEquals("fgij", solve(listOf("abcde", "fghij", "klmno", "pqrst", "fguij", "axcye", "wvxyz")))
    }

    @Test
    fun testChallenge() {
        val input = File("src/test/kotlin/day2/input.txt").readLines()
        assertEquals("evsialkqyiurohzpwucngttmf", solve(input))
    }
}