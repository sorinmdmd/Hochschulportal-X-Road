package edu.hsbo.hsbobackend.controller

import edu.hsbo.hsbobackend.entities.StudyProgram
import edu.hsbo.hsbobackend.service.StudentService
import edu.hsbo.hsbobackend.service.StudyProgramService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class StudyProgramController(val studyProgramService: StudyProgramService) {

    @GetMapping("/studyPrograms")
    fun getStudyPrograms(): List<StudyProgram> {
        return studyProgramService.getAllStudyPrograms()
    }

    @GetMapping("/studyProgram/{id}")
    fun getStudyProgramById(@PathVariable id: String): StudyProgram {
        return studyProgramService.getStudyProgram(id)
    }

    @GetMapping("/byName/{name}")
    fun getStudyProgramByName(@PathVariable name: String): StudyProgram {
        return studyProgramService.getStudyProgramByName(name)
    }

    @PostMapping("/addStudyProgram")
    fun addStudyProgram(@RequestBody studyProgram: StudyProgram): StudyProgram {
        return studyProgramService.addStudyProgram(studyProgram)
    }

}