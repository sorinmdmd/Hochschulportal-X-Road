package edu.hsbo.hsbobackend.controller

import edu.hsbo.hsbobackend.service.HousingUnitXRoadService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/api")
class HousingUnitXRoadController(val xRoadHousingUnitService: HousingUnitXRoadService) {

    /**
     * Ruft eine Liste der verfügbaren Wohneinheiten über den X-Road-Dienst ab.
     * HTTP-Methode: GET
     * Endpunkt: /api/x-road/housingUnits
     * Berechtigung: Nur für Benutzer mit der Rolle 'STUDENT'.
     * @return Eine ResponseEntity, die die Antwort vom externen Dienst als String enthält.
     */
    @GetMapping("/x-road/housingUnits")
    @PreAuthorize("hasRole('STUDENT')")
    fun getAvailableHousingUnits(): ResponseEntity<String> {
        return xRoadHousingUnitService.getHousingUnitsList()
    }

    /**
     * Bucht eine Wohneinheit für den authentifizierten Studenten über den X-Road-Dienst.
     * HTTP-Methode: POST
     * Endpunkt: /api/x-road/housingUnit/{housingUnitID}/{startDate}
     * Berechtigung: Nur für Benutzer mit der Rolle 'STUDENT'.
     * @param housingUnitID Die ID der zu buchenden Wohneinheit.
     * @param startDate Das Startdatum für die Buchung.
     * @return Eine ResponseEntity, die die Antwort (z.B. Bestätigung) vom externen Dienst als String enthält.
     */
    @PostMapping("/x-road/housingUnit/{housingUnitID}/{startDate}")
    @PreAuthorize("hasRole('STUDENT')")
    fun bookHousingUnit(
        @PathVariable housingUnitID: String,
        @PathVariable startDate: Instant
    ): ResponseEntity<String> {
        return xRoadHousingUnitService.bookHousingUnit(housingUnitID, startDate)
    }

    /**
     * Ruft die Protokolldaten (Logs) des authentifizierten Studenten im Zusammenhang mit Wohneinheiten vom X-Road-Dienst ab.
     * HTTP-Methode: GET
     * Endpunkt: /api/x-road/housingUnits/getLogs
     * Berechtigung: Nur für Benutzer mit der Rolle 'STUDENT'.
     * @return Eine ResponseEntity, die die Log-Informationen als String enthält.
     */
    @GetMapping("/x-road/housingUnits/getLogs")
    @PreAuthorize("hasRole('STUDENT')")
    fun getHousingUnitsLogs(): ResponseEntity<String> {
        return xRoadHousingUnitService.getStudentLogs()
    }

    /**
     * Ruft die aktuellen Buchungsdetails des authentifizierten Studenten vom X-Road-Dienst ab.
     * HTTP-Methode: GET
     * Endpunkt: /api/x-road/housingUnit/getMyBooking
     * Berechtigung: Nur für Benutzer mit der Rolle 'STUDENT'.
     * @return Eine ResponseEntity, die die Buchungsdetails als String enthält.
     */
    @GetMapping("/x-road/housingUnit/getMyBooking")
    @PreAuthorize("hasRole('STUDENT')")
    fun getBooking(): ResponseEntity<String> {
        return xRoadHousingUnitService.getMyBooking()
    }
    /**
     * Löscht die bestehende Wohnungsbuchung des authentifizierten Studenten über den X-Road-Dienst.
     * HTTP-Methode: DELETE
     * Endpunkt: /x-road/housingUnit/
     * Berechtigung: Nur für Benutzer mit der Rolle 'STUDENT'.
     * @return Eine ResponseEntity, die eine Bestätigungsnachricht über den Erfolg der Operation als String enthält.
     */
    @DeleteMapping("/x-road/housingUnit/")
    @PreAuthorize("hasRole('STUDENT')")
    fun delBooking(): ResponseEntity<String> {
        return xRoadHousingUnitService.deleteBooking()
    }
}
