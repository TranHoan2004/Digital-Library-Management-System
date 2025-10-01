package com.se1853_jv.reviewservice.repository

import com.se1853_jv.reviewservice.model.CompositeKey
import com.se1853_jv.reviewservice.model.Review
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : MongoRepository<Review, CompositeKey> {
    fun existsByIdIdAndIdUserId(id: String, userId: String): Boolean
    fun deleteByIdIdAndIdUserId(id: String, userId: String): Int
}