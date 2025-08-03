package edu.hsbo.hsbobackend.repository

import edu.hsbo.hsbobackend.entities.Student
import edu.hsbo.hsbobackend.entities.StudyProgram
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface StudyProgramRepository : MongoRepository<StudyProgram, String>{
    fun findByName(name : String) : StudyProgram
}