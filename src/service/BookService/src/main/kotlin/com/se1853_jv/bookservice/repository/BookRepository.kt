package com.se1853_jv.bookservice.repository

import com.se1853_jv.bookservice.model.Book
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : MongoRepository<Book, String> {
    fun existsByBookCode(bookCode: MutableList<String>): Boolean
    fun findBookByAuthor(author: String): List<Book>
    fun deleteByAuthor(author: String)
}