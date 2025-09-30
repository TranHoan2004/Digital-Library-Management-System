package com.se1853_jv.bookservice.repository

import com.se1853_jv.bookservice.model.BookShelf
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BookShelfRepository : MongoRepository<BookShelf, String> {
    fun findByBookshelfCode(code: String): BookShelf?
}