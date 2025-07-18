package edu.hsbo.hsbobackend.repository

import edu.hsbo.hsbobackend.entities.Student
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : MongoRepository<Student, String> {
    fun findByFirstName(firstName: String): List<Student>
}