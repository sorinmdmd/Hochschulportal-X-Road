package edu.hsbo.hsbobackend.service

import edu.hsbo.hsbobackend.entities.StudyProgram
import edu.hsbo.hsbobackend.repository.StudyProgramRepository
import org.springframework.stereotype.Service
import java.nio.file.NoSuchFileException

@Service
class StudyProgramService(val studyProgramRepository: StudyProgramRepository) {

    fun getAllStudyPrograms(): List<StudyProgram> {
        return studyProgramRepository.findAll()
    }

    fun getStudyProgram(id: String): StudyProgram {
        return studyProgramRepository.findById(id).orElseThrow {
            NoSuchFileException("StudyProgram $id not found")
        }
    }

    fun getStudyProgramByName(name: String): StudyProgram {
        return studyProgramRepository.findByName(name)
    }

    fun addStudyProgram(studyProgram: StudyProgram): StudyProgram {
        return studyProgramRepository.save(studyProgram)
    }

}