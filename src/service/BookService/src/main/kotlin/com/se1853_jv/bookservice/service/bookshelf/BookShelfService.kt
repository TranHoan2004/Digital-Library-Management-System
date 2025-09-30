package com.se1853_jv.bookservice.service.bookshelf

import com.se1853_jv.bookservice.dto.request.CreateShelfRequest
import com.se1853_jv.bookservice.dto.request.UpdateBookShelfRequest
import com.se1853_jv.bookservice.dto.response.BookResponse
import com.se1853_jv.bookservice.dto.response.BookShelfResponse
import com.se1853_jv.bookservice.model.BookShelf
import org.springframework.data.domain.Page

interface BookShelfService {
    fun createShelf(request: CreateShelfRequest)
    fun getShelfById(id: String): BookShelfResponse?
    fun getShelfByCode(code: String): BookShelfResponse?
    fun getAllShelves(pageIndex: Int, pageSize: Int?): Page<BookShelfResponse>
    fun updateShelf(id: String, request: UpdateBookShelfRequest)
    fun deleteShelf(id: String)

    // Tương tác với Book
    fun getBooksInShelf(shelfId: String, pageIndex: Int, pageSize: Int?): Page<BookResponse>
    fun addBookToShelf(shelfId: String, bookId: String)
    fun removeBookFromShelf(shelfId: String, bookId: String)
}