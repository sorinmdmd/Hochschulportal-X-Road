package edu.hsbo.hsbobackend.service

import edu.hsbo.hsbobackend.entities.Student
import edu.hsbo.hsbobackend.repository.StudentRepository
import org.springframework.stereotype.Service

@Service
class StudentService(val studentRepository: StudentRepository) {

    fun getAllStudents(): List<Student> {
        return studentRepository.findAll()
    }

    fun addStudent(student: Student): Student {
       return studentRepository.save(student)
    }
}