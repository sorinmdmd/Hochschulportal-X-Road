package edu.hsbo.akafoewg.controller

import edu.hsbo.akafoewg.service.WaitlistService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class WaitlistController(val waitlistService: WaitlistService) {

}