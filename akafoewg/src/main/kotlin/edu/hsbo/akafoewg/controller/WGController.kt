package edu.hsbo.akafoewg.controller

import edu.hsbo.akafoewg.entities.WG
import edu.hsbo.akafoewg.service.WGService
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
class WGController(val wgService: WGService) {

    /**
     * Endpoint to add a new WG.
     * POST /api/wgs
     * @param wg The WG object to be added (from request body).
     * @return ResponseEntity with the created WG and HTTP status 201 (Created).
     */
    @PostMapping("/wg")
    fun addWG(@RequestBody wg: WG): ResponseEntity<WG> {
        val createdWG = wgService.addWG(wg)
        return ResponseEntity(createdWG, HttpStatus.CREATED)
    }

    /**
     * Endpoint to get all WGs.
     * GET /api/wgs
     * @return ResponseEntity with a list of all WGs and HTTP status 200 (OK).
     */
    @GetMapping("/wgs")
    fun getAllWGs(): ResponseEntity<List<WG>> {
        val wgs = wgService.getAllWGs()
        return ResponseEntity(wgs, HttpStatus.OK)
    }

    /**
     * Endpoint to get a WG by its ID.
     * GET /api/wgs/{id}
     * @param id The ID of the WG to retrieve (from path variable).
     * @return ResponseEntity with the WG and HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if not found.
     */
    @GetMapping("wg/{id}")
    fun getWGById(@PathVariable id: String): ResponseEntity<WG> {
        val wg = wgService.getWGById(id)
        return if (wg.isPresent) {
            ResponseEntity(wg.get(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    /**
     * Endpoint to update an existing WG.
     * PUT /api/wgs/{id}
     * @param id The ID of the WG to update (from path variable).
     * @param updatedWG The WG object with updated data (from request body).
     * @return ResponseEntity with the updated WG and HTTP status 200 (OK) if successful,
     * or HTTP status 404 (Not Found) if the WG was not found.
     */
    @PutMapping("wg/{id}")
    fun updateWG(@PathVariable id: String, @RequestBody updatedWG: WG): ResponseEntity<WG> {
        val result = wgService.updateWG(id, updatedWG)
        return if (result != null) {
            ResponseEntity(result, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    /**
     * Endpoint to delete a WG by its ID.
     * DELETE /api/wgs/{id}
     * @param id The ID of the WG to delete (from path variable).
     * @return ResponseEntity with HTTP status 204 (No Content) if deleted successfully,
     * or HTTP status 404 (Not Found) if the WG was not found.
     */
    @DeleteMapping("wg/{id}")
    fun deleteWG(@PathVariable id: String): ResponseEntity<Void> {
        return if (wgService.deleteWG(id)) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}