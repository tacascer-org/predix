package com.github.tacascer.predix

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PredixApplication

fun main(args: Array<String>) {
	runApplication<PredixApplication>(*args)
}
