package com.se1853_jv.bookservice.service.book

import com.se1853_jv.bookservice.dto.request.CreateBookRequest
import com.se1853_jv.bookservice.dto.request.UpdateBookRequest
import com.se1853_jv.bookservice.dto.response.BookResponse
import org.springframework.data.domain.Page

interface BookService {
    fun createNewBook(request: CreateBookRequest): BookResponse
    fun getBookById(id: String): BookResponse?
    fun getBooksByAuthor(author: String, pageIndex: Int, pageSize: Int?): Page<BookResponse>
    fun getBookByCode(code: String): BookResponse?
    fun getAvailableBooks(pageIndex: Int, pageSize: Int?): Page<BookResponse>
    fun findBooksByTitle(keyword: String, pageIndex: Int, pageSize: Int?): Page<BookResponse>
    fun getBooksByPageIndex(pageIndex: Int, pageSize: Int?): Page<BookResponse>
    fun getBooksByBookShelfId(shelfId: String?, pageIndex: Int, pageSize: Int?): Page<BookResponse>
    fun updateBook(request: UpdateBookRequest): BookResponse
    fun deleteBookById(id: String)
    fun deleteBooksByAuthor(author: String)
}