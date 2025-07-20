package edu.hsbo.akafoewg.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import java.util.Date

@Document(collection = "waitlist")
data class Waitlist (
    @Id
    val id: String,
    val registrationDate: Date,

    @DocumentReference
    var wg: WG? = null
)