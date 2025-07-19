package edu.hsbo.hsbobackend.repository

import edu.hsbo.hsbobackend.entities.Student
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface StudentRepository : MongoRepository<Student, String> {
    fun findByFirstName(firstName: String): List<Student>
    fun findByUniId(id:Int): Optional<Student>
}