package day03

import util.Rect
import util.readInput
import util.shouldBe

fun main() {
    val testInput = readInput(Input::class, "test").parseInput()
    testInput.part1() shouldBe 4361
    testInput.part2() shouldBe 467835

    val input = readInput(Input::class).parseInput()
    println("output for part1: ${input.part1()}")
    println("output for part2: ${input.part2()}")
}

private data class Number(
    val value: Int,
    val area: Rect,
)

private data class Symbol(
    val char: Char,
    val area: Rect,
)

private class Input(
    val height: Int,
    val numbers: List<Number>,
    val symbols: List<Symbol>,
)

private fun List<String>.parseInput(): Input {
    val numberPattern = Regex("""\d+""")
    val symbolPattern = Regex("""[^\d.]""")
    val numbers = mutableListOf<Number>()
    val symbols = mutableListOf<Symbol>()
    for ((y, line) in this.withIndex()) {
        numbers += numberPattern
            .findAll(line)
            .map { Number(it.value.toInt(), Rect(it.range, y..y)) }
        symbols += symbolPattern
            .findAll(line)
            .map { Symbol(it.value.first(), Rect(it.range, y..y).expandedBy(1)) }
    }
    return Input(size, numbers, symbols)
}

private fun Input.part1(): Int {
    return numbers
        .asSequence()
        .filter { number -> symbols.any { it.area intersects number.area } }
        .sumOf { it.value }
}

private fun Input.part2(): Int {
    return symbols
        .asSequence()
        .filter { it.char == '*' }
        .sumOf { symbol ->
            val parts = numbers.filter { it.area intersects symbol.area }
            when (parts.size) {
                2 -> parts.first().value * parts.last().value
                else -> 0
            }
        }
}
