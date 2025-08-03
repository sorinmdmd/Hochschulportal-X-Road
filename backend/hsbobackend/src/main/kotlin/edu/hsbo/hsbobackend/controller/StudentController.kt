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

    @GetMapping("/students")
    @PreAuthorize("hasRole('ADMIN')")
    fun getStudents(): List<Student> {
        return studentService.getAllStudents()
    }

    @PostMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    fun addStudent(@RequestBody student: Student): Student {
       return studentService.addStudent(student)
    }

    @GetMapping("/student/{id}")
    fun getStudent(@PathVariable id: Int): Student {
        return studentService.findByStudentId(id)
    }

    @GetMapping("/personalStudentInfo")
    @PreAuthorize("hasRole('STUDENT')")
    fun getStudentPersonalInfo(): Optional<Student> {
        return studentService.findMyProfile()
    }
}
