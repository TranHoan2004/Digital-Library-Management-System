package com.se1853_jv.bookservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Review(
    @Id
    val id: String? = null,
    val content: String? = null,
    val createdDate: LocalDateTime? = null,
    val updatedDate: LocalDateTime? = null,

    @DBRef val book: Book? = null,
)