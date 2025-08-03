package edu.hsbo.hsbobackend.controller

import edu.hsbo.hsbobackend.service.LanguageCourseXRoadService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class LanguageCourseXRoadController(val languageCourseXRoadService: LanguageCourseXRoadService) {

    @GetMapping("/x-road/languageCourses")
    @PreAuthorize("hasRole('STUDENT')")
    fun getAvailableAccommodations(): ResponseEntity<String> {
        return languageCourseXRoadService.getLanguageCourses()
    }

    @PostMapping("/x-road/languageCourse/{languageCourseId}")
    @PreAuthorize("hasRole('STUDENT')")
    fun bookLanguageCourse(
        @PathVariable languageCourseId: String,
    ): ResponseEntity<String> {
        return languageCourseXRoadService.bookLanguageCourse(languageCourseId)
    }

    @GetMapping("/x-road/myLanguageCourses")
    @PreAuthorize("hasRole('STUDENT')")
    fun myLanguageCourse(): ResponseEntity<String> {
        return languageCourseXRoadService.findStudentCourses()
    }
}