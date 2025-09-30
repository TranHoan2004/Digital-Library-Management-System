package com.se1853_jv.bookservice.dto.request

data class UpdateBookShelfRequest(
    val id: String,
    val bookshelfCode: String?,
    val name: String?,
    val qrImage: String?
)