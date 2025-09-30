package com.se1853_jv.bookservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookServiceApplication

fun main(args: Array<String>) {
    runApplication<BookServiceApplication>(*args)
}
