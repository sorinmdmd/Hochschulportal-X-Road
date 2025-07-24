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
     * Creates a new HouseUnitOccupancy entry.
     * Decrements the freeSpaces in the associated WG document.
     *
     * @param studentId The university ID or similar identifier for the person occupying the space.
     * @param housingUnitId The ID of the WG to associate with this active entry.
     * @param startDate The start date of the active entry.
     * @param expiryDate The optional expiry date of the active entry.
     * @return The created HouseUnitOccupancy object, or null if the associated WG is not found or has no free spaces.
     */
    fun createHouseUnitOccupancy(
        studentId: String,
        housingUnitId: String,
        startDate: Instant,
        expiryDate: Instant? = null
    ): HousingUnitOccupancy? {
        // Basic validation for required fields
        if (studentId.isBlank()) {
            println("Error: uniId is required for HouseUnitOccupancy creation.")
            return null
        }
        if (housingUnitId.isBlank()) {
            println("Error: WG ID is required for HouseUnitOccupancy creation.")
            return null
        }

        // Fetch the associated WG document
        val housingUnitOptional = housingUnitService.getHousingUnitById(housingUnitId)
        if (housingUnitOptional.isPresent) {
            val housingUnit = housingUnitOptional.get()

            // Check if there are free spaces
            if (housingUnit.freeSpaces > 0) {
                // Decrement freeSpaces in the WG
                val updatedHousingUnit = housingUnit.copy(freeSpaces = housingUnit.freeSpaces - 1)
                housingUnit.id?.let { housingUnitService.updateHousingUnit(it, updatedHousingUnit) } // Update the WG document

                // Create the HouseUnitOccupancy object with the provided parameters and the updated WG reference
                val newHousingUnitOccupancy = HousingUnitOccupancy(
                    studentId = studentId,
                    startDate = startDate,
                    expiryDate = expiryDate,
                    housingUnit = updatedHousingUnit // Associate the updated WG object
                )
                return housingUnitOccupancyRepository.save(newHousingUnitOccupancy)
            } else {
                println("Error: WG '${housingUnit.name}' (ID: $housingUnitId) has no free spaces.")
                return null
            }
        } else {
            println("Error: Associated WG with ID '$housingUnitId' not found.")
            return null
        }
    }

    /**
     * Retrieves all HouseUnitOccupancy entries.
     * @return A list of all HouseUnitOccupancy objects.
     */
    fun getAllHouseUnitOccupancies(): List<HousingUnitOccupancy> {
        return housingUnitOccupancyRepository.findAll()
    }

    /**
     * Retrieves a HouseUnitOccupancy entry by its ID.
     * @param id The ID of the HouseUnitOccupancy to retrieve.
     * @return An Optional containing the HouseUnitOccupancy if found, or empty if not.
     */
    fun getHouseUnitOccupancyById(id: String): Optional<HousingUnitOccupancy> {
        return housingUnitOccupancyRepository.findById(id)
    }

    /**
     * Updates an existing HouseUnitOccupancy entry.
     * Note: This method does NOT automatically update freeSpaces in the WG based on changes
     * to the associated WG. If the associated WG changes, freeSpaces logic needs to be
     * handled carefully in the controller or a separate service method.
     *
     * @param id The ID of the HouseUnitOccupancy to update.
     * @param updatedHousingUnitOccupancy The HouseUnitOccupancy object with updated data.
     * @return The updated HouseUnitOccupancy object, or null if the HouseUnitOccupancy with the given ID was not found.
     */
    fun updateHouseUnitOccupancy(id: String, updatedHousingUnitOccupancy: HousingUnitOccupancy): HousingUnitOccupancy? {
        val existingHouseUnitOccupancyOptional = housingUnitOccupancyRepository.findById(id)
        return if (existingHouseUnitOccupancyOptional.isPresent) {
            val existingHouseUnitOccupancy = existingHouseUnitOccupancyOptional.get()

            // For a simple update, we just copy the fields.
            // Note: The @DocumentReference 'wg' field will be updated if provided in updatedHouseUnitOccupancy.
            val houseUnitOccupancyToSave = existingHouseUnitOccupancy.copy(
                studentId = updatedHousingUnitOccupancy.studentId,
                startDate = updatedHousingUnitOccupancy.startDate,
                expiryDate = updatedHousingUnitOccupancy.expiryDate,
                housingUnit = updatedHousingUnitOccupancy.housingUnit // Update the reference if provided
            )
            housingUnitOccupancyRepository.save(houseUnitOccupancyToSave)
        } else {
            null
        }
    }

    /**
     * Deletes a HouseUnitOccupancy entry.
     * Increments the freeSpaces in the associated WG document.
     *
     * @param id The ID of the HouseUnitOccupancy to delete.
     * @return True if the HouseUnitOccupancy was deleted and WG freeSpaces updated, false otherwise.
     */
    fun deleteHouseUnitOccupancy(id: String): Boolean {
        val houseUnitOccupancyOptional = housingUnitOccupancyRepository.findById(id)
        if (houseUnitOccupancyOptional.isPresent) {
            val houseUnitOccupancy = houseUnitOccupancyOptional.get()
            val housingUnitId = houseUnitOccupancy.housingUnit?.id

            if (housingUnitId != null && housingUnitId.isNotBlank()) {
                val wgOptional = housingUnitService.getHousingUnitById(housingUnitId)
                if (wgOptional.isPresent) {
                    val wg = wgOptional.get()
                    // Increment freeSpaces in the WG
                    val updatedWG = wg.copy(freeSpaces = wg.freeSpaces + 1)
                    wg.id?.let { housingUnitService.updateHousingUnit(it, updatedWG) } // Update the WG document
                } else {
                    println("Warning: Associated WG with ID '$housingUnitId' not found during HouseUnitOccupancy deletion. Free spaces not updated.")
                }
            } else {
                println("Warning: HouseUnitOccupancy '${houseUnitOccupancy.id}' has no associated WG ID. Free spaces not updated.")
            }

            housingUnitOccupancyRepository.deleteById(id)
            return true
        } else {
            println("Error: HouseUnitOccupancy with ID '$id' not found for deletion.")
            return false
        }
    }
}