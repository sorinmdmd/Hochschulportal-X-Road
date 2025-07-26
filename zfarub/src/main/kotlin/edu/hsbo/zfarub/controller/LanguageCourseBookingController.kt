package edu.hsbo.zfarub.controller

import edu.hsbo.zfarub.entities.LanguageCourseBooking
import edu.hsbo.zfarub.repository.LanguageCourseRepository
import edu.hsbo.zfarub.service.LanguageCourseBookingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class LanguageCourseBookingController(val languageCourseBookingService: LanguageCourseBookingService) {

    @PostMapping("booking")
    fun createBooking(@RequestParam studentId: String, @RequestParam courseId: String): ResponseEntity<Any> {
        return try {
            val booking = languageCourseBookingService.createBooking(studentId, courseId)
            ResponseEntity.ok(booking)
        } catch (e: IllegalStateException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }

    @GetMapping("/{id}")
    fun getBooking(@PathVariable id: String): ResponseEntity<LanguageCourseBooking> {
        val booking = languageCourseBookingService.getBookingById(id)
        return if (booking != null) {
            ResponseEntity.ok(booking)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAllBookings(): ResponseEntity<List<LanguageCourseBooking>> {
        return ResponseEntity.ok(languageCourseBookingService.getAllBookings())
    }

    @GetMapping("/student/{studentId}")
    fun getBookingsByStudent(@PathVariable studentId: String): ResponseEntity<List<LanguageCourseBooking>> {
        return ResponseEntity.ok(languageCourseBookingService.getBookingsByStudentId(studentId))
    }

    @DeleteMapping("/{id}")
    fun cancelBooking(@PathVariable id: String): ResponseEntity<Any> {
        return if (languageCourseBookingService.cancelBooking(id)) {
            ResponseEntity.ok(mapOf("message" to "Booking cancelled successfully"))
        } else {
            ResponseEntity.notFound().build()
        }
    }
}