package edu.hsbo.zfarub.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference

@Document(collection = "languageCourseBooking")
class LanguageCourseBooking(
    @Id var id: String,
    var studentId: String?,

    @DocumentReference
    val course: LanguageCourse,
) {

}