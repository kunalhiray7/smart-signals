package com.hack

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SmartSignalsApp {
}

fun main(args: Array<String>) {
    runApplication<SmartSignalsApp>(*args)
}