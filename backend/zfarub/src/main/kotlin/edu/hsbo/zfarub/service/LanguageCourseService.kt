package edu.hsbo.zfarub.service

import edu.hsbo.zfarub.entities.LanguageCourse
import edu.hsbo.zfarub.repository.LanguageCourseRepository
import org.springframework.stereotype.Service

@Service
class LanguageCourseService(var languageCourseRepository: LanguageCourseRepository) {
    fun findAll(): List<LanguageCourse> = languageCourseRepository.findAll()

    fun findById(id: String): LanguageCourse? = languageCourseRepository.findById(id).orElse(null)

    fun save(languageCourse: LanguageCourse): LanguageCourse = languageCourseRepository.save(languageCourse)

    fun update(id: String, updatedCourse: LanguageCourse): LanguageCourse? {
        val existingCourse = languageCourseRepository.findById(id)
        return if (existingCourse.isPresent) {
            val courseToSave = updatedCourse.copy(id = id)
            languageCourseRepository.save(courseToSave)
        } else {
            null
        }
    }

    fun delete(id: String): Boolean {
        return if (languageCourseRepository.existsById(id)) {
            languageCourseRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}