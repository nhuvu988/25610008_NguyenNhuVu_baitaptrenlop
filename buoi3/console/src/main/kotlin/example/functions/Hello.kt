package example.functions

import kotlin.random.Random

val week = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
val decorations = listOf("rock", "pagoda", "plastic plant", "alligator", "flowerpot")
fun randomDay(random: Random = Random.Default): String = week[random.nextInt(week.size)]

// Final when/else exercise: Tuesday and Saturday intentionally use the default branch.
fun fishFood(day: String): String = when (day) {
    "Monday" -> "flakes"
    "Wednesday" -> "redworms"
    "Thursday" -> "granules"
    "Friday" -> "mosquitoes"
    "Sunday" -> "plankton"
    else -> "nothing"
}
fun isTooHot(temperature: Int) = temperature > 30
fun isDirty(dirty: Int) = dirty > 30
fun isSunday(day: String) = day == "Sunday"
fun shouldChangeWater(day: String, temperature: Int = 22, dirty: Int = 20): Boolean = when {
    isTooHot(temperature) -> true
    isDirty(dirty) -> true
    isSunday(day) -> true
    else -> false
}
fun feedTheFish(day: String = randomDay()) {
    println("Today is $day and the fish eat ${fishFood(day)}")
    println("Change water: ${shouldChangeWater(day)}")
}
fun swim(speed: String = "fast") { println("swimming $speed") }
fun updateDirty(dirty: Int, operation: (Int) -> Int): Int = operation(dirty)
fun increaseDirty(start: Int) = start + 1

fun demonstrateFilters() {
    val eager = decorations.filter { it.startsWith('p') }
    println("eager: $eager")
    val filtered = decorations.asSequence().filter { it.startsWith('p') }
    println("filtered (not evaluated): $filtered")
    println("new list: ${filtered.toList()}")
    val lazyMap = decorations.asSequence().map { println("access: $it"); it }
    println("lazy: $lazyMap")
    println("first: ${lazyMap.first()}")
    println("all: ${lazyMap.toList()}")
    val lazyMap2 = decorations.asSequence().filter { it.startsWith('p') }
        .map { println("filtered access: $it"); it }
    println("filtered: ${lazyMap2.toList()}")
    val sports = listOf("basketball", "fishing", "running")
    val players = listOf("LeBron James", "Ernest Hemingway", "Usain Bolt")
    val cities = listOf("Los Angeles", "Chicago", "Jamaica")
    println("Flat: ${listOf(sports, players, cities).flatten()}")
}

fun main(args: Array<String>) {
    println("Hello, ${args.firstOrNull() ?: "Kotlin!"}")
    val isUnit = println("This is an expression")
    println(isUnit)
    val temperature = 10
    val isHot = if (temperature > 50) true else false
    println("isHot: $isHot")
    println("The water temperature is ${if (temperature > 50) "too warm" else "OK"}.")
    feedTheFish()
    swim(); swim("slow"); swim(speed = "turtle-like")
    demonstrateFilters()
    val waterFilter: (Int) -> Int = { dirty -> dirty / 2 }
    println(waterFilter(20))
    println(updateDirty(30, waterFilter))
    println(updateDirty(15, ::increaseDirty))
    var dirtyLevel = 19
    dirtyLevel = updateDirty(dirtyLevel) { it + 23 }
    println(dirtyLevel)
}
