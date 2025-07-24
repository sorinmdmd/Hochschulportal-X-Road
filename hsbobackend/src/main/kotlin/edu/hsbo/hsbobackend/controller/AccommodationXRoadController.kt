package edu.hsbo.hsbobackend.controller

import edu.hsbo.hsbobackend.entities.Student
import edu.hsbo.hsbobackend.service.AccommodationXRoadService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AccommodationXRoadController(val xRoadAccommodationService: AccommodationXRoadService) {

    @GetMapping("/x-road/accommodations")
    @PreAuthorize("hasRole('STUDENT')")
    fun getStudents(): ResponseEntity<String> {
        return xRoadAccommodationService.getAccommodationsList()
    }
}