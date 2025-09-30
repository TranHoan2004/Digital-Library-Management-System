package com.se1853_jv.bookservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime

@Document
data class Book(
    @Id
    val id: String? = null,
    val isbn: String? = null,
    val title: String? = null,
    val description: String? = null,
    val author: String? = null,
    val availability: Boolean? = null, // true if available, false if checked out
    val bookCode: List<String>? = null,
    val publicationYear: Int? = null,
    val updatedDate: LocalDateTime? = null,
    val createdDate: LocalDateTime? = null,
    val coverImage: String? = null,
    val publisher: String? = null,
    val language: String? = null,
    val earliestAvailableTime: LocalDate? = null,
    val numberOfLoans: Int? = null,

    @DBRef
    val bookshelf: BookShelf? = null,
)