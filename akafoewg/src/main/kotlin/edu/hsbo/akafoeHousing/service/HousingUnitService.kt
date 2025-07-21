package edu.hsbo.akafoeHousing.service

import edu.hsbo.akafoeHousing.entities.HousingUnit
import edu.hsbo.akafoeHousing.repository.HousingUnitRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class HousingUnitService(val housingUnitRepository: HousingUnitRepository) {

    /**
     * Adds a new HousingUnit to the database.
     * @param HousingUnit The HousingUnit object to be added.
     * @return The saved HousingUnit object.
     */
    fun addHousingUnit(housingUnit: HousingUnit): HousingUnit {
        return housingUnitRepository.save(housingUnit)
    }

    /**
     * Retrieves all HousingUnits from the database.
     * @return A list of all HousingUnit objects.
     */
    fun getAllHousingUnits(): List<HousingUnit> {
        return housingUnitRepository.findAll()
    }

    /**
     * Retrieves a HousingUnit by its ID.
     * @param id The ID of the HousingUnit to retrieve.
     * @return An Optional containing the HousingUnit if found, or empty if not.
     */
    fun getHousingUnitById(id: String): Optional<HousingUnit> {
        return housingUnitRepository.findById(id)
    }

    /**
     * Updates an existing HousingUnit.
     * @param id The ID of the HousingUnit to update.
     * @param updatedHousingUnit The HousingUnit object containing the updated data.
     * @return The updated HousingUnit object, or null if the HousingUnit with the given ID was not found.
     */
    fun updateHousingUnit(id: String, updatedHousingUnit: HousingUnit): HousingUnit? {
        val existingHousingUnitOptional = housingUnitRepository.findById(id)
        return if (existingHousingUnitOptional.isPresent) {
            val existingHousingUnit = existingHousingUnitOptional.get()
            // Create a new HousingUnit object with the existing ID and updated fields
            // This ensures the ID remains the same while other fields are updated
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
            null // Return null if the HousingUnit does not exist
        }
    }

    /**
     * Deletes a HousingUnit by its ID.
     * @param id The ID of the HousingUnit to delete.
     * @return True if the HousingUnit was deleted, false otherwise.
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