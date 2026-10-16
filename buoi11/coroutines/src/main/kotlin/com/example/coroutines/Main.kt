package com.example.coroutines

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

// Bài 1: suspend, delay, async/await và structured concurrency.
suspend fun getForecast(): String { delay(1_000); return "Sunny" }
suspend fun getTemperature(): String { delay(1_000); return "30°C" }

suspend fun sequentialWeather(): String = "${getForecast()} ${getTemperature()}"

suspend fun concurrentWeather(): String = coroutineScope {
    val forecast = async { getForecast() }
    val temperature = async { getTemperature() }
    "${forecast.await()} ${temperature.await()}"
}

suspend fun cancellationExample(): Boolean = coroutineScope {
    val temperature = async { getTemperature() }
    delay(100)
    temperature.cancelAndJoin()
    temperature.isCancelled
}

suspend fun exceptionExample(): String = coroutineScope {
    val temperature = async {
        try {
            delay(100)
            throw IllegalStateException("Không đọc được nhiệt độ")
        } catch (error: IllegalStateException) {
            "--°C"
        }
    }
    "Sunny ${temperature.await()}"
}

fun main() = runBlocking {
    println("Buổi 11 — Coroutine trong Kotlin Playground")
    val sequentialTime = measureTimeMillis { println("Tuần tự: ${sequentialWeather()}") }
    val concurrentTime = measureTimeMillis { println("Đồng thời: ${concurrentWeather()}") }
    println("Thời gian tuần tự: $sequentialTime ms; đồng thời: $concurrentTime ms")
    println("Hủy tác vụ: ${cancellationExample()}")
    println("Xử lý lỗi: ${exceptionExample()}")
    // launch phù hợp khi không cần trả giá trị; join đợi coroutine hoàn tất.
    val first = launch { delay(100); println("launch: tác vụ 1 hoàn tất") }
    val second = launch { delay(50); println("launch: tác vụ 2 hoàn tất") }
    joinAll(first, second)
}
