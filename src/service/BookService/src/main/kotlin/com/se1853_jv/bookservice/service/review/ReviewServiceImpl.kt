package com.se1853_jv.bookservice.service.review

import com.se1853_jv.bookservice.dto.response.ReviewResponse
import com.se1853_jv.bookservice.model.Review
import com.se1853_jv.bookservice.repository.ReviewRepository
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Service
class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository
) : ReviewService {

    override fun createReview(content: String): Review {
        val review = Review(
            id = UUID.randomUUID().toString(),
            content = content,
            createdDate = LocalDateTime.now(),
            updatedDate = LocalDateTime.now()
        )
        logger.info { "Creating new review with id=${review.id}" }
        return reviewRepository.save(review)
    }

    override fun getReviewById(id: String): ReviewResponse? {
        logger.info { "Fetching review with id=$id" }
        val response = reviewRepository.findById(id).orElse(null)
        if (response == null) {
            IllegalArgumentException("Review with id $id was not found")
        }
        return convert(response)
    }

    override fun getReviewsByBook(bookId: String, page: Int, size: Int): Page<ReviewResponse> {
        logger.info { "Fetching reviews for bookId=$bookId, page=$page, size=$size" }
        val pageable = PageRequest.of(page, size)
        return reviewRepository.findByBookId(bookId, pageable).map({ convert(it) })
    }

    override fun updateReview(id: String, content: String) {
        val existing = reviewRepository.findById(id).orElseThrow {
            IllegalArgumentException("Review with id=$id not found")
        }
        val updated = existing.copy(
            content = content,
            updatedDate = LocalDateTime.now()
        )
        logger.info { "Updating review with id=$id" }
        reviewRepository.save(updated)
    }

    override fun deleteReview(id: String) {
        if (!reviewRepository.existsById(id)) {
            throw IllegalArgumentException("Review with id=$id not found")
        }
        logger.info { "Deleting review with id=$id" }
        reviewRepository.deleteById(id)
    }

    fun convert(target: Review) =
        ReviewResponse(
            target.id!!,
            target.content!!,
            target.createdDate!!,
            target.updatedDate
        )
}