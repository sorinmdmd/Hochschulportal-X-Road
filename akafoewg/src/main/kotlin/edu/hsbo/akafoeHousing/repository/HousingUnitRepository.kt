package edu.hsbo.akafoeHousing.repository

import edu.hsbo.akafoeHousing.entities.HousingUnit
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository


@Repository
interface HousingUnitRepository: MongoRepository<HousingUnit, String>
