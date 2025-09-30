package com.se1853_jv.bookservice.dto.response

class BookResponse(
    val id: String?,
    val isbn: String?,
    val title: String?,
    val description: String?,
    val author: String?,
    val availability: String?,
    val bookCode: List<String>?,
    val coverImage: String?,
    val publishedDate: String?,
    val updatedDate: String?,
    val createdDate: String?,
)