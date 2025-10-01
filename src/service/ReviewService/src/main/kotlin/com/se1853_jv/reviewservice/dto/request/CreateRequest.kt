package com.se1853_jv.reviewservice.dto.request

data class CreateRequest(
    val userId: String,
    val content: String,
    val bookId: String,
)
