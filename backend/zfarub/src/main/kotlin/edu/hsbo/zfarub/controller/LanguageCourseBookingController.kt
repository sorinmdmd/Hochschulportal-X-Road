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

    /**
     * Erstellt eine neue Buchung für einen Sprachkurs.
     * POST /api/booking
     * @param studentId Die ID des Studenten (als Request-Parameter).
     * @param courseId Die ID des zu buchenden Kurses (als Request-Parameter).
     * @return ResponseEntity mit der erstellten Buchung bei Erfolg oder einer Fehlermeldung bei einem Fehler (z.B. Kurs voll).
     */
    @PostMapping("/booking")
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

    /**
     * Ruft eine einzelne Buchung anhand ihrer eindeutigen ID ab.
     * GET /api/courseBooking/{id}
     * @param id Die ID der abzurufenden Buchung (aus der Pfadvariable).
     * @return ResponseEntity mit der gefundenen Buchung oder den Status 404 (Not Found), falls sie nicht existiert.
     */
    @GetMapping("courseBooking/{id}")
    fun getBooking(@PathVariable id: String): ResponseEntity<LanguageCourseBooking> {
        val booking = languageCourseBookingService.getBookingById(id)
        return if (booking != null) {
            ResponseEntity.ok(booking)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * Ruft eine Liste aller Sprachkursbuchungen ab.
     * GET /api/allBookings
     * @return ResponseEntity mit einer Liste aller Buchungen.
     */
    @GetMapping("/allBookings")
    fun getAllBookings(): ResponseEntity<List<LanguageCourseBooking>> {
        return ResponseEntity.ok(languageCourseBookingService.getAllBookings())
    }

    /**
     * Ruft alle Buchungen für einen bestimmten Studenten ab.
     * GET /api/booking/student/{studentId}
     * @param studentId Die ID des Studenten, dessen Buchungen abgerufen werden sollen (aus der Pfadvariable).
     * @return ResponseEntity mit einer Liste der Buchungen des Studenten.
     */
    @GetMapping("/booking/student/{studentId}")
    fun getBookingsByStudent(@PathVariable studentId: String): ResponseEntity<List<LanguageCourseBooking>> {
        return ResponseEntity.ok(languageCourseBookingService.getBookingsByStudentId(studentId))
    }

    /**
     * Storniert eine Buchung anhand ihrer ID.
     * DELETE /api/courseBooking/{id}
     * @param id Die ID der zu stornierenden Buchung (aus der Pfadvariable).
     * @return ResponseEntity mit einer Erfolgsnachricht oder den Status 404 (Not Found), falls die Buchung nicht existiert.
     */
    @DeleteMapping("/courseBooking/{id}")
    fun cancelBooking(@PathVariable id: String): ResponseEntity<Any> {
        return if (languageCourseBookingService.cancelBooking(id)) {
            ResponseEntity.ok(mapOf("message" to "Booking cancelled successfully"))
        } else {
            ResponseEntity.notFound().build()
        }
    }
}