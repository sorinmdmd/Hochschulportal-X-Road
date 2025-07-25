package edu.hsbo.zfarub.controller

import edu.hsbo.zfarub.service.LanguageCourseService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class LanguageCourseController(val languageCourseService: LanguageCourseService) {
}