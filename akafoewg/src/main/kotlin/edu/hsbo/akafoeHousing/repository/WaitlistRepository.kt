package edu.hsbo.akafoeHousing.repository

import edu.hsbo.akafoeHousing.entities.Waitlist
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface  WaitlistRepository : MongoRepository<Waitlist, String> {

}
