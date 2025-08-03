package edu.hsbo.hsbobackend.service

import edu.hsbo.hsbobackend.SecurityContext
import edu.hsbo.hsbobackend.entities.Student
import edu.hsbo.hsbobackend.repository.StudentRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class StudentService(val studentRepository: StudentRepository) {

    fun getAllStudents(): List<Student> {
        return studentRepository.findAll()
    }

    fun addStudent(student: Student): Student {
        return studentRepository.save(student)
    }

    fun findByStudentId(id: Int): Student {
        return studentRepository.findByStudentId(id).orElseThrow {
            throw IllegalArgumentException("Student with id $id not found")
        }
    }

    fun findMyProfile():Optional<Student>{
        return studentRepository.findByStudentId(SecurityContext.getStudentId())
    }
}