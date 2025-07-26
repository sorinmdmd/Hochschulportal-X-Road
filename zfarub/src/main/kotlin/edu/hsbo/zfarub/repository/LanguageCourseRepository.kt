package edu.hsbo.zfarub.repository

import edu.hsbo.zfarub.entities.LanguageCourse
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface LanguageCourseRepository : MongoRepository<LanguageCourse, String> {
    fun findById(id: String?): LanguageCourse
}