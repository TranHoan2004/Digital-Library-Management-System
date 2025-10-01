package com.se1853_jv.reviewservice.service

import com.se1853_jv.reviewservice.dto.response.ReviewResponse
import com.se1853_jv.reviewservice.model.CompositeKey
import com.se1853_jv.reviewservice.model.Review
import com.se1853_jv.reviewservice.repository.ReviewRepository
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
class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository
) : ReviewService {

    override fun createReview(
        content: String,
        bookId: String,
        userId: String,
    ): ReviewResponse {
        val review = Review(
            id = CompositeKey(UUID.randomUUID().toString(), userId),
            content = content,
            bookId = bookId,
            createdDate = LocalDateTime.now(),
            updatedDate = LocalDateTime.now()
        )
        logger.info { "Creating new review with id=${review.id}" }
        return convert(reviewRepository.save(review))
    }

    override fun getReviewById(
        id: String,
        userId: String
    ): ReviewResponse {
        logger.info { "Fetching review with id=$id" }
        val response = reviewRepository.findById(CompositeKey(id, userId))
            .orElseThrow { throw IllegalArgumentException("Cannot found the comment") }
        return convert(response)
    }

    override fun getReviewsByBook(
        bookId: String,
        pageIndex: Int,
        pageSize: Int?
    ): Page<ReviewResponse> {
        logger.info { "Get reviews list with bookId=$bookId, page $pageIndex of $pageSize" }
        val pageRequest = PageRequest.of(pageIndex, pageSize ?: DEFAULT_PAGE_SIZE)
        val target = reviewRepository.findAll(pageRequest)
            .content
            .filter { it.bookId == bookId }
            .map { convert(it) }
        return PageImpl(target, pageRequest, target.size.toLong())
    }

    override fun getReviewsByAuthor(
        userId: String,
        pageIndex: Int,
        pageSize: Int?
    ): Page<ReviewResponse> {
        logger.info { "Get reviews list with user=$userId, page $pageIndex of $pageSize" }
        val pageRequest = PageRequest.of(pageIndex, pageSize ?: DEFAULT_PAGE_SIZE)
        val target = reviewRepository.findAll(pageRequest)
            .content
            .filter { it.id?.userId == userId }
            .map { convert(it) }
        return PageImpl(target, pageRequest, target.size.toLong())
    }

    override fun updateReview(
        id: String,
        userId: String,
        content: String
    ): ReviewResponse {
        val existing = reviewRepository.findById(CompositeKey(id, userId)).orElseThrow {
            IllegalArgumentException("Cannot found the comment")
        }
        val updated = existing.copy(
            content = content,
            updatedDate = LocalDateTime.now()
        )
        logger.info { "Updating review with id=$id" }
        return convert(reviewRepository.save(updated))
    }

    override fun deleteReview(id: String, userId: String) {
        if (!reviewRepository.existsByIdIdAndIdUserId(id, userId)) {
            IllegalArgumentException("Cannot found the comment")
        }
        logger.info { "Deleting review with id=$id" }
        reviewRepository.deleteByIdIdAndIdUserId(id, userId)
    }

    fun convert(target: Review) =
        ReviewResponse(
            target.id?.id!!,
            target.id.userId!!,
            target.bookId!!,
            target.content!!,
            target.createdDate!!,
            target.updatedDate
        )
}