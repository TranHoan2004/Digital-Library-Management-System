package com.se1853_jv.reviewservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.time.LocalDateTime

@Document
data class Review(
    @Id
    val id: CompositeKey? = null,
    val content: String? = null,
    val createdDate: LocalDateTime? = null,
    val updatedDate: LocalDateTime? = null,
    val bookId: String? = null,
)

data class CompositeKey(
    val id: String? = null,
    val userId: String? = null,
) : Serializable