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

    /**
     * Ruft eine Liste aller verfügbaren Sprachkurse über den X-Road-Dienst ab.
     * HTTP-Methode: GET
     * Endpunkt: /api/x-road/languageCourses
     * Berechtigung: Nur für Benutzer mit der Rolle 'STUDENT'.
     * @return Eine ResponseEntity, die die Liste der Kurse als String vom externen Dienst enthält.
     */
    @GetMapping("/x-road/languageCourses")
    @PreAuthorize("hasRole('STUDENT')")
    fun getAvailableLanguageCourses(): ResponseEntity<String> {
        return languageCourseXRoadService.getLanguageCourses()
    }

    /**
     * Bucht einen Sprachkurs für den authentifizierten Studenten über den X-Road-Dienst.
     * HTTP-Methode: POST
     * Endpunkt: /api/x-road/languageCourse/{languageCourseId}
     * Berechtigung: Nur für Benutzer mit der Rolle 'STUDENT'.
     * @param languageCourseId Die ID des zu buchenden Sprachkurses.
     * @return Eine ResponseEntity, die die Antwort (z.B. Bestätigung) vom externen Dienst als String enthält.
     */
    @PostMapping("/x-road/languageCourse/{languageCourseId}")
    @PreAuthorize("hasRole('STUDENT')")
    fun bookLanguageCourse(
        @PathVariable languageCourseId: String,
    ): ResponseEntity<String> {
        return languageCourseXRoadService.bookLanguageCourse(languageCourseId)
    }

    /**
     * Ruft alle Sprachkurse ab, für die der authentifizierte Student aktuell angemeldet ist.
     * Die Daten werden vom X-Road-Dienst bezogen.
     * HTTP-Methode: GET
     * Endpunkt: /api/x-road/myLanguageCourses
     * Berechtigung: Nur für Benutzer mit der Rolle 'STUDENT'.
     * @return Eine ResponseEntity, die die gebuchten Kurse des Studenten als String enthält.
     */
    @GetMapping("/x-road/myLanguageCourses")
    @PreAuthorize("hasRole('STUDENT')")
    fun myLanguageCourse(): ResponseEntity<String> {
        return languageCourseXRoadService.findStudentCourses()
    }
}