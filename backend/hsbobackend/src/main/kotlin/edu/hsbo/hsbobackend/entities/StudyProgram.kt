package edu.hsbo.hsbobackend.entities

import edu.hsbo.hsbobackend.entities.enums.Campus
import edu.hsbo.hsbobackend.entities.enums.DegreeLevel
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "study_programs")
class StudyProgram(
    @Id
    var id: String? = null,
    var name: String? = null,
    var degreeLevel: DegreeLevel? = null,
    var numberOfSemesters: Int? = null,
    var facultyName: String? = null,
    var campusName: Campus? = null,
)
