package edu.hsbo.zfarub.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "languageCourses")
data class LanguageCourse(
    @Id val id: String,
    val language: String,
    val duration: String,
    val start: Instant,
    val end: Instant,
    val availableSlots: Int,
    val group: String,
    val location: String,
    val tutorName: String,
)
