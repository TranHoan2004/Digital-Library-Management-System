package com.se1853_jv.bookservice.service.review

import com.se1853_jv.bookservice.dto.response.ReviewResponse
import com.se1853_jv.bookservice.model.Review
import org.springframework.data.domain.Page

interface ReviewService {
    fun createReview(content: String): Review
    fun getReviewById(id: String): ReviewResponse?
    fun updateReview(id: String, content: String)
    fun deleteReview(id: String)
}