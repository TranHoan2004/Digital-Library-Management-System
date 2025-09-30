package com.se1853_jv.bookservice.controller

import com.se1853_jv.bookservice.dto.request.CreateBookRequest
import com.se1853_jv.bookservice.dto.request.UpdateBookRequest
import com.se1853_jv.bookservice.dto.response.BookResponse
import com.se1853_jv.bookservice.dto.response.WrapperApiResponse
import com.se1853_jv.bookservice.service.book.BookService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.springframework.data.domain.Page
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
@RequestMapping("/books")
@Tag(name = "Books", description = "Book management APIs")
class BookController(private val service: BookService) {

    @PostMapping
    @Operation(
        summary = "Create a new book",
        description = "Register a new book with ISBN, title, and author",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Book creation request",
            required = true,
            content = [Content(schema = Schema(implementation = CreateBookRequest::class))]
        ),
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Book created successfully",
                content = [Content(schema = Schema(implementation = BookResponse::class))]
            ),
            ApiResponse(responseCode = "400", description = "Invalid request data")
        ]
    )
    fun createBook(@RequestBody request: CreateBookRequest) {
        logger.info { "POST /books - Create book with ISBN=${request.isbn}, title=${request.title}" }
        service.createNewBook(request)
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get book by ID",
        parameters = [
            Parameter(name = "id", description = "Book ID", required = true, example = "64f9f12c1e8b2c5a8e77d5f1")
        ],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Book retrieved successfully",
                content = [Content(schema = Schema(implementation = BookResponse::class))]
            ),
            ApiResponse(responseCode = "404", description = "Book not found")
        ]
    )
    fun getBookById(@PathVariable id: String): BookResponse? {
        logger.info { "GET /books/$id - Fetch book by ID" }
        return service.getBookById(id)
    }

    @GetMapping("/author/{author}")
    @Operation(
        summary = "Get books by author",
        parameters = [
            Parameter(name = "author", description = "Author name", example = "Robert C. Martin")
        ],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Books retrieved successfully",
                content = [Content(schema = Schema(implementation = BookResponse::class))]
            )
        ]
    )
    fun getBooksByAuthor(
        @PathVariable author: String,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(required = false) pageSize: Int?
    ): ResponseEntity<WrapperApiResponse> {
        logger.info { "GET /books/author/$author - Fetch books by author in page $pageIndex and $pageSize records" }
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Find book with author $author in page $pageIndex successfully",
                service.getBooksByAuthor(author, pageIndex, pageSize)
            )
        )
    }

    @GetMapping("/code/{code}")
    fun getBookByCode(@PathVariable code: String): ResponseEntity<WrapperApiResponse> {
        logger.info { "GET /books/code/$code - Fetch book by code" }
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Find book with code $code successfully",
                service.getBookByCode(code)
            )
        )
    }

    @GetMapping("/available")
    fun getAvailableBooks(
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(required = false) pageSize: Int?
    ): ResponseEntity<WrapperApiResponse> {
        logger.info { "GET /books/available - Fetch available books" }
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Find available books successfully",
                service.getAvailableBooks(pageIndex, pageSize)
            )
        )
    }

    @GetMapping("/search")
    @Operation(
        summary = "Search books by title",
        parameters = [
            Parameter(name = "keyword", description = "Search keyword", example = "Code")
        ],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Books matching keyword",
                content = [Content(schema = Schema(implementation = BookResponse::class))]
            )
        ]
    )
    fun findBooksByTitle(
        @RequestParam keyword: String,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(required = false) pageSize: Int?
    ): ResponseEntity<WrapperApiResponse> {
        logger.info { "GET /books/search?keyword=$keyword - Search books by title" }
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Find books with keyword $keyword successfully",
                service.findBooksByTitle(keyword, pageIndex, pageSize)
            )
        )
    }

    @GetMapping
    fun getBooksByPage(
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(required = false) pageSize: Int?
    ): ResponseEntity<WrapperApiResponse> {
        logger.info { "GET /books?pageIndex=$pageIndex&pageSize=$pageSize - Fetch books by page" }
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Get books list with page index $pageIndex and $pageSize records successfully",
                service.getBooksByPageIndex(pageIndex, pageSize)
            )
        )
    }

    @PutMapping
    @Operation(
        summary = "Update book information",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Book update request",
            required = true,
            content = [Content(schema = Schema(implementation = UpdateBookRequest::class))]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Book updated successfully"),
            ApiResponse(responseCode = "404", description = "Book not found")
        ]
    )
    fun updateBook(@RequestBody request: UpdateBookRequest): ResponseEntity<WrapperApiResponse> {
        logger.info { "PUT /books - Update book with ID=${request.id}" }
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Update book information successfully",
                service.updateBook(request)
            )
        )
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete book by ID",
        parameters = [
            Parameter(name = "id", description = "Book ID", example = "64f9f12c1e8b2c5a8e77d5f1")
        ],
        responses = [
            ApiResponse(responseCode = "200", description = "Book deleted successfully"),
            ApiResponse(responseCode = "404", description = "Book not found")
        ]
    )
    fun deleteBookById(@PathVariable id: String): ResponseEntity<WrapperApiResponse> {
        logger.info { "DELETE /books/$id - Delete book by ID" }
        service.deleteBookById(id)
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Delete book successfully",
                null
            )
        )
    }

    @DeleteMapping("/author/{author}")
    fun deleteBooksByAuthor(@PathVariable author: String): ResponseEntity<WrapperApiResponse> {
        logger.info { "DELETE /books/author/$author - Delete books by author" }
        service.deleteBooksByAuthor(author)
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Delete book successfully",
                null
            )
        )
    }
}
