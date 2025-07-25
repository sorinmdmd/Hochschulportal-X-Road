package edu.hsbo.zfarub.repository

import edu.hsbo.zfarub.entities.LanguageCourse
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface LanguageCourseRepository : MongoRepository<LanguageCourse, String> {
}