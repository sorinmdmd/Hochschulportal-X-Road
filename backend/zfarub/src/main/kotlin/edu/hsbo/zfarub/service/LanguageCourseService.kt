package edu.hsbo.zfarub.service

import edu.hsbo.zfarub.entities.LanguageCourse
import edu.hsbo.zfarub.repository.LanguageCourseRepository
import org.springframework.stereotype.Service

@Service
class LanguageCourseService(var languageCourseRepository: LanguageCourseRepository) {
    /**
     * Ruft alle Sprachkurse aus der Datenbank ab.
     * @return Eine Liste aller LanguageCourse-Objekte.
     */
    fun findAll(): List<LanguageCourse> = languageCourseRepository.findAll()

    /**
     * Findet einen Sprachkurs anhand seiner eindeutigen ID.
     * @param id Die ID des zu suchenden Kurses.
     * @return Der gefundene Sprachkurs oder null, wenn kein Kurs mit dieser ID existiert.
     */
    fun findById(id: String): LanguageCourse? = languageCourseRepository.findById(id).orElse(null)

    /**
     * Speichert einen neuen Sprachkurs in der Datenbank.
     * @param languageCourse Das zu speichernde LanguageCourse-Objekt.
     * @return Das gespeicherte LanguageCourse-Objekt.
     */
    fun save(languageCourse: LanguageCourse): LanguageCourse = languageCourseRepository.save(languageCourse)

    /**
     * Aktualisiert einen bestehenden Sprachkurs.
     * @param id Die ID des zu aktualisierenden Kurses.
     * @param updatedCourse Das Objekt mit den neuen Daten für den Kurs.
     * @return Der aktualisierte Sprachkurs oder null, wenn kein Kurs mit der angegebenen ID gefunden wurde.
     */
    fun update(id: String, updatedCourse: LanguageCourse): LanguageCourse? {
        val existingCourse = languageCourseRepository.findById(id)
        return if (existingCourse.isPresent) {
            val courseToSave = updatedCourse.copy(id = id)
            languageCourseRepository.save(courseToSave)
        } else {
            null
        }
    }

    /**
     * Löscht einen Sprachkurs anhand seiner ID.
     * @param id Die ID des zu löschenden Kurses.
     * @return true, wenn der Kurs erfolgreich gelöscht wurde, andernfalls false.
     */
    fun delete(id: String): Boolean {
        return if (languageCourseRepository.existsById(id)) {
            languageCourseRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}