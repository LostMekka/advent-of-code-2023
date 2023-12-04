package util

import java.io.File
import kotlin.reflect.KClass

fun readInput(dayNumber: Int, fileName: String) =
    dayNumber.toString()
        .padStart(2, '0')
        .let { "day${it}/$fileName" }
        .let { File("src/$it.txt").readLines() }

fun readInput(dayClass: KClass<*>, fileName: String = "input") = readInput(dayClass.dayNumber, fileName)

private val KClass<*>.dayNumber get() = qualifiedName!!.split(".").first().removePrefix("day").toInt()
