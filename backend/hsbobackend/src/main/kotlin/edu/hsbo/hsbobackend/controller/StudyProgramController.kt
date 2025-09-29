package edu.hsbo.hsbobackend.controller

import edu.hsbo.hsbobackend.SecurityContext
import edu.hsbo.hsbobackend.entities.StudyProgram
import edu.hsbo.hsbobackend.service.StudentService
import edu.hsbo.hsbobackend.service.StudyProgramService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class StudyProgramController(val studyProgramService: StudyProgramService) {

    /**
     * Ruft eine Liste aller verfügbaren Studiengänge ab.
     * HTTP-Methode: GET
     * Endpunkt: /api/studyPrograms
     * Berechtigung: Nur für Benutzer mit der Rolle 'STUDENT'.
     * @return Eine Liste aller StudyProgram-Objekte.
     */
    @GetMapping("/studyPrograms")
    @PreAuthorize("hasRole('STUDENT')")
    fun getStudyPrograms(): List<StudyProgram> {
        println(SecurityContext.getStudentId())
        return studyProgramService.getAllStudyPrograms()
    }

    /**
     * Ruft einen bestimmten Studiengang anhand seiner eindeutigen ID ab.
     * HTTP-Methode: GET
     * Endpunkt: /api/studyProgram/{id}
     * @param id Die ID des gesuchten Studiengangs, die als Pfadvariable übergeben wird.
     * @return Der gefundene Studiengang.
     */
    @GetMapping("/studyProgram/{id}")
    fun getStudyProgramById(@PathVariable id: String): StudyProgram {
        return studyProgramService.getStudyProgram(id)
    }

    /**
     * Sucht und retourniert einen Studiengang anhand seines Namens.
     * HTTP-Methode: GET
     * Endpunkt: /api/byName/{name}
     * @param name Der Name des gesuchten Studiengangs, der als Pfadvariable übergeben wird.
     * @return Der gefundene Studiengang.
     */
    @GetMapping("/byName/{name}")
    fun getStudyProgramByName(@PathVariable name: String): StudyProgram {
        return studyProgramService.getStudyProgramByName(name)
    }

    /**
     * Fügt einen neuen Studiengang zum System hinzu.
     * Die Daten des Studiengangs werden aus dem Request Body entnommen.
     * HTTP-Methode: POST
     * Endpunkt: /api/addStudyProgram
     * Berechtigung: Nur für Benutzer mit der Rolle 'STUDENT'.
     * @param studyProgram Das Studiengang-Objekt, das hinzugefügt werden soll.
     * @return Der neu erstellte Studiengang.
     */
    @PostMapping("/addStudyProgram")
    @PreAuthorize("hasRole('STUDENT')")
    fun addStudyProgram(@RequestBody studyProgram: StudyProgram): StudyProgram {
        return studyProgramService.addStudyProgram(studyProgram)
    }

}