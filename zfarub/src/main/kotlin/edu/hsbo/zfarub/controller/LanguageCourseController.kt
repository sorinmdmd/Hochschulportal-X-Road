package edu.hsbo.zfarub.controller

import edu.hsbo.zfarub.entities.LanguageCourse
import edu.hsbo.zfarub.service.LanguageCourseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class LanguageCourseController(val languageCourseService: LanguageCourseService) {
    @GetMapping("/languageCourses")
    fun getAll() = languageCourseService.findAll()

    @GetMapping("languageCourse/{id}")
    fun getById(@PathVariable id: String) = languageCourseService.findById(id)
        ?: ResponseEntity.notFound().build<Any>()

    @PostMapping("/languageCourse")
    fun create(@RequestBody course: LanguageCourse) = languageCourseService.save(course)

    @PutMapping("languageCourse/{id}")
    fun update(@PathVariable id: String, @RequestBody course: LanguageCourse) =
        languageCourseService.update(id, course)
            ?: ResponseEntity.notFound().build<Any>()

    @DeleteMapping("languageCourse/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> =
        if (languageCourseService.delete(id)) ResponseEntity.noContent().build()
        else ResponseEntity.notFound().build()

}