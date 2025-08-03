package edu.hsbo.akafoeHousing.controller

import edu.hsbo.akafoeHousing.entities.HousingUnit
import edu.hsbo.akafoeHousing.service.HousingUnitService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class HousingUnitController(val housingUnitService: HousingUnitService) {

    /**
     * Endpoint to add a new HousingUnit.
     * POST /api/HousingUnits
     * @param HousingUnit The HousingUnit object to be added (from request body).
     * @return ResponseEntity with the created HousingUnit and HTTP status 201 (Created).
     */
    @PostMapping("/housingUnit")
    fun addHousingUnit(@RequestBody housingUnit: HousingUnit): ResponseEntity<HousingUnit> {
        val createdHousingUnit = housingUnitService.addHousingUnit(housingUnit)
        return ResponseEntity(createdHousingUnit, HttpStatus.CREATED)
    }

    /**
     * Endpoint to get all HousingUnits.
     * GET /api/HousingUnits
     * @return ResponseEntity with a list of all HousingUnits and HTTP status 200 (OK).
     */
    @GetMapping("/housingUnits")
    fun getAllHousingUnits(): ResponseEntity<List<HousingUnit>> {
        val housingUnits = housingUnitService.getAllHousingUnits()
        return ResponseEntity(housingUnits, HttpStatus.OK)
    }

    /**
     * Endpoint to get a HousingUnit by its ID.
     * GET /api/HousingUnits/{id}
     * @param id The ID of the HousingUnit to retrieve (from path variable).
     * @return ResponseEntity with the HousingUnit and HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if not found.
     */
    @GetMapping("/housingUnit/{id}")
    fun getHousingUnitById(@PathVariable id: String): ResponseEntity<HousingUnit> {
        val housingUnit = housingUnitService.getHousingUnitById(id)
        return if (housingUnit.isPresent) {
            ResponseEntity(housingUnit.get(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    /**
     * Endpoint to update an existing HousingUnit.
     * PUT /api/HousingUnits/{id}
     * @param id The ID of the HousingUnit to update (from path variable).
     * @param updatedHousingUnit The HousingUnit object with updated data (from request body).
     * @return ResponseEntity with the updated HousingUnit and HTTP status 200 (OK) if successful,
     * or HTTP status 404 (Not Found) if the HousingUnit was not found.
     */
    @PutMapping("/housingUnit/{id}")
    fun updateHousingUnit(@PathVariable id: String, @RequestBody updatedHousingUnit: HousingUnit): ResponseEntity<HousingUnit> {
        val result = housingUnitService.updateHousingUnit(id, updatedHousingUnit)
        return if (result != null) {
            ResponseEntity(result, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    /**
     * Endpoint to delete a HousingUnit by its ID.
     * DELETE /api/HousingUnits/{id}
     * @param id The ID of the HousingUnit to delete (from path variable).
     * @return ResponseEntity with HTTP status 204 (No Content) if deleted successfully,
     * or HTTP status 404 (Not Found) if the HousingUnit was not found.
     */
    @DeleteMapping("/housingUnit/{id}")
    fun deleteHousingUnit(@PathVariable id: String): ResponseEntity<Void> {
        return if (housingUnitService.deleteHousingUnit(id)) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}