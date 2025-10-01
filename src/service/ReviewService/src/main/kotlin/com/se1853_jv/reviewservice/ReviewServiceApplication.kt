<<<<<<<< HEAD:src/service/BookService/src/main/kotlin/com/se1853_jv/bookservice/BookServiceApplication.kt
package com.se1853_jv.bookservice
========
package com.se1853_jv.reviewservice
>>>>>>>> 375e2e98b293b61d98a0e28f2636731a3d3dd943:src/service/ReviewService/src/main/kotlin/com/se1853_jv/reviewservice/ReviewServiceApplication.kt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
<<<<<<<< HEAD:src/service/BookService/src/main/kotlin/com/se1853_jv/bookservice/BookServiceApplication.kt
class BookServiceApplication

fun main(args: Array<String>) {
    runApplication<BookServiceApplication>(*args)
========
class ReviewServiceApplication

fun main(args: Array<String>) {
    runApplication<ReviewServiceApplication>(*args)
>>>>>>>> 375e2e98b293b61d98a0e28f2636731a3d3dd943:src/service/ReviewService/src/main/kotlin/com/se1853_jv/reviewservice/ReviewServiceApplication.kt
}
