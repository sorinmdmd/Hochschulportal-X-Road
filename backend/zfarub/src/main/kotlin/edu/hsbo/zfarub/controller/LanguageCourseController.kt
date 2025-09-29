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

    /**
     * Ruft eine Liste aller verfügbaren Sprachkurse ab.
     * GET /api/languageCourses
     * @return Eine Liste von LanguageCourse-Objekten.
     */
    @GetMapping("/languageCourses")
    fun getAll() = languageCourseService.findAll()

    /**
     * Ruft einen bestimmten Sprachkurs anhand seiner eindeutigen ID ab.
     * GET /api/languageCourse/{id}
     * @param id Die ID des abzurufenden Kurses.
     * @return Den Sprachkurs, falls gefunden, andernfalls den Status 404 (Not Found).
     */
    @GetMapping("languageCourse/{id}")
    fun getById(@PathVariable id: String) = languageCourseService.findById(id)
        ?: ResponseEntity.notFound().build<Any>()

    /**
     * Erstellt einen neuen Sprachkurs.
     * POST /api/languageCourse
     * @param course Das zu erstellende LanguageCourse-Objekt aus dem Request Body.
     * @return Der gespeicherte Sprachkurs.
     */
    @PostMapping("/languageCourse")
    fun create(@RequestBody course: LanguageCourse) = languageCourseService.save(course)

    /**
     * Aktualisiert einen bestehenden Sprachkurs.
     * PUT /api/languageCourse/{id}
     * @param id Die ID des zu aktualisierenden Kurses.
     * @param course Das LanguageCourse-Objekt mit den neuen Daten aus dem Request Body.
     * @return Der aktualisierte Sprachkurs, falls gefunden, andernfalls den Status 404 (Not Found).
     */
    @PutMapping("/languageCourse/{id}")
    fun update(@PathVariable id: String, @RequestBody course: LanguageCourse) =
        languageCourseService.update(id, course)
            ?: ResponseEntity.notFound().build<Any>()

    /**
     * Löscht einen Sprachkurs anhand seiner ID.
     * DELETE /api/languageCourse/{id}
     * @param id Die ID des zu löschenden Kurses.
     * @return ResponseEntity mit dem Status 204 (No Content) bei Erfolg oder 404 (Not Found), falls der Kurs nicht existiert.
     */
    @DeleteMapping("/languageCourse/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> =
        if (languageCourseService.delete(id)) ResponseEntity.noContent().build()
        else ResponseEntity.notFound().build()

}