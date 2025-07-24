package edu.hsbo.akafoeHousing.controller

import edu.hsbo.akafoeHousing.service.WaitlistService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class WaitlistController(val waitlistService: WaitlistService) {

}