package edu.hsbo.zfarub.service

import edu.hsbo.zfarub.entities.LanguageCourse
import edu.hsbo.zfarub.entities.LanguageCourseBooking
import edu.hsbo.zfarub.repository.LanguageCourseBookingRepository
import edu.hsbo.zfarub.repository.LanguageCourseRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Service
class LanguageCourseBookingService(
    val languageCourseBookingRepository: LanguageCourseBookingRepository,
    val languageCourseRepository: LanguageCourseRepository
) {
    /**
     * Erstellt eine neue Buchung für einen Studenten in einem Sprachkurs.
     * Führt mehrere Prüfungen durch, bevor die Buchung erstellt wird.
     * @param studentId Die ID des Studenten.
     * @param courseId Die ID des Kurses.
     * @return Das neu erstellte LanguageCourseBooking-Objekt.
     * @throws IllegalStateException wenn der Student den Kurs bereits gebucht hat oder keine Plätze mehr frei sind.
     * @throws IllegalArgumentException wenn kein Kurs mit der angegebenen ID gefunden wird.
     */
    fun createBooking(studentId: String, courseId: String): LanguageCourseBooking? {
        // Prüfen, ob der Student diesen Kurs bereits gebucht hat
        val objectId = ObjectId(courseId)
        val existingBooking = languageCourseBookingRepository.findByStudentIdAndCourseId(studentId, objectId)

        if (existingBooking != null) {
            throw IllegalStateException("Student has already booked this course")
        }

        // Den Kurs abrufen und die Verfügbarkeit prüfen
        val course = languageCourseRepository.findById(courseId).orElse(null)
            ?: throw IllegalArgumentException("Course not found")

        if (course.availableSlots <= 0) {
            throw IllegalStateException("No available slots for this course")
        }

        // Verfügbare Plätze aktualisieren
        course.availableSlots -= 1
        languageCourseRepository.save(course)

        // Buchung erstellen
        val booking = LanguageCourseBooking(
            studentId = studentId,
            course = course
        )

        return languageCourseBookingRepository.save(booking)
    }

    /**
     * Ruft eine einzelne Buchung anhand ihrer eindeutigen ID ab.
     * @param id Die ID der zu suchenden Buchung.
     * @return Die gefundene Buchung oder null, wenn keine Buchung mit dieser ID existiert.
     */
    fun getBookingById(id: String): LanguageCourseBooking? {
        return languageCourseBookingRepository.findById(id).orElse(null)
    }

    /**
     * Findet alle Buchungen, die zu einer bestimmten Studenten-ID gehören.
     * @param studentId Die ID des Studenten.
     * @return Eine Liste der Buchungen für den angegebenen Studenten.
     */
    fun getBookingsByStudentId(studentId: String): List<LanguageCourseBooking> {
        return languageCourseBookingRepository.findAll().filter { it.studentId == studentId }
    }

    /**
     * Ruft eine Liste aller existierenden Buchungen ab.
     * @return Eine Liste aller LanguageCourseBooking-Objekte.
     */
    fun getAllBookings(): List<LanguageCourseBooking> {
        return languageCourseBookingRepository.findAll()
    }

    /**
     * Storniert eine Buchung anhand ihrer ID.
     * Wenn die Buchung storniert wird, wird ein Platz im entsprechenden Kurs wieder freigegeben.
     * @param id Die ID der zu stornierenden Buchung.
     * @return true, wenn die Stornierung erfolgreich war, andernfalls false.
     */
    fun cancelBooking(id: String): Boolean {
        val booking = languageCourseBookingRepository.findById(id).orElse(null)
            ?: return false

        // Verfügbaren Platz wiederherstellen
        booking.course?.let { course ->
            course.availableSlots += 1
            languageCourseRepository.save(course)
        }

        languageCourseBookingRepository.deleteById(id)
        return true
    }
}