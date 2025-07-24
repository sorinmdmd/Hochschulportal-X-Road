package edu.hsbo.akafoeHousing.service

import edu.hsbo.akafoeHousing.repository.WaitlistRepository
import org.springframework.stereotype.Service

@Service
class WaitlistService (val waitlistRepository: WaitlistRepository) {
}