package edu.hsbo.akafoeHousing.controller

import edu.hsbo.akafoeHousing.entities.HousingUnitOccupancy
import edu.hsbo.akafoeHousing.service.HousingUnitOccupancyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.Instant
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping("/api")
class HousingUnitOccupancyController(val housingUnitOccupancyService: HousingUnitOccupancyService) {

    /**
     * Endpoint to create a new HousingUnitActive entry.
     * Endpoint to create a new HousingUnitActive entry.
     * POST /api/HousingUnit-actives
     * @param uniId The university ID or similar identifier for the person occupying the space (from request parameter).
     * @param HousingUnitId The ID of the HousingUnit to associate with this active entry (from request parameter).
     * @param startDate The start date of the active entry (from request parameter).
     * @param expiryDate The optional expiry date of the active entry (from request parameter).
     *
     * Example POST URL with query parameters:
     * http://localhost:8080/api/HousingUnit-actives?uniId=student123&HousingUnitId=654321abcdef&startDate=2023-01-15T10:00:00.000Z
     *
     * @return ResponseEntity with the created HousingUnitActive and HTTP status 201 (Created),
     * or 400 (Bad Request) if input is invalid, or 404 (Not Found) if HousingUnit not found/no spaces.
     */
    @PostMapping("/housingUnitActivate/{uniId}/{housingUnitId}/{startDate}")
    fun createHousingUnitActive(
        @PathVariable uniId: String,
        @PathVariable housingUnitId: String,
        @PathVariable startDate: Instant,
        @RequestParam(required = false) expiryDate: Instant? // still optional as query parameter if desired
    ): ResponseEntity<HousingUnitOccupancy> {
        val createdHousingUnitActive = housingUnitOccupancyService.createHouseUnitOccupancy(
            uniId,
            housingUnitId,
            startDate,
            expiryDate
        )
        return if (createdHousingUnitActive != null) {
            ResponseEntity(createdHousingUnitActive, HttpStatus.CREATED)
        } else {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }


    /**
     * Endpoint to get all HousingUnitActive entries.
     * GET /api/HousingUnit-actives
     * @return ResponseEntity with a list of all HousingUnitActive entries and HTTP status 200 (OK).
     */
    @GetMapping("/housingUnitOccupancy")
    fun getAllHousingUnitActives(): ResponseEntity<List<HousingUnitOccupancy>> {
        val housingUnitActives = housingUnitOccupancyService.getAllHouseUnitOccupancies()
        return ResponseEntity(housingUnitActives, HttpStatus.OK)
    }

    /**
     * Endpoint to get a HousingUnitActive entry by its ID.
     * GET /api/HousingUnit-actives/{id}
     * @param id The ID of the HousingUnitActive to retrieve (from path variable).
     * @return ResponseEntity with the HousingUnitActive and HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if not found.
     */
    @GetMapping("/housingUnitOccupancy/{id}")
    fun getHousingUnitActiveById(@PathVariable id: String): ResponseEntity<HousingUnitOccupancy> {
        val housingUnitActive = housingUnitOccupancyService.getHouseUnitOccupancyById(id)
        return if (housingUnitActive.isPresent) {
            ResponseEntity(housingUnitActive.get(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    /**
     * Endpoint to update an existing HousingUnitActive entry.
     * PUT /api/HousingUnit-actives/{id}
     * @param id The ID of the HousingUnitActive to update (from path variable).
     * @param updatedHousingUnitOccupancy The HousingUnitActive object with updated data (from request body).
     * @return ResponseEntity with the updated HousingUnitActive and HTTP status 200 (OK) if successful,
     * or HTTP status 404 (Not Found) if the HousingUnitActive was not found.
     */
    @PutMapping("/housingUnitOccupancy/{id}")
    fun updateHousingUnitActive(
        @PathVariable id: String,
        @RequestBody updatedHousingUnitOccupancy: HousingUnitOccupancy
    ): ResponseEntity<HousingUnitOccupancy> {
        val result = housingUnitOccupancyService.updateHouseUnitOccupancy(id, updatedHousingUnitOccupancy)
        return if (result != null) {
            ResponseEntity(result, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    /**
     * Endpoint to delete a HousingUnitActive entry.
     * DELETE /api/HousingUnit-actives/{id}
     * @param id The ID of the HousingUnitActive to delete (from path variable).
     * @return ResponseEntity with HTTP status 204 (No Content) if deleted successfully,
     * or HTTP status 404 (Not Found) if the HousingUnitActive was not found.
     */
    @DeleteMapping("/housingUnitOccupancy/{id}")
    fun deleteHousingUnitActive(@PathVariable id: String): ResponseEntity<Void> {
        return if (housingUnitOccupancyService.deleteHouseUnitOccupancy(id)) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/housingUnitOccupancyByStudentId/{studentId}")
    fun getOccupancyByStudentId(@PathVariable studentId: String): ResponseEntity<HousingUnitOccupancy> {
        val optionalOccupancy = housingUnitOccupancyService.getHouseOccupancyByStudentId(studentId)

        return if (optionalOccupancy.isPresent) {
            // If an occupancy is found, return it with 200 OK
            ResponseEntity.ok(optionalOccupancy.get())
        } else {
            // If no occupancy is found, return 404 Not Found
            ResponseEntity.notFound().build()
            // Or you could return 204 No Content if you prefer for an empty response body
            // ResponseEntity.noContent().build()
        }
    }
}
