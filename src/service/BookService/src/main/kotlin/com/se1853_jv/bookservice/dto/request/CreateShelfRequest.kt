package com.se1853_jv.bookservice.dto.request

data class CreateShelfRequest (
    val bookshelfCode: String,
    val name: String?,
    val qrImage: String?
)