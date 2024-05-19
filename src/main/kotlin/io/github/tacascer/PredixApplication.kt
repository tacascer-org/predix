package io.github.tacascer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PredixApplication

fun main(args: Array<String>) {
    runApplication<PredixApplication>(*args)
}
