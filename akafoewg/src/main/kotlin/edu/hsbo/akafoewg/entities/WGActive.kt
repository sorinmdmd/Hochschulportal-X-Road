package edu.hsbo.akafoewg.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import java.time.Instant
import java.util.Date

@Document(collection = "wgActive")
data class WGActive(
    @Id
    val id: String? = null,
    val uniId: String,
    val startDate: Instant,
    val expiryDate: Instant? = null,

    @DocumentReference
    var wg: WG? = null
)

