package edu.hsbo.hsbobackend.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document(collection = "students")
class Student(
    @Id
    var id: String? = null,
    var firstName: String,
    var lastName: String,
    var nationality: String,
    var dateOfBirth: Date,
)