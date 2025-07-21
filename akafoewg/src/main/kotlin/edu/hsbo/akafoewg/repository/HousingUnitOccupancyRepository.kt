package edu.hsbo.akafoewg.repository

import edu.hsbo.akafoewg.entities.HousingUnitOccupancy
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface HousingUnitOccupancyRepository: MongoRepository<HousingUnitOccupancy, String> {
}