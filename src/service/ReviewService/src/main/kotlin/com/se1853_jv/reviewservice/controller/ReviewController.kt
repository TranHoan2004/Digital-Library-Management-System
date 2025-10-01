package com.se1853_jv.bookservice.controller

import com.se1853_jv.bookservice.dto.request.UpdateReviewRequest
import com.se1853_jv.bookservice.dto.response.WrapperApiResponse
import com.se1853_jv.bookservice.service.review.ReviewService
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
@RequestMapping("/reviews")
class ReviewController(
    private val service: ReviewService
) {

    @PostMapping
    fun createReview(@RequestParam content: String): ResponseEntity<WrapperApiResponse> {
        logger.info { "POST /reviews - Create review with content=$content" }
        service.createReview(content)
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Create new reviews successfully",
                null
            )
        )
    }

    @GetMapping("/{id}")
    fun getReviewById(@PathVariable id: String): ResponseEntity<WrapperApiResponse> {
        logger.info { "GET /reviews/$id - Fetch review by ID" }
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Get reviews with id $id successfully",
                service.getReviewById(id)
            )
        )
    }

    @PutMapping
    fun updateReview(@RequestBody request: UpdateReviewRequest): ResponseEntity<WrapperApiResponse> {
        logger.info { "PUT /reviews/$request - Update review with new content=${request.content}" }
        service.updateReview(request.id, request.content)
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Update review successfully",
                null
            )
        )
    }

    @DeleteMapping("/{id}")
    fun deleteReview(@PathVariable id: String) {
        logger.info { "DELETE /reviews/$id - Delete review" }
        service.deleteReview(id)
    }
}
