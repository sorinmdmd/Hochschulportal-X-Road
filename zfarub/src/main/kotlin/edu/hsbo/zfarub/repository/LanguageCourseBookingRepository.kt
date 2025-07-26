package edu.hsbo.zfarub.repository

import edu.hsbo.zfarub.entities.LanguageCourseBooking
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface LanguageCourseBookingRepository : MongoRepository<LanguageCourseBooking, String> {
    @Query("{ 'studentId': ?0, 'course': ?1 }")
    fun findByStudentIdAndCourseId(studentId: String, course: ObjectId?): LanguageCourseBooking?
}