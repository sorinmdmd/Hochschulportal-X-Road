package edu.hsbo.akafoewg.repository

import edu.hsbo.akafoewg.entities.Waitlist
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface  WaitlistRepository : MongoRepository<Waitlist, String> {

}
