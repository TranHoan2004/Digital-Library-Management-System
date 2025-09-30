package com.se1853_jv.bookservice.dto.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request object for creating a book")
data class CreateBookRequest(
    @param:Schema(description = "ISBN of the book", example = "978-3-16-148410-0")
    val isbn: String,

    @param:Schema(description = "Title of the book", example = "Clean Code")
    val title: String,
    val description: String,

    @param:Schema(description = "Author of the book", example = "Robert C. Martin")
    val author: String,
    val bookCode: List<String>,
    val publicationYear: Int,
    val coverImage: String? = null,
)
