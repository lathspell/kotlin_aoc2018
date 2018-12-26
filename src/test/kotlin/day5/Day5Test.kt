package day5

import org.assertj.core.api.Assertions.assertThat
import java.io.File
import kotlin.test.Test

class Day5Test {

    @Test
    fun testPre1() {
        val line = "dabAcCaCBAcCcaDA"
        assertThat(reactRecursive(line)).isEqualTo("dabCBAcaDA")
    }

    @Test
    fun testPre2() {
        val line = "itLiIleEXxGxXg"
        assertThat(reactRecursive(line)).isEqualTo("it")
    }

    @Test
    fun testChallenge1() {
        val line = File("src/test/kotlin/day5/input.txt").readText().trim()
        assertThat(line).hasSize(50_000)
        val reacted = reactRecursive(line)
        assertThat(reacted).hasSize(9348)
    }

    @Test
    fun testChallenge2() {
        val line = File("src/test/kotlin/day5/input.txt").readText().trim()
        assertThat(line).hasSize(50_000)

        val bestResult = ('a'..'z')
                .map { Pair(it, reactRecursive(line.replace(it.toString(), "").replace(it.toUpperCase().toString(), "")).length) }
                .minBy { it.second }
        assertThat(bestResult).isEqualTo(Pair('f', 4996))
    }

    // TODO: this does not look elegant
    private fun reactRecursive(line: String): String {
        var newLine = line
        do {
            var oldLength = newLine.length
            newLine = react(newLine)
        } while (newLine.length != oldLength)
        return newLine
    }

    private fun react(line: String) = line
            .fold(" ") { acc, c ->
                val last = acc.last()
                when {
                    last.isUpperCase() && last != c && last.toLowerCase() == c -> acc.dropLast(1)
                    last.isLowerCase() && last != c && last.toUpperCase() == c -> acc.dropLast(1)
                    else -> acc + c
                }
            }
            .trimStart()
}