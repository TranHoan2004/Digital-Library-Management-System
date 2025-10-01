package com.se1853_jv.reviewservice.dto.request

data class UpdateReviewRequest(
    val id: String,
    val userId: String,
    val content: String,
)