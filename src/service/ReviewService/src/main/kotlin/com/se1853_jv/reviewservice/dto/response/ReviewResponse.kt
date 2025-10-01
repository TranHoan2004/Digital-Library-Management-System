package com.se1853_jv.bookservice.dto.response

import java.time.LocalDateTime

data class ReviewResponse(
    val id: String,
    val content: String,
    val createdDate: LocalDateTime,
    val updatedDate: LocalDateTime?,
)