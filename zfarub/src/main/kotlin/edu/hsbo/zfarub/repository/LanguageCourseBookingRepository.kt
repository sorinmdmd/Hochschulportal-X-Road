package edu.hsbo.zfarub.repository

import edu.hsbo.zfarub.entities.LanguageCourseBooking
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface LanguageCourseBookingRepository : MongoRepository<LanguageCourseBooking, String> {
}