package com.se1853_jv.bookservice.service.book

import com.se1853_jv.bookservice.dto.request.CreateBookRequest
import com.se1853_jv.bookservice.dto.request.UpdateBookRequest
import com.se1853_jv.bookservice.dto.response.BookResponse
import com.se1853_jv.bookservice.model.Book
import com.se1853_jv.bookservice.repository.BookRepository
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

private val logger = KotlinLogging.logger {}
private const val DEFAULT_PAGE_SIZE = 10

@Service
class BookServiceImpl(
    private val repo: BookRepository
) : BookService {

    override fun createNewBook(request: CreateBookRequest): BookResponse {
        logger.info { "Creating new Book" }
        if (isBookCodeExists(request.bookCode)) {
            throw IllegalArgumentException("Book code already exists")
        }
        val book = buildBookEntity(
            id = UUID.randomUUID().toString(),
            isbn = request.isbn,
            title = request.title,
            description = request.description,
            author = request.author,
            availability = true,
            bookCode = request.bookCode,
            publicationYear = request.publicationYear,
            createdDate = LocalDateTime.now(),
            coverImage = request.coverImage,
        )
        logger.info { "Book created with id: ${book.id}" }
        return convertToResponse(repo.save(book))
    }

    override fun getBookById(id: String): BookResponse? {
        logger.info { "Getting book with id: $id" }
        val book = repo.findById(id).orElse(null)
        return convertToResponse(book ?: return null)
    }

    override fun getBooksByAuthor(author: String, pageIndex: Int, pageSize: Int?): Page<BookResponse> {
        logger.info { "Getting book with author: $author" }
        val books = getBooksByPageIndex(pageIndex, pageSize)
        val filterBooks = books.content
            .filter { it.author == author }
        return buildPageIml(filterBooks, pageIndex, pageSize ?: DEFAULT_PAGE_SIZE)
    }

    override fun getBookByCode(code: String): BookResponse? {
        logger.info { "Getting book with code: $code" }
        val book = repo.findAll().find { it.bookCode?.contains(code) == true }
        return convertToResponse(book ?: return null)
    }

    override fun getAvailableBooks(pageIndex: Int, pageSize: Int?): Page<BookResponse> {
        logger.info { "Getting available book" }
        val books = getBooksByPageIndex(pageIndex, pageSize)
        val filterBooks = books.content
            .filter { it.availability == "true" }
        return buildPageIml(filterBooks, pageIndex, pageSize ?: DEFAULT_PAGE_SIZE)
    }

    override fun findBooksByTitle(keyword: String, pageIndex: Int, pageSize: Int?): Page<BookResponse> {
        logger.info { "Find book by title" }
        val books = getBooksByPageIndex(pageIndex, pageSize)
        val filterBooks = books.content.filter { it.title?.contains(keyword, ignoreCase = true) == true }
        return buildPageIml(filterBooks, pageIndex, pageSize ?: DEFAULT_PAGE_SIZE)
    }

    override fun getBooksByPageIndex(pageIndex: Int, pageSize: Int?): Page<BookResponse> {
        logger.info { "Get books by page index" }
        val pageRequest: PageRequest = PageRequest.of(pageIndex, pageSize ?: DEFAULT_PAGE_SIZE)
        val books = repo.findAll(pageRequest)
        return books.map { convertToResponse(it) }
    }

    override fun getBooksByBookShelfId(shelfId: String?, pageIndex: Int, pageSize: Int?): Page<BookResponse> {
        logger.info { "Get books by page index" }
        val pageRequest: PageRequest = PageRequest.of(pageIndex, pageSize ?: DEFAULT_PAGE_SIZE)
        val books = repo.findAll(pageRequest)
        val filterBooks = books.content
            .filter { it.bookshelf?.id == shelfId }
            .map { convertToResponse(it) }
        return buildPageIml(filterBooks, pageIndex, pageSize ?: DEFAULT_PAGE_SIZE)
    }

    override fun updateBook(request: UpdateBookRequest): BookResponse {
        logger.info { "Updating book with id: ${request.id}" }
        val existing = repo.findById(request.id).orElseThrow {
            IllegalArgumentException("Book with id ${request.id} not found")
        }

        val updated = buildBookEntity(
            id = request.id,
            isbn = request.isbn ?: existing.isbn,
            title = request.title ?: existing.title,
            description = request.description ?: existing.description,
            author = request.author ?: existing.author,
            availability = request.availability ?: existing.availability,
            bookCode = request.bookCode ?: existing.bookCode,
            publicationYear = request.publicationYear ?: existing.publicationYear,
            coverImage = request.coverImage ?: existing.coverImage,
        ).copy(createdDate = existing.createdDate)

        repo.save(updated)
        logger.info { "Book with id ${request.id} updated successfully" }
        return convertToResponse(updated)
    }

    override fun deleteBookById(id: String) {
        logger.info { "Deleting book with id: $id" }
        if (!repo.existsById(id)) {
            throw IllegalArgumentException("Book with id $id not found")
        }
        repo.deleteById(id)
        logger.info { "Book with id $id deleted successfully" }
    }

    override fun deleteBooksByAuthor(author: String) {
        logger.info { "Deleting books by author: $author" }
        val books = repo.findBookByAuthor(author)
        if (books.isEmpty()) {
            throw IllegalArgumentException("No books found for author $author")
        }
        repo.deleteByAuthor(author)
        logger.info { "Deleted ${books.size} books by author: $author" }
    }

    private fun isBookCodeExists(code: List<String>): Boolean {
        return repo.existsByBookCode(code.toMutableList())
    }

    private fun buildBookEntity(
        id: String?,
        isbn: String?,
        title: String?,
        description: String?,
        author: String?,
        bookCode: List<String>?,
        publicationYear: Int?,
        availability: Boolean?,
        createdDate: LocalDateTime? = null,
        coverImage: String? = null,
    ): Book {
        return Book(
            id = id,
            isbn = isbn,
            title = title,
            description = description,
            author = author,
            bookCode = bookCode,
            publicationYear = publicationYear,
            updatedDate = LocalDateTime.now(),
            availability = availability,
            createdDate = createdDate,
            coverImage = coverImage,
        )
    }

    private fun convertToResponse(book: Book): BookResponse = BookResponse(
        id = book.id,
        isbn = book.isbn,
        title = book.title,
        description = book.description,
        author = book.author,
        availability = book.availability.toString(),
        bookCode = book.bookCode,
        publishedDate = book.publicationYear.toString(),
        updatedDate = book.updatedDate.toString(),
        createdDate = book.createdDate.toString(),
        coverImage = book.coverImage.toString(),
    )

    private fun buildPageIml(page: List<BookResponse>, pageIndex: Int, pageSize: Int?): Page<BookResponse> =
        PageImpl(
            page,
            PageRequest.of(pageIndex, pageSize ?: DEFAULT_PAGE_SIZE),
            page.size.toLong(),
        )
}