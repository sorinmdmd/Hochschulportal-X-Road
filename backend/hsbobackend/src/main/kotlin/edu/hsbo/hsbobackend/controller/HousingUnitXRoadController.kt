package edu.hsbo.hsbobackend.controller

import edu.hsbo.hsbobackend.service.HousingUnitXRoadService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/api")
class HousingUnitXRoadController(val xRoadHousingUnitService: HousingUnitXRoadService) {

    @GetMapping("/x-road/housingUnits")
    @PreAuthorize("hasRole('STUDENT')")
    fun getAvailableHousingUnits(): ResponseEntity<String> {
        return xRoadHousingUnitService.getHousingUnitsList()
    }

    @PostMapping("/x-road/housingUnit/{housingUnitID}/{startDate}")
    @PreAuthorize("hasRole('STUDENT')")
    fun bookHousingUnit(
        @PathVariable housingUnitID: String,
        @PathVariable startDate: Instant
    ): ResponseEntity<String> {
        return xRoadHousingUnitService.bookHousingUnit(housingUnitID, startDate)
    }

    @GetMapping("/x-road/housingUnits/getLogs")
    @PreAuthorize("hasRole('STUDENT')")
    fun getHousingUnitsLogs(): ResponseEntity<String> {
        return xRoadHousingUnitService.getStudentLogs()
    }

    @GetMapping("/x-road/housingUnit/getMyBooking")
    @PreAuthorize("hasRole('STUDENT')")
    fun getBooking(): ResponseEntity<String> {
        return xRoadHousingUnitService.getMyBooking()
    }
}