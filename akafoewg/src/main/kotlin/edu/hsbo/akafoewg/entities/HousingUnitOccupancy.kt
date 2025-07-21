package edu.hsbo.akafoewg.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import java.time.Instant

@Document(collection = "housingUnitOccupancy")
data class HousingUnitOccupancy(
    @Id
    val id: String? = null,
    val studentId: String,
    val startDate: Instant,
    val expiryDate: Instant? = null,

    @DocumentReference
    var housingUnit: HousingUnit? = null
)

