package com.se1853_jv.reviewservice.dto.response

import java.time.LocalDateTime

data class ReviewResponse(
    val id: String,
    val userId: String,
    val bookId: String,
    val content: String,
    val createdDate: LocalDateTime,
    val updatedDate: LocalDateTime?,
)