package edu.hsbo.akafoeHousing.service

import edu.hsbo.akafoeHousing.entities.HousingUnit
import edu.hsbo.akafoeHousing.repository.HousingUnitOccupancyRepository
import edu.hsbo.akafoeHousing.repository.HousingUnitRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class HousingUnitService(
    val housingUnitRepository: HousingUnitRepository,
    val housingUnitOccupancyRepository: HousingUnitOccupancyRepository
) {

    /**
     * Fügt eine neue Wohneinheit (HousingUnit) zur Datenbank hinzu.
     * @param housingUnit Das hinzuzufügende HousingUnit-Objekt.
     * @return Das gespeicherte HousingUnit-Objekt.
     */
    fun addHousingUnit(housingUnit: HousingUnit): HousingUnit {
        return housingUnitRepository.save(housingUnit)
    }

    /**
     * Ruft alle Wohneinheiten aus der Datenbank ab.
     * @return Eine Liste aller HousingUnit-Objekte.
     */
    fun getAllHousingUnits(): List<HousingUnit> {
        return housingUnitRepository.findAll()
    }

    /**
     * Ruft eine Wohneinheit anhand ihrer ID ab.
     * @param id Die ID der abzurufenden Wohneinheit.
     * @return Ein Optional, das die Wohneinheit enthält, falls gefunden, andernfalls leer.
     */
    fun getHousingUnitById(id: String): Optional<HousingUnit> {
        return housingUnitRepository.findById(id)
    }

    /**
     * Aktualisiert eine bestehende Wohneinheit.
     * @param id Die ID der zu aktualisierenden Wohneinheit.
     * @param updatedHousingUnit Das HousingUnit-Objekt mit den aktualisierten Daten.
     * @return Das aktualisierte HousingUnit-Objekt, oder null, falls keine Wohneinheit mit der angegebenen ID gefunden wurde.
     */
    fun updateHousingUnit(id: String, updatedHousingUnit: HousingUnit): HousingUnit? {
        val existingHousingUnitOptional = housingUnitRepository.findById(id)
        return if (existingHousingUnitOptional.isPresent) {
            val existingHousingUnit = existingHousingUnitOptional.get()
            // Erstellt ein neues HousingUnit-Objekt mit der bestehenden ID und den aktualisierten Feldern.
            // Dies stellt sicher, dass die ID gleich bleibt, während andere Felder aktualisiert werden.
            val housingUnitToSave = existingHousingUnit.copy(
                name = updatedHousingUnit.name,
                description = updatedHousingUnit.description,
                category = updatedHousingUnit.category,
                city = updatedHousingUnit.city,
                zipCode = updatedHousingUnit.zipCode,
                address = updatedHousingUnit.address,
                freeSpaces = updatedHousingUnit.freeSpaces
            )
            housingUnitRepository.save(housingUnitToSave)
        } else {
            null // Gibt null zurück, wenn die Wohneinheit nicht existiert
        }
    }

    /**
     * Löscht eine Wohneinheit anhand ihrer ID.
     * @param id Die ID der zu löschenden Wohneinheit.
     * @return True, wenn die Wohneinheit gelöscht wurde, andernfalls false.
     */
    fun deleteHousingUnit(id: String): Boolean {
        return if (housingUnitRepository.existsById(id)) {
            housingUnitRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}