package com.se1853_jv.bookservice.service.bookshelf

import com.se1853_jv.bookservice.dto.request.CreateShelfRequest
import com.se1853_jv.bookservice.dto.request.UpdateBookShelfRequest
import com.se1853_jv.bookservice.dto.response.BookResponse
import com.se1853_jv.bookservice.dto.response.BookShelfResponse
import com.se1853_jv.bookservice.model.BookShelf
import com.se1853_jv.bookservice.repository.BookRepository
import com.se1853_jv.bookservice.repository.BookShelfRepository
import com.se1853_jv.bookservice.service.book.BookService
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.UUID

private val logger = KotlinLogging.logger {}
private const val DEFAULT_PAGE_SIZE = 10

@Service
class BookShelfServiceImpl(
    private val shelfRepo: BookShelfRepository,
    private val bookRepo: BookRepository,
    private val bookService: BookService,
) : BookShelfService {

    override fun createShelf(request: CreateShelfRequest) {
        logger.info { "Creating bookshelf with code: ${request.bookshelfCode}" }
        val shelf = buildShelf(
            UUID.randomUUID().toString(),
            request.bookshelfCode,
            request.name,
            request.qrImage
        )
        shelfRepo.save(shelf)
    }

    override fun getShelfById(id: String): BookShelfResponse? =
        convertToResponse(shelfRepo.findById(id).orElse(null))

    override fun getShelfByCode(code: String): BookShelfResponse? =
        convertToResponse(shelfRepo.findByBookshelfCode(code))

    override fun getAllShelves(pageIndex: Int, pageSize: Int?): Page<BookShelfResponse> {
        val pageRequest = PageRequest.of(pageIndex, pageSize ?: DEFAULT_PAGE_SIZE)
        return shelfRepo.findAll(pageRequest).map { convertToResponse(it) }
    }

    override fun updateShelf(id: String, request: UpdateBookShelfRequest) {
        val existing = shelfRepo.findById(id).orElseThrow {
            IllegalArgumentException("Bookshelf with id $id not found")
        }
        val updated = buildShelf(
            id = existing.id!!,
            code = request.bookshelfCode ?: existing.bookshelfCode!!,
            name = request.name ?: existing.name,
            qrImage = request.qrImage ?: existing.qrImage
        )
        shelfRepo.save(updated)
    }

    override fun deleteShelf(id: String) {
        if (!shelfRepo.existsById(id)) {
            throw IllegalArgumentException("Bookshelf with id $id not found")
        }
        shelfRepo.deleteById(id)
        logger.info { "Deleted bookshelf with id: $id" }
    }

    override fun getBooksInShelf(shelfId: String, pageIndex: Int, pageSize: Int?): Page<BookResponse> {
        val shelf = shelfRepo.findById(shelfId).orElseThrow {
            IllegalArgumentException("Bookshelf with id $shelfId not found")
        }
        return bookService.getBooksByBookShelfId(shelf.bookshelfCode, pageIndex, pageSize)
    }

    override fun addBookToShelf(shelfId: String, bookId: String) {
        val shelf = shelfRepo.findById(shelfId).orElseThrow {
            IllegalArgumentException("Bookshelf with id $shelfId not found")
        }
        val book = bookRepo.findById(bookId).orElseThrow {
            IllegalArgumentException("Book with id $bookId not found")
        }
        val updatedBook = book.copy(bookshelf = shelf)
        bookRepo.save(updatedBook)
    }

    override fun removeBookFromShelf(shelfId: String, bookId: String) {
        val shelf = shelfRepo.findById(shelfId).orElseThrow {
            IllegalArgumentException("Bookshelf with id $shelfId not found")
        }
        val book = bookRepo.findById(bookId).orElseThrow {
            IllegalArgumentException("Book with id $bookId not found")
        }
        if (book.bookshelf?.bookshelfCode != shelf.bookshelfCode) {
            throw IllegalArgumentException("Book $bookId is not in shelf $shelfId")
        }
        val updatedBook = book.copy(bookshelf = null)
        bookRepo.save(updatedBook)
    }

    fun convertToResponse(shelf: BookShelf?): BookShelfResponse {
        return BookShelfResponse(
            id = shelf?.id,
            bookshelfCode = shelf?.bookshelfCode,
            name = shelf?.name,
            qrImage = shelf?.qrImage
        )
    }

    fun buildShelf(
        id: String,
        code: String,
        name: String?,
        qrImage: String?
    ): BookShelf {
        return BookShelf(
            id = id,
            bookshelfCode = code,
            name = name,
            qrImage = qrImage
        )
    }
}