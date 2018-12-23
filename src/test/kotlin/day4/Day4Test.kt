package day4

import org.junit.Test
import java.io.File
import java.util.regex.Pattern
import kotlin.test.assertEquals

typealias Sleeps = MutableMap<Int, IntArray>

class Day4Test {

    @Test
    fun strategy1Small() {
        val sortedLines = File("src/test/kotlin/day4/input-small.txt").readLines().sorted()
        val sleeps = parseLines(sortedLines)
        val bestMinute = calc1BestMinute(sleeps)
        assertEquals(33, bestMinute)
    }

    @Test
    fun strategy1() {
        val sortedLines = File("src/test/kotlin/day4/input.txt").readLines().sorted()
        val sleeps = parseLines(sortedLines)
        val bestMinute = calc1BestMinute(sleeps)
        assertEquals(39, bestMinute)
    }

    @Test
    fun strategy2() {
        val sortedLines = File("src/test/kotlin/day4/input.txt").readLines().sorted()
        val sleeps = parseLines(sortedLines)
        val guardAndMinute = calc2MostAsleep(sleeps)
        assertEquals(Triple(1901, 51, 19), guardAndMinute)
    }

    private fun calc2MostAsleep(sleeps: Sleeps): Triple<Int, Int, Int> =
            sleeps
                    .map { (guard, sleepingMins) ->
                        // map to: guard -> the minute with max sleeps -> amount of sleeps in this minute
                        sleepingMins.foldIndexed(Triple(-1, -1, 0)) { index, acc, i -> if (i > acc.third) Triple(guard, index, i) else acc }
                    }
                    .maxBy { it.third }!!

    private fun calc1BestMinute(sleeps: Sleeps): Int {
        val guardWithMostSleepingMinutes = sleeps.maxBy { it.value.sum() }!!.key
        println("Best guard: $guardWithMostSleepingMinutes")
        val minuteMap = (0..59).associate { m -> Pair(m, sleeps[guardWithMostSleepingMinutes]!![m]) }
        val bestMinute = minuteMap.toList().sortedByDescending { (_, v) -> v }.first().first
        println("Best minute: $bestMinute => answer is " + (guardWithMostSleepingMinutes * bestMinute))
        return bestMinute
    }

    private fun debugPrintSleeps(sleeps: Sleeps) {
        sleeps.forEach { guard, minuteMap ->
            println("Guard $guard: " + minuteMap.map { String.format("%3d", it) }.joinToString(""))
        }
    }

    private fun parseLines(lines: List<String>): Sleeps {
        val pattern = Pattern.compile("""^\[(\d{4}-\d{2}-\d{2}) (\d\d):(\d\d)\] (Guard #(\d+) begins shift|falls asleep|wakes up)$""")

        val sleeps: MutableMap<Int, IntArray> = mutableMapOf()
        var guard = -1
        var sleepStart = 60

        lines.map { line ->
            val matcher = pattern.matcher(line)
            check(matcher.find()) { throw IllegalArgumentException("Invalid line: |$line|") }
            // val date = LocalDate.parse(matcher.group(1))
            // val currentHour = matcher.group(2).toInt()
            val currentMinute = matcher.group(3).toInt()
            val msg = matcher.group(4)

            when {
                msg.startsWith("Guard") -> {
                    guard = matcher.group(5).toInt()
                    sleeps.putIfAbsent(guard, IntArray(60))
                    sleepStart = 60
                }
                msg == "falls asleep" -> {
                    check(guard != -1) { "Guard was not initialized when waking up!" }
                    sleepStart = currentMinute
                }
                msg == "wakes up" -> {
                    check(sleepStart != 60) { "Guard $guard was not asleep when waking up!" }
                    (sleepStart until currentMinute).forEach { minuteOfHour ->
                        val old = (sleeps[guard]!!).get(minuteOfHour)
                        sleeps[guard]!![minuteOfHour] = old + 1
                    }
                }
            }
        }

        if (sleepStart != -1) {
            (sleepStart..59).forEach { minuteOfHour ->
                val old = (sleeps[guard]!!).get(minuteOfHour)
                sleeps[guard]!![minuteOfHour] = old + 1
            }
        }

        debugPrintSleeps(sleeps)
        return sleeps
    }
}