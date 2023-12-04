package day04

import util.readInput
import util.shouldBe
import kotlin.math.pow

fun main() {
    val testInput = readInput(Input::class,"test").parseInput()
    testInput.part1() shouldBe 13
    testInput.part2() shouldBe 30

    val input = readInput(Input::class).parseInput()
    println("output for part1: ${input.part1()}")
    println("output for part2: ${input.part2()}")
}

private data class Card(
    val id: Int,
    val winningNumbers: Set<Int>,
    val numbersYouHave: Set<Int>,
)

private class Input(
    val cards: List<Card>,
)

private fun List<String>.parseInput(): Input {
    val cardPattern = Regex("""^Card +(\d+): (.+) \| (.+)$""")
    return Input(
        map { line ->
            val (idPart, winPart, havePart) = cardPattern.matchEntire(line)!!.groupValues.drop(1)
            Card(
                idPart.toInt(),
                winPart.chunked(3).mapTo(mutableSetOf()) { it.trim().toInt() },
                havePart.chunked(3).mapTo(mutableSetOf()) { it.trim().toInt() },
            )
        }
    )
}

private fun Input.part1(): Int {
    return cards.sumOf {
        val hits = it.winningNumbers.intersect(it.numbersYouHave).size
        2.0.pow(hits - 1).toInt()
    }
}

private fun Input.part2(): Int {
    val cardCounts = cards.associate { it.id to 1 }.toMutableMap()
    for (card in cards) {
        val amount = cardCounts.getValue(card.id)
        val hits = card.winningNumbers.intersect(card.numbersYouHave).size
        for (i in (card.id + 1)..(card.id + hits)) {
            cardCounts.compute(i) { _, old -> old!! + amount }
        }
    }
    return cardCounts.values.sum()
}
