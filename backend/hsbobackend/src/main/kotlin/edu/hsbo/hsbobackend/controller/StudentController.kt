package edu.hsbo.hsbobackend.controller

import edu.hsbo.hsbobackend.entities.Student
import edu.hsbo.hsbobackend.service.StudentService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Optional

@RestController
@RequestMapping("/api")
class StudentController(val studentService: StudentService) {

    /**
     * Holt eine Liste aller Studenten.
     * HTTP-Methode: GET
     * Endpunkt: /api/students
     * Berechtigung: Nur für Benutzer mit der Rolle 'ADMIN'.
     * @return Eine Liste aller Studenten-Objekte.
     */
    @GetMapping("/students")
    @PreAuthorize("hasRole('ADMIN')")
    fun getStudents(): List<Student> {
        return studentService.getAllStudents()
    }

    /**
     * Fügt einen neuen Studenten hinzu. Die Studentendaten werden aus dem Request Body entnommen.
     * HTTP-Methode: POST
     * Endpunkt: /api/student
     * Berechtigung: Nur für Benutzer mit der Rolle 'STUDENT'.
     * @param student Das Studenten-Objekt, das hinzugefügt werden soll.
     * @return Der neu erstellte Student.
     */
    @PostMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    fun addStudent(@RequestBody student: Student): Student {
        return studentService.addStudent(student)
    }

    /**
     * Ruft einen bestimmten Studenten anhand seiner eindeutigen ID ab.
     * HTTP-Methode: GET
     * Endpunkt: /api/student/{id}
     * @param id Die ID des gesuchten Studenten, die als Pfadvariable übergeben wird.
     * @return Der gefundene Student.
     */
    @GetMapping("/student/{id}")
    fun getStudent(@PathVariable id: Int): Student {
        return studentService.findByStudentId(id)
    }

    /**
     * Ruft die persönlichen Profilinformationen des aktuell authentifizierten Studenten ab.
     * Ermöglicht es einem Studenten, seine eigenen Daten einzusehen.
     * HTTP-Methode: GET
     * Endpunkt: /api/personalStudentInfo
     * Berechtigung: Nur für Benutzer mit der Rolle 'STUDENT'.
     * @return Ein Optional, das den Studenten enthält, wenn er gefunden wird.
     */
    @GetMapping("/personalStudentInfo")
    @PreAuthorize("hasRole('STUDENT')")
    fun getStudentPersonalInfo(): Optional<Student> {
        return studentService.findMyProfile()
    }
}