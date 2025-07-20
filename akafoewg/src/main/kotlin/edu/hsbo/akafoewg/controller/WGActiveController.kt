package edu.hsbo.akafoewg.controller

import edu.hsbo.akafoewg.entities.WGActive
import edu.hsbo.akafoewg.repository.WGActiveRepository
import edu.hsbo.akafoewg.service.WGActiveService
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
import java.time.Instant
import java.util.Date

@RestController
@RequestMapping("/api")
class WGActiveController(val wgActiveService: WGActiveService) {

    /**
     * Endpoint to create a new WGActive entry.
     * POST /api/wg-actives
     * @param uniId The university ID or similar identifier for the person occupying the space (from request parameter).
     * @param wgId The ID of the WG to associate with this active entry (from request parameter).
     * @param startDate The start date of the active entry (from request parameter).
     * @param expiryDate The optional expiry date of the active entry (from request parameter).
     *
     * Example POST URL with query parameters:
     * http://localhost:8080/api/wg-actives?uniId=student123&wgId=654321abcdef&startDate=2023-01-15T10:00:00.000Z
     *
     * @return ResponseEntity with the created WGActive and HTTP status 201 (Created),
     * or 400 (Bad Request) if input is invalid, or 404 (Not Found) if WG not found/no spaces.
     */
    @PostMapping("/wgactive")
    fun createWGActive(
        @RequestParam uniId: String,
        @RequestParam wgId: String,
        @RequestParam startDate: Instant,
        @RequestParam(required = false) expiryDate: Instant? // expiryDate is optional
    ): ResponseEntity<WGActive> {
        // Service will handle detailed validation and business logic
        val createdWGActive = wgActiveService.createWGActive(uniId, wgId, startDate, expiryDate)
        return if (createdWGActive != null) {
            ResponseEntity(createdWGActive, HttpStatus.CREATED)
        } else {
            // Service returns null if WG not found or no free spaces, or validation fails
            ResponseEntity(HttpStatus.BAD_REQUEST) // Use BAD_REQUEST for general creation issues
        }
    }

    /**
     * Endpoint to get all WGActive entries.
     * GET /api/wg-actives
     * @return ResponseEntity with a list of all WGActive entries and HTTP status 200 (OK).
     */
    @GetMapping("/wgactive")
    fun getAllWGActives(): ResponseEntity<List<WGActive>> {
        val wgActives = wgActiveService.getAllWGActives()
        return ResponseEntity(wgActives, HttpStatus.OK)
    }

    /**
     * Endpoint to get a WGActive entry by its ID.
     * GET /api/wg-actives/{id}
     * @param id The ID of the WGActive to retrieve (from path variable).
     * @return ResponseEntity with the WGActive and HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if not found.
     */
    @GetMapping("wgactive/{id}")
    fun getWGActiveById(@PathVariable id: String): ResponseEntity<WGActive> {
        val wgActive = wgActiveService.getWGActiveById(id)
        return if (wgActive.isPresent) {
            ResponseEntity(wgActive.get(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    /**
     * Endpoint to update an existing WGActive entry.
     * PUT /api/wg-actives/{id}
     * @param id The ID of the WGActive to update (from path variable).
     * @param updatedWGActive The WGActive object with updated data (from request body).
     * @return ResponseEntity with the updated WGActive and HTTP status 200 (OK) if successful,
     * or HTTP status 404 (Not Found) if the WGActive was not found.
     */
    @PutMapping("wgactive/{id}")
    fun updateWGActive(@PathVariable id: String, @RequestBody updatedWGActive: WGActive): ResponseEntity<WGActive> {
        val result = wgActiveService.updateWGActive(id, updatedWGActive)
        return if (result != null) {
            ResponseEntity(result, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    /**
     * Endpoint to delete a WGActive entry.
     * DELETE /api/wg-actives/{id}
     * @param id The ID of the WGActive to delete (from path variable).
     * @return ResponseEntity with HTTP status 204 (No Content) if deleted successfully,
     * or HTTP status 404 (Not Found) if the WGActive was not found.
     */
    @DeleteMapping("wgactive/{id}")
    fun deleteWGActive(@PathVariable id: String): ResponseEntity<Void> {
        return if (wgActiveService.deleteWGActive(id)) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}