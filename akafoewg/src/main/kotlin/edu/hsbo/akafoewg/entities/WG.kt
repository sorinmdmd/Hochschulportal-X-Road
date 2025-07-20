package edu.hsbo.akafoewg.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "wg")
data class WG(
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