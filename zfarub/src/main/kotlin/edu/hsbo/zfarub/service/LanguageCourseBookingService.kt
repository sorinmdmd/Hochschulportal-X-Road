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
) { fun createBooking(studentId: String, courseId: String): LanguageCourseBooking? {
    // Check if student already booked this course
    val objectId = ObjectId(courseId)
    val existingBooking = languageCourseBookingRepository.findByStudentIdAndCourseId(studentId, objectId)

    if (existingBooking != null) {
        throw IllegalStateException("Student has already booked this course")
    }

    // Get the course and check availability
    val course = languageCourseRepository.findById(courseId).orElse(null)
        ?: throw IllegalArgumentException("Course not found")

    if (course.availableSlots <= 0) {
        throw IllegalStateException("No available slots for this course")
    }

    // Update available slots
    course.availableSlots -= 1
    languageCourseRepository.save(course)

    // Create booking
    val booking = LanguageCourseBooking(
        studentId = studentId,
        course = course
    )

    return languageCourseBookingRepository.save(booking)
}

    fun getBookingById(id: String): LanguageCourseBooking? {
        return languageCourseBookingRepository.findById(id).orElse(null)
    }

    fun getBookingsByStudentId(studentId: String): List<LanguageCourseBooking> {
        return languageCourseBookingRepository.findAll().filter { it.studentId == studentId }
    }

    fun getAllBookings(): List<LanguageCourseBooking> {
        return languageCourseBookingRepository.findAll()
    }

    fun cancelBooking(id: String): Boolean {
        val booking = languageCourseBookingRepository.findById(id).orElse(null)
            ?: return false

        // Restore available slot
        booking.course?.let { course ->
            course.availableSlots += 1
            languageCourseRepository.save(course)
        }

        languageCourseBookingRepository.deleteById(id)
        return true
    }
}