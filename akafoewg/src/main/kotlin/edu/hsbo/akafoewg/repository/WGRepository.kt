package edu.hsbo.akafoewg.repository

import edu.hsbo.akafoewg.entities.WG
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository


@Repository
interface WGRepository: MongoRepository<WG, String> {
    fun findById(id: String?)
}