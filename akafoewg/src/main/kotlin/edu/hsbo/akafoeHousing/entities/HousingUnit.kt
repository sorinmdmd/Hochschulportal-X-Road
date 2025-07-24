package edu.hsbo.akafoeHousing.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "housingUnit")
data class HousingUnit(
    @Id
    val id: String? = null,
    val name: String,
    val description: String,
    val category: String,
    val city: String,
    val zipCode: String,
    val address: String,
    val freeSpaces: Int,
)