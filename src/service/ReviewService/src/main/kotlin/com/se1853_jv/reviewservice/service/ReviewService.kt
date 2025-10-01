package com.se1853_jv.reviewservice.service

import com.se1853_jv.reviewservice.dto.response.ReviewResponse
import org.springframework.data.domain.Page

interface ReviewService {
    fun createReview(content: String, bookId: String, userId: String): ReviewResponse
    fun getReviewById(id: String, userId: String): ReviewResponse
    fun getReviewsByBook(bookId: String, pageIndex: Int, pageSize: Int?): Page<ReviewResponse>
    fun getReviewsByAuthor(userId: String, pageIndex: Int, pageSize: Int?): Page<ReviewResponse>

    fun updateReview(id: String, userId: String, content: String): ReviewResponse
    fun deleteReview(id: String, userId: String)
}