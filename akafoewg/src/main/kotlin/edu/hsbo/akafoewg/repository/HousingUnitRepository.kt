package edu.hsbo.akafoewg.repository

import edu.hsbo.akafoewg.entities.HousingUnit
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository


@Repository
interface HousingUnitRepository: MongoRepository<HousingUnit, String>
