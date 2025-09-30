package com.se1853_jv.bookservice.dto.request

data class UpdateBookRequest(
    val id: String,
    val isbn: String?,
    val title: String?,
    val description: String?,
    val availability: Boolean?,
    val author: String?,
    val bookCode: List<String>?,
    val publicationYear: Int?,
    val coverImage: String?,
)