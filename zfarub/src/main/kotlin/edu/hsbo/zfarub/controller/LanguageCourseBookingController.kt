package edu.hsbo.zfarub.controller

import edu.hsbo.zfarub.service.LanguageCourseBookingService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class LanguageCourseBookingController(val languageCourseBookingService: LanguageCourseBookingService) {
}