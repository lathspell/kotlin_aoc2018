package day3

import java.io.File
import java.util.regex.Pattern
import kotlin.test.Test
import kotlin.test.assertEquals

class Day3Test {

    private class Claim(val id: Int, val dx: Int, val dy: Int, val width: Int, val height: Int) {
        companion object {
            private val pattern = Pattern.compile("""^#(\d+) @ (\d+),(\d+): (\d+)x(\d+)$""")
            fun parse(line: String): Claim {
                val matcher = pattern.matcher(line)
                check(matcher.find()) { "Invalid line: |$line|" }
                val id = matcher.group(1).toInt()
                val dx = matcher.group(2).toInt()
                val dy = matcher.group(3).toInt()
                val width = matcher.group(4).toInt()
                val height = matcher.group(5).toInt()
                return Claim(id, dx, dy, width, height)
            }
        }
    }

    private class Claims(private val xsize: Int, private val ysize: Int) {
        val matrix = MutableList(ysize) { MutableList(xsize) { 0 } }

        override fun toString() =
                matrix.fold("") { yacc, column ->
                    yacc + column.fold("") { xacc, cell ->
                        xacc + String.format("%5d ", cell)
                    } + "\n"
                }


        fun readLines(lines: List<String>): Claims {
            lines.forEach { readLine(it) }
            return this
        }

        fun readLine(line: String): Claims {
            Claim.parse(line).run {
                (0 until width).forEach { xi ->
                    (0 until height).forEach { yi ->
                        val x = dx + xi
                        val y = dy + yi
                        matrix[y][x] = if (matrix[y][x] == 0) id else -1
                    }
                }
            }
            return this
        }

        fun calcProblems(): Int = matrix.sumBy { columns -> columns.count { it == -1 } }

        fun check(lines: List<String>): List<Int> =
                lines.map { Claim.parse(it) }
                        .filter { checkClaim(it) }
                        .map { it.id }

        fun checkClaim(claim : Claim) : Boolean {
            (0 until claim.width).forEach { xi ->
                (0 until claim.height).forEach { yi ->
                    val x = claim.dx + xi
                    val y = claim.dy + yi
                    if (matrix[y][x] != claim.id) return false
                }
            }
            return true
        }
    }

    @Test
    fun testExample1() {
        val claims = Claims(10, 10).readLine("#123 @ 3,2: 5x4")
        println(claims)
    }

    @Test
    fun testExample2() {
        val input = listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2")
        val claims = Claims(10, 10).readLines(input)
        println(claims)
        assertEquals(4, claims.calcProblems());
    }

    @Test
    fun testChallenge() {
        val input = File("src/test/kotlin/day3/input.txt").readLines()
        assertEquals(105231, Claims(1000, 1000).readLines(input).calcProblems())
    }

    @Test
    fun testChallengePart2() {
        val input = File("src/test/kotlin/day3/input.txt").readLines()
        val claims = Claims(1000, 1000).readLines(input)
        assertEquals(105231, claims.calcProblems())
        assertEquals(listOf(164), claims.check(input))
    }
}