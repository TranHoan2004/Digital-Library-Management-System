package com.se1853_jv.bookservice.dto.response

import java.time.LocalDateTime

data class WrapperApiResponse(
    val status: Int,
    val message: String,
    val data: Any?,
    val timestamp: LocalDateTime = LocalDateTime.now()
)