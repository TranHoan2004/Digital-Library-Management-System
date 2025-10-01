package com.se1853_jv.bookservice.controller

import com.se1853_jv.bookservice.dto.request.CreateShelfRequest
import com.se1853_jv.bookservice.dto.request.UpdateBookShelfRequest
import com.se1853_jv.bookservice.dto.response.WrapperApiResponse
import com.se1853_jv.bookservice.service.bookshelf.BookShelfService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/bookshelves")
class BookshelfController(private val service: BookShelfService) {

    @PostMapping
    fun createBookShelf(@RequestBody shelf: CreateShelfRequest): ResponseEntity<WrapperApiResponse> {
        logger.info { "POST /bookshelves - Create bookshelf with code=${shelf.bookshelfCode}, name=${shelf.name}" }
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Create bookshelf successfully",
                service.createShelf(shelf)
            )
        )
    }

    @PostMapping("/{id}/books/{bookId}")
    fun addBookToShelf(@PathVariable id: String, @PathVariable bookId: String): ResponseEntity<Any> {
        logger.info { "POST /bookshelves/$id/books/$bookId - Add book to shelf" }
        service.addBookToShelf(id, bookId)
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Add book to the bookshelf successfully",
                null
            )
        )
    }

    @GetMapping("/{id}")
    fun getShelfById(@PathVariable id: String): ResponseEntity<WrapperApiResponse> {
        logger.info { "GET /bookshelves/$id - Fetch bookshelf by ID" }
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Get bookshelf successfully",
                service.getShelfById(id)
            )
        )
    }

    @GetMapping("/code/{code}")
    fun getShelfByCode(@PathVariable code: String): ResponseEntity<Any> {
        logger.info { "GET /bookshelves/code/$code - Fetch bookshelf by code" }
        val message = { "Get bookshelf successfully with code $code" }
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                message.toString(),
                service.getShelfByCode(code)
            )
        )
    }

    @GetMapping("/{id}/books")
    fun getBooksInShelf(
        @PathVariable id: String,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(required = false) pageSize: Int?
    ): ResponseEntity<Any> {
        logger.info { "GET /bookshelves/$id/books - Fetch books in shelf" }
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Get books in bookshelf successfully",
                service.getBooksInShelf(id, pageIndex, pageSize)
            )
        )
    }

    @GetMapping
    fun getAllShelves(
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(required = false) pageSize: Int?
    ): ResponseEntity<Any> {
        logger.info { "GET /bookshelves - Fetch all bookshelves" }
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Get all bookshelves successfully",
                service.getAllShelves(pageIndex, pageSize)
            )
        )
    }

    @PutMapping
    fun updateShelf(@RequestBody request: UpdateBookShelfRequest): ResponseEntity<Any> {
        logger.info { "PUT /bookshelves/${request.id} - Update bookshelf" }
        service.updateShelf(request.id, request)
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Update bookshelf successfully",
                null
            )
        )
    }

    @DeleteMapping
    fun deleteShelf(@RequestBody id: String): ResponseEntity<Any> {
        logger.info { "DELETE /bookshelves/$id - Delete bookshelf" }
        service.deleteShelf(id)
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Delete bookshelf successfully",
                null
            )
        )
    }

    @DeleteMapping("/{id}/books/{bookId}")
    fun removeBookFromShelf(@PathVariable id: String, @PathVariable bookId: String): ResponseEntity<Any> {
        logger.info { "DELETE /bookshelves/$id/books/$bookId - Remove book from shelf" }
        service.removeBookFromShelf(id, bookId)
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Remove book in the bookshelf successfully",
                null
            )
        )
    }
}
