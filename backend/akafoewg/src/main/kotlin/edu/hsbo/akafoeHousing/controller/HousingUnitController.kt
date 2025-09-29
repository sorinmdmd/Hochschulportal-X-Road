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
     * Endpunkt zum Hinzufügen einer neuen Wohneinheit.
     * POST /api/housingUnit
     * @param housingUnit Das hinzuzufügende HousingUnit-Objekt (aus dem Request Body).
     * @return ResponseEntity mit der erstellten Wohneinheit und dem HTTP-Status 201 (Created).
     */
    @PostMapping("/housingUnit")
    fun addHousingUnit(@RequestBody housingUnit: HousingUnit): ResponseEntity<HousingUnit> {
        val createdHousingUnit = housingUnitService.addHousingUnit(housingUnit)
        return ResponseEntity(createdHousingUnit, HttpStatus.CREATED)
    }

    /**
     * Endpunkt zum Abrufen aller Wohneinheiten.
     * GET /api/housingUnits
     * @return ResponseEntity mit einer Liste aller Wohneinheiten und dem HTTP-Status 200 (OK).
     */
    @GetMapping("/housingUnits")
    fun getAllHousingUnits(): ResponseEntity<List<HousingUnit>> {
        val housingUnits = housingUnitService.getAllHousingUnits()
        return ResponseEntity(housingUnits, HttpStatus.OK)
    }

    /**
     * Endpunkt zum Abrufen einer Wohneinheit anhand ihrer ID.
     * GET /api/housingUnit/{id}
     * @param id Die ID der abzurufenden Wohneinheit (aus der Pfadvariable).
     * @return ResponseEntity mit der Wohneinheit und dem HTTP-Status 200 (OK), falls gefunden,
     * oder dem HTTP-Status 404 (Not Found), falls nicht gefunden.
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
     * Endpunkt zum Aktualisieren einer bestehenden Wohneinheit.
     * PUT /api/housingUnit/{id}
     * @param id Die ID der zu aktualisierenden Wohneinheit (aus der Pfadvariable).
     * @param updatedHousingUnit Das HousingUnit-Objekt mit den aktualisierten Daten (aus dem Request Body).
     * @return ResponseEntity mit der aktualisierten Wohneinheit und dem HTTP-Status 200 (OK), falls erfolgreich,
     * oder dem HTTP-Status 404 (Not Found), falls die Wohneinheit nicht gefunden wurde.
     */
    @PutMapping("/housingUnit/{id}")
    fun updateHousingUnit(
        @PathVariable id: String,
        @RequestBody updatedHousingUnit: HousingUnit
    ): ResponseEntity<HousingUnit> {
        val result = housingUnitService.updateHousingUnit(id, updatedHousingUnit)
        return if (result != null) {
            ResponseEntity(result, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    /**
     * Endpunkt zum Löschen einer Wohneinheit anhand ihrer ID.
     * DELETE /api/housingUnit/{id}
     * @param id Die ID der zu löschenden Wohneinheit (aus der Pfadvariable).
     * @return ResponseEntity mit dem HTTP-Status 204 (No Content), falls erfolgreich gelöscht,
     * oder dem HTTP-Status 404 (Not Found), falls die Wohneinheit nicht gefunden wurde.
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