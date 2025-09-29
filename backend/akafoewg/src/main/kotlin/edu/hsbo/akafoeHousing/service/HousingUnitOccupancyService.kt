package edu.hsbo.akafoeHousing.service

import edu.hsbo.akafoeHousing.entities.HousingUnitOccupancy
import edu.hsbo.akafoeHousing.repository.HousingUnitOccupancyRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.Optional

@Service
class HousingUnitOccupancyService(
    val housingUnitOccupancyRepository: HousingUnitOccupancyRepository,
    val housingUnitService: HousingUnitService
) {
    /**
     * Erstellt einen neuen Belegungseintrag (HouseUnitOccupancy).
     * Verringert die Anzahl der freien Plätze ('freeSpaces') im zugehörigen Wohneinheit-Dokument.
     *
     * @param studentId Die ID des Studenten, der den Platz belegt.
     * @param housingUnitId Die ID der Wohneinheit, die mit diesem Eintrag verknüpft ist.
     * @param startDate Das Startdatum des Belegungseintrags.
     * @param expiryDate Das optionale Ablaufdatum des Eintrags.
     * @return Das erstellte HouseUnitOccupancy-Objekt, oder null, falls die zugehörige Wohneinheit nicht gefunden wurde oder keine freien Plätze hat.
     */
    fun createHouseUnitOccupancy(
        studentId: String,
        housingUnitId: String,
        startDate: Instant,
        expiryDate: Instant? = null
    ): HousingUnitOccupancy? {
        // Grundlegende Validierung der erforderlichen Felder
        if (studentId.isBlank()) {
            println("Fehler: studentId wird für die Erstellung von HouseUnitOccupancy benötigt.")
            return null
        }
        if (housingUnitId.isBlank()) {
            println("Fehler: Wohneinheit-ID wird für die Erstellung von HouseUnitOccupancy benötigt.")
            return null
        }
        if (housingUnitOccupancyRepository.findByStudentId(studentId).isPresent) {
            println("Fehler: Eine HousingUnitOccupancy existiert bereits für die Studenten-ID '$studentId'.")
            return null
        }

        // Das zugehörige Wohneinheit-Dokument abrufen
        val housingUnitOptional = housingUnitService.getHousingUnitById(housingUnitId)
        if (housingUnitOptional.isPresent) {
            val housingUnit = housingUnitOptional.get()

            // Überprüfen, ob freie Plätze vorhanden sind
            if (housingUnit.freeSpaces > 0) {
                // 'freeSpaces' in der Wohneinheit dekrementieren
                val updatedHousingUnit = housingUnit.copy(freeSpaces = housingUnit.freeSpaces - 1)
                housingUnit.id?.let {
                    housingUnitService.updateHousingUnit(
                        it,
                        updatedHousingUnit
                    )
                } // Das Wohneinheit-Dokument aktualisieren

                // Das HouseUnitOccupancy-Objekt mit den übergebenen Parametern und der aktualisierten Referenz erstellen
                val newHousingUnitOccupancy = HousingUnitOccupancy(
                    studentId = studentId,
                    startDate = startDate,
                    expiryDate = expiryDate,
                    housingUnit = updatedHousingUnit // Das aktualisierte Wohneinheit-Objekt zuweisen
                )
                return housingUnitOccupancyRepository.save(newHousingUnitOccupancy)
            } else {
                println("Fehler: Wohneinheit '${housingUnit.name}' (ID: $housingUnitId) hat keine freien Plätze.")
                return null
            }
        } else {
            println("Fehler: Zugehörige Wohneinheit mit ID '$housingUnitId' nicht gefunden.")
            return null
        }
    }

    /**
     * Ruft alle Belegungseinträge (HouseUnitOccupancy) ab.
     * @return Eine Liste aller HouseUnitOccupancy-Objekte.
     */
    fun getAllHouseUnitOccupancies(): List<HousingUnitOccupancy> {
        return housingUnitOccupancyRepository.findAll()
    }

    /**
     * Ruft einen Belegungseintrag (HouseUnitOccupancy) anhand seiner ID ab.
     * @param id Die ID des abzurufenden Eintrags.
     * @return Ein Optional, das den HouseUnitOccupancy-Eintrag enthält, falls gefunden, andernfalls leer.
     */
    fun getHouseUnitOccupancyById(id: String): Optional<HousingUnitOccupancy> {
        return housingUnitOccupancyRepository.findById(id)
    }

    /**
     * Aktualisiert einen bestehenden Belegungseintrag (HouseUnitOccupancy).
     * Hinweis: Diese Methode aktualisiert NICHT automatisch die freien Plätze ('freeSpaces') in der Wohneinheit
     * basierend auf Änderungen. Wenn die zugehörige Wohneinheit geändert wird, muss die Logik
     * für die freien Plätze sorgfältig im Controller oder einer separaten Service-Methode behandelt werden.
     *
     * @param id Die ID des zu aktualisierenden Eintrags.
     * @param updatedHousingUnitOccupancy Das HouseUnitOccupancy-Objekt mit den aktualisierten Daten.
     * @return Das aktualisierte HouseUnitOccupancy-Objekt, oder null, falls kein Eintrag mit der gegebenen ID gefunden wurde.
     */
    fun updateHouseUnitOccupancy(id: String, updatedHousingUnitOccupancy: HousingUnitOccupancy): HousingUnitOccupancy? {
        val existingHouseUnitOccupancyOptional = housingUnitOccupancyRepository.findById(id)
        return if (existingHouseUnitOccupancyOptional.isPresent) {
            val existingHouseUnitOccupancy = existingHouseUnitOccupancyOptional.get()

            // Für ein einfaches Update kopieren wir einfach die Felder.
            // Hinweis: Das @DocumentReference 'housingUnit'-Feld wird aktualisiert, wenn es in updatedHouseUnitOccupancy vorhanden ist.
            val houseUnitOccupancyToSave = existingHouseUnitOccupancy.copy(
                studentId = updatedHousingUnitOccupancy.studentId,
                startDate = updatedHousingUnitOccupancy.startDate,
                expiryDate = updatedHousingUnitOccupancy.expiryDate,
                housingUnit = updatedHousingUnitOccupancy.housingUnit // Die Referenz aktualisieren, falls vorhanden
            )
            housingUnitOccupancyRepository.save(houseUnitOccupancyToSave)
        } else {
            null
        }
    }

    /**
     * Löscht einen Belegungseintrag (HouseUnitOccupancy).
     * Erhöht die Anzahl der freien Plätze ('freeSpaces') im zugehörigen Wohneinheit-Dokument.
     *
     * @param id Die ID des zu löschenden Eintrags.
     * @return True, wenn der Eintrag gelöscht und die freien Plätze aktualisiert wurden, andernfalls false.
     */
    fun deleteHouseUnitOccupancy(studentId: String): Boolean {
        val houseUnitOccupancyOptional = housingUnitOccupancyRepository.findByStudentId(studentId)
        if (houseUnitOccupancyOptional.isPresent) {
            val houseUnitOccupancy = houseUnitOccupancyOptional.get()
            val housingUnitId = houseUnitOccupancy.housingUnit?.id

            if (housingUnitId != null && housingUnitId.isNotBlank()) {
                val wgOptional = housingUnitService.getHousingUnitById(housingUnitId)
                if (wgOptional.isPresent) {
                    val wg = wgOptional.get()
                    // 'freeSpaces' in der Wohneinheit inkrementieren
                    val updatedWG = wg.copy(freeSpaces = wg.freeSpaces + 1)
                    wg.id?.let {
                        housingUnitService.updateHousingUnit(
                            it,
                            updatedWG
                        )
                    } // Das Wohneinheit-Dokument aktualisieren
                } else {
                    println("Warnung: Zugehörige Wohneinheit mit ID '$housingUnitId' wurde beim Löschen der HouseUnitOccupancy nicht gefunden. Freie Plätze nicht aktualisiert.")
                }
            } else {
                println("Warnung: HouseUnitOccupancy '${houseUnitOccupancy.id}' hat keine zugehörige Wohneinheit-ID. Freie Plätze nicht aktualisiert.")
            }

            housingUnitOccupancyRepository.deleteByStudentId(studentId)
            return true
        } else {
            println("Fehler: HouseUnitOccupancy mit ID '$studentId' zum Löschen nicht gefunden.")
            return false
        }
    }

    /**
     * Ruft einen Belegungseintrag anhand der Studenten-ID ab.
     * @param studentId Die ID des Studenten.
     * @return Ein Optional, das den Belegungseintrag enthält, falls einer für den Studenten existiert.
     */
    fun getHouseOccupancyByStudentId(studentId: String): Optional<HousingUnitOccupancy> {
        return housingUnitOccupancyRepository.findByStudentId(studentId)
    }
}