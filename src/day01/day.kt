package day01

import util.readInput
import util.shouldBe

fun main() {
    val testInput1 = readInput(Input::class, "test1").parseInput()
    testInput1.part1() shouldBe 142
    val testInput2 = readInput(Input::class, "test2").parseInput()
    testInput2.part2() shouldBe 281

    val input = readInput(Input::class).parseInput()
    println("output for part1: ${input.part1()}")
    println("output for part2: ${input.part2()}")
}

private class Input(
    val lines: List<String>,
)

private fun List<String>.parseInput(): Input {
    return Input(this)
}

private fun Input.part1(): Int {
    return lines.sumOf { line ->
        val digitsOnly = line.filter { it.isDigit() }
        10 * digitsOnly.first().digitToInt() + digitsOnly.last().digitToInt()
    }
}

private fun Input.part2(): Int {
    return lines.sumOf { line ->
        // fun fact: Regex("one|1|two|2|three|3|four|4|five|5|six|6|seven|7|eight|8|nine|9").findAll(line)
        //   will not find ALL occurrences. if two occurrences overlap, it only finds the first one.
        //   so this approach fails, if there are overlapping digits at the end of the input line,
        //   like in 'twocsfzd1eight7eightwovm' (it will not notice the last 'two')
        //   i first thought to solve this by lookahead, but then i still need to parse the string afterward.
        //   so im keeping it simple and dumb instead ^^
        val digitsOnly = line.indices.mapNotNull {
            val s = line.drop(it)
            when {
                s.first().isDigit() -> s.first().digitToInt()
                s.startsWith("one") -> 1
                s.startsWith("two") -> 2
                s.startsWith("three") -> 3
                s.startsWith("four") -> 4
                s.startsWith("five") -> 5
                s.startsWith("six") -> 6
                s.startsWith("seven") -> 7
                s.startsWith("eight") -> 8
                s.startsWith("nine") -> 9
                else -> null
            }
        }
        10 * digitsOnly.first() + digitsOnly.last()
    }
}
