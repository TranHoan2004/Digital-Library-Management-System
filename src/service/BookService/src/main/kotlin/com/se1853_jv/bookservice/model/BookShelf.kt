package com.se1853_jv.bookservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class BookShelf(
    @Id
    val id: String? = null,
    val bookshelfCode: String? = null,
    val name: String? = null,
    val qrImage: String? = null
)