package edu.hsbo.akafoeHousing.repository

import edu.hsbo.akafoeHousing.entities.HousingUnitOccupancy
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface HousingUnitOccupancyRepository: MongoRepository<HousingUnitOccupancy, String> {
    fun findByStudentId(id:String): Optional<HousingUnitOccupancy>
}