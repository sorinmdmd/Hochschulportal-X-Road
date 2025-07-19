package edu.hsbo.hsbobackend.controller

import edu.hsbo.hsbobackend.entities.Student
import edu.hsbo.hsbobackend.service.StudentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.w3c.dom.stylesheets.LinkStyle

@RestController
@RequestMapping("/api")
class StudentController(val studentService: StudentService) {

    @GetMapping("/students")
    fun getStudents(): List<Student> {
        return studentService.getAllStudents()
    }

    @PostMapping("/students")
    fun addStudent(@RequestBody student: Student): Student {
       return studentService.addStudent(student)
    }
}
