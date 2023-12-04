package day02

import util.readInput
import util.shouldBe

fun main() {
    val testInput = readInput(Input::class, "test").parseInput()
    testInput.part1() shouldBe 8
    testInput.part2() shouldBe 2286

    val input = readInput(Input::class).parseInput()
    println("output for part1: ${input.part1()}")
    println("output for part2: ${input.part2()}")
}

private data class Game(
    val id: Int,
    val draws: List<Map<String, Int>>,
)

private class Input(
    val games: List<Game>,
)

private fun List<String>.parseInput(): Input {
    return Input(
        games = map { line ->
            val (gamePart, drawsPart) = line.split(":")
            Game(
                id = gamePart.removePrefix("Game ").toInt(),
                draws = drawsPart.split(";").map { draw ->
                    draw.split(",").associate {
                        val (numberPart, colorPart) = it.trim().split(" ")
                        colorPart to numberPart.toInt()
                    }
                },
            )
        }
    )
}

private fun Input.part1(): Int {
    val limit = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14,
    )
    return games
        .filter { game ->
            game.draws.all { draw ->
                draw.all { (color, amount) -> limit.getOrDefault(color, 0) >= amount }
            }
        }
        .sumOf { it.id }
}

private fun Input.part2(): Int {
    // huh... if fold does not specify explicit type arguments,
    //        the compiler fails to infer that it only can use the Int version of sumOf
    return games.sumOf { game ->
        listOf("red", "green", "blue")
            .map { color -> game.draws.maxOf { it.getOrDefault(color, 0) } }
            .fold<Int, Int>(1) { acc, amount -> acc * amount }
    }
}
