package edu.hsbo.akafoewg.repository

import edu.hsbo.akafoewg.entities.WG
import edu.hsbo.akafoewg.entities.WGActive
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface WGActiveRepository: MongoRepository<WGActive, String> {
}