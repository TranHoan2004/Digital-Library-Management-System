package com.se1853_jv.reviewservice.controller

import com.se1853_jv.reviewservice.dto.request.CreateRequest
import com.se1853_jv.reviewservice.dto.request.IdRequest
import com.se1853_jv.reviewservice.dto.request.UpdateReviewRequest
import com.se1853_jv.reviewservice.dto.response.WrapperApiResponse
import com.se1853_jv.reviewservice.service.ReviewService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/reviews")
class ReviewController(private val service: ReviewService) {

    @PostMapping
    fun createReview(@RequestBody request: CreateRequest): ResponseEntity<WrapperApiResponse> {
        logger.info { "POST /reviews - Create review with content=$request" }
        val response = service.createReview(request.content, request.bookId, request.userId)
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Create new review successfully",
                response
            )
        )
    }

    @GetMapping
    fun getReviewById(
        @RequestParam id: String,
        @RequestParam("user") userId: String
    ): ResponseEntity<WrapperApiResponse> {
        logger.info { "GET /reviews/ - Fetch review by ID" }
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Get review successfully",
                service.getReviewById(id, userId)
            )
        )
    }

    @PutMapping
    fun updateReview(@RequestBody request: UpdateReviewRequest): ResponseEntity<WrapperApiResponse> {
        logger.info { "PUT /reviews/$request - Update review with new content=${request.content}" }
        val response = service.updateReview(request.id, request.userId, request.content)
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Update review successfully",
                response
            )
        )
    }

    @DeleteMapping
    fun deleteReview(@RequestBody request: IdRequest): ResponseEntity<WrapperApiResponse> {
        logger.info { "DELETE /reviews/$request - Delete review" }
        service.deleteReview(request.id, request.userId)
        return ResponseEntity.ok(
            WrapperApiResponse(
                HttpStatus.OK.value(),
                "Delete comment successfully",
                null
            )
        )
    }
}
