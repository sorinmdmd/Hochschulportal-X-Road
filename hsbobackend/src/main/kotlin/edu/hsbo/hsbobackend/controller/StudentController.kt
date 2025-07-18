package edu.hsbo.hsbobackend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class StudentController {

    @GetMapping("/students")
    fun getStudents(): String {
        return "Hello World!"
    }
}