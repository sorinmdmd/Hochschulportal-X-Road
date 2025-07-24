package edu.hsbo.hsbobackend.controller

import edu.hsbo.hsbobackend.SecurityContext
import edu.hsbo.hsbobackend.entities.StudyProgram
import edu.hsbo.hsbobackend.service.StudentService
import edu.hsbo.hsbobackend.service.StudyProgramService
import org.springframework.security.access.prepost.PreAuthorize
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
    @PreAuthorize("hasRole('STUDENT')")
    fun getStudyPrograms(): List<StudyProgram> {
        println(SecurityContext.getStudentId())
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
    @PreAuthorize("hasRole('STUDENT')")
    fun addStudyProgram(@RequestBody studyProgram: StudyProgram): StudyProgram {
        return studyProgramService.addStudyProgram(studyProgram)
    }

}