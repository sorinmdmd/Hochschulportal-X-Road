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
     * Endpunkt zum Erstellen eines neuen Belegungseintrags für eine Wohneinheit.
     * POST /api/housingUnitActivate/{uniId}/{housingUnitId}/{startDate}
     * @param uniId Die ID des Studenten (aus der Pfadvariable).
     * @param housingUnitId Die ID der Wohneinheit (aus der Pfadvariable).
     * @param startDate Das Startdatum der Belegung (aus der Pfadvariable).
     * @param expiryDate Das optionale Enddatum der Belegung (aus dem Query-Parameter).
     * @return ResponseEntity mit dem erstellten Belegungseintrag und dem HTTP-Status 201 (Created),
     * oder 400 (Bad Request), falls die Eingabe ungültig ist.
     */
    @PostMapping("/housingUnitActivate/{uniId}/{housingUnitId}/{startDate}")
    fun createHousingUnitActive(
        @PathVariable uniId: String,
        @PathVariable housingUnitId: String,
        @PathVariable startDate: Instant,
        @RequestParam(required = false) expiryDate: Instant?
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
     * Endpunkt zum Abrufen aller Belegungseinträge.
     * GET /api/housingUnitOccupancy
     * @return ResponseEntity mit einer Liste aller Belegungseinträge und dem HTTP-Status 200 (OK).
     */
    @GetMapping("/housingUnitOccupancy")
    fun getAllHousingUnitActives(): ResponseEntity<List<HousingUnitOccupancy>> {
        val housingUnitActives = housingUnitOccupancyService.getAllHouseUnitOccupancies()
        return ResponseEntity(housingUnitActives, HttpStatus.OK)
    }

    /**
     * Endpunkt zum Abrufen eines Belegungseintrags anhand seiner ID.
     * GET /api/housingUnitOccupancy/{id}
     * @param id Die ID des abzurufenden Eintrags (aus der Pfadvariable).
     * @return ResponseEntity mit dem gefundenen Eintrag und dem HTTP-Status 200 (OK),
     * oder dem HTTP-Status 404 (Not Found), falls nicht gefunden.
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
     * Endpunkt zum Aktualisieren eines bestehenden Belegungseintrags.
     * PUT /api/housingUnitOccupancy/{id}
     * @param id Die ID des zu aktualisierenden Eintrags (aus der Pfadvariable).
     * @param updatedHousingUnitOccupancy Das Objekt mit den aktualisierten Daten (aus dem Request Body).
     * @return ResponseEntity mit dem aktualisierten Eintrag und dem HTTP-Status 200 (OK),
     * oder dem HTTP-Status 404 (Not Found), falls der Eintrag nicht gefunden wurde.
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
     * Endpunkt zum Löschen eines Belegungseintrags anhand seiner ID.
     * DELETE /api/housingUnitOccupancy/{id}
     * @param id Die ID des zu löschenden Eintrags (aus der Pfadvariable).
     * @return ResponseEntity mit dem HTTP-Status 204 (No Content), falls erfolgreich gelöscht,
     * oder dem HTTP-Status 404 (Not Found), falls der Eintrag nicht gefunden wurde.
     */
    @DeleteMapping("/housingUnitOccupancy/{studentId}")
    fun deleteHousingUnitActive(@PathVariable studentId: String): ResponseEntity<Void> {
        return if (housingUnitOccupancyService.deleteHouseUnitOccupancy(studentId)) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    /**
     * Endpunkt zum Abrufen eines Belegungseintrags anhand der Studenten-ID.
     * GET /api/housingUnitOccupancyByStudentId/{studentId}
     * @param studentId Die ID des Studenten (aus der Pfadvariable).
     * @return ResponseEntity mit dem gefundenen Belegungseintrag und HTTP-Status 200 (OK),
     * oder 404 (Not Found), wenn kein Eintrag für diesen Studenten existiert.
     */
    @GetMapping("/housingUnitOccupancyByStudentId/{studentId}")
    fun getOccupancyByStudentId(@PathVariable studentId: String): ResponseEntity<HousingUnitOccupancy> {
        val optionalOccupancy = housingUnitOccupancyService.getHouseOccupancyByStudentId(studentId)

        return if (optionalOccupancy.isPresent) {
            ResponseEntity.ok(optionalOccupancy.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }
}