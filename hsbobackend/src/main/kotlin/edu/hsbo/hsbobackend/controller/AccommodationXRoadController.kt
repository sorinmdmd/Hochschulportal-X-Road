package edu.hsbo.hsbobackend.controller

import edu.hsbo.hsbobackend.entities.Student
import edu.hsbo.hsbobackend.service.AccommodationXRoadService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/api")
class AccommodationXRoadController(val xRoadAccommodationService: AccommodationXRoadService) {

    @GetMapping("/x-road/accommodations")
    @PreAuthorize("hasRole('STUDENT')")
    fun getAvailableAccommodations(): ResponseEntity<String> {
        return xRoadAccommodationService.getAccommodationsList()
    }

    @PostMapping("/x-road/accommodation/{accommodationID}/{startDate}")
    @PreAuthorize("hasRole('STUDENT')")
    fun bookAccommodation(
        @PathVariable accommodationID: String,
        @PathVariable startDate: Instant
    ): ResponseEntity<String> {
        return xRoadAccommodationService.bookAccommodation(accommodationID, startDate)
    }
}