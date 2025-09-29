package edu.hsbo.hsbobackend.service

import edu.hsbo.hsbobackend.SecurityContext
import edu.hsbo.hsbobackend.entities.Student
import edu.hsbo.hsbobackend.repository.StudentRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class StudentService(val studentRepository: StudentRepository) {

    /**
     * Ruft eine Liste aller Studenten aus der Datenbank ab.
     * @return Eine Liste aller Studenten-Objekte.
     */
    fun getAllStudents(): List<Student> {
        return studentRepository.findAll()
    }

    /**
     * Fügt einen neuen Studenten hinzu oder aktualisiert einen bestehenden.
     * @param student Der zu speichernde Student.
     * @return Der gespeicherte Student.
     */
    fun addStudent(student: Student): Student {
        return studentRepository.save(student)
    }

    /**
     * Findet einen Studenten anhand seiner eindeutigen Studenten-ID.
     * @param id Die Studenten-ID.
     * @return Das gefundene Studenten-Objekt.
     * @throws IllegalArgumentException wenn kein Student mit der angegebenen ID gefunden wird.
     */
    fun findByStudentId(id: Int): Student {
        return studentRepository.findByStudentId(id).orElseThrow {
            throw IllegalArgumentException("Student with id $id not found")
        }
    }

    /**
     * Findet das Profil des aktuell authentifizierten Studenten.
     * Die ID des Studenten wird aus dem SecurityContext bezogen.
     * @return Ein Optional, das den Studenten enthält, falls er gefunden wird.
     */
    fun findMyProfile(): Optional<Student> {
        return studentRepository.findByStudentId(SecurityContext.getStudentId())
    }
}