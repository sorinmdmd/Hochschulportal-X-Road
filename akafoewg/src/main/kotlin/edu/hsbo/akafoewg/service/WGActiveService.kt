package edu.hsbo.akafoewg.service

import edu.hsbo.akafoewg.entities.WGActive
import edu.hsbo.akafoewg.repository.WGActiveRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.Date
import java.util.Optional

@Service
class WGActiveService(val wgActiveRepository: WGActiveRepository, val wgService:WGService) {
    /**
     * Creates a new WGActive entry.
     * Decrements the freeSpaces in the associated WG document.
     *
     * @param uniId The university ID or similar identifier for the person occupying the space.
     * @param wgId The ID of the WG to associate with this active entry.
     * @param startDate The start date of the active entry.
     * @param expiryDate The optional expiry date of the active entry.
     * @return The created WGActive object, or null if the associated WG is not found or has no free spaces.
     */
    fun createWGActive(uniId: String, wgId: String, startDate: Instant, expiryDate: Instant? = null): WGActive? {
        // Basic validation for required fields
        if (uniId.isBlank()) {
            println("Error: uniId is required for WGActive creation.")
            return null
        }
        if (wgId.isBlank()) {
            println("Error: WG ID is required for WGActive creation.")
            return null
        }

        // Fetch the associated WG document
        val wgOptional = wgService.getWGById(wgId)
        if (wgOptional.isPresent) {
            val wg = wgOptional.get()

            // Check if there are free spaces
            if (wg.freeSpaces > 0) {
                // Decrement freeSpaces in the WG
                val updatedWG = wg.copy(freeSpaces = wg.freeSpaces - 1)
                wg.id?.let { wgService.updateWG(it, updatedWG) } // Update the WG document

                // Create the WGActive object with the provided parameters and the updated WG reference
                val newWGActive = WGActive(
                    uniId = uniId,
                    startDate = startDate,
                    expiryDate = expiryDate,
                    wg = updatedWG // Associate the updated WG object
                )
                return wgActiveRepository.save(newWGActive)
            } else {
                println("Error: WG '${wg.name}' (ID: $wgId) has no free spaces.")
                return null
            }
        } else {
            println("Error: Associated WG with ID '$wgId' not found.")
            return null
        }
    }

    /**
     * Retrieves all WGActive entries.
     * @return A list of all WGActive objects.
     */
    fun getAllWGActives(): List<WGActive> {
        return wgActiveRepository.findAll()
    }

    /**
     * Retrieves a WGActive entry by its ID.
     * @param id The ID of the WGActive to retrieve.
     * @return An Optional containing the WGActive if found, or empty if not.
     */
    fun getWGActiveById(id: String): Optional<WGActive> {
        return wgActiveRepository.findById(id)
    }

    /**
     * Updates an existing WGActive entry.
     * Note: This method does NOT automatically update freeSpaces in the WG based on changes
     * to the associated WG. If the associated WG changes, freeSpaces logic needs to be
     * handled carefully in the controller or a separate service method.
     *
     * @param id The ID of the WGActive to update.
     * @param updatedWGActive The WGActive object with updated data.
     * @return The updated WGActive object, or null if the WGActive with the given ID was not found.
     */
    fun updateWGActive(id: String, updatedWGActive: WGActive): WGActive? {
        val existingWGActiveOptional = wgActiveRepository.findById(id)
        return if (existingWGActiveOptional.isPresent) {
            val existingWGActive = existingWGActiveOptional.get()

            // For a simple update, we just copy the fields.
            // Note: The @DocumentReference 'wg' field will be updated if provided in updatedWGActive.
            val wgActiveToSave = existingWGActive.copy(
                uniId = updatedWGActive.uniId,
                startDate = updatedWGActive.startDate,
                expiryDate = updatedWGActive.expiryDate,
                wg = updatedWGActive.wg // Update the reference if provided
            )
            wgActiveRepository.save(wgActiveToSave)
        } else {
            null
        }
    }

    /**
     * Deletes a WGActive entry.
     * Increments the freeSpaces in the associated WG document.
     *
     * @param id The ID of the WGActive to delete.
     * @return True if the WGActive was deleted and WG freeSpaces updated, false otherwise.
     */
    fun deleteWGActive(id: String): Boolean {
        val wgActiveOptional = wgActiveRepository.findById(id)
        if (wgActiveOptional.isPresent) {
            val wgActive = wgActiveOptional.get()
            val wgId = wgActive.wg?.id

            if (wgId != null && wgId.isNotBlank()) {
                val wgOptional = wgService.getWGById(wgId)
                if (wgOptional.isPresent) {
                    val wg = wgOptional.get()
                    // Increment freeSpaces in the WG
                    val updatedWG = wg.copy(freeSpaces = wg.freeSpaces + 1)
                    wg.id?.let { wgService.updateWG(it, updatedWG) } // Update the WG document
                } else {
                    println("Warning: Associated WG with ID '$wgId' not found during WGActive deletion. Free spaces not updated.")
                }
            } else {
                println("Warning: WGActive '${wgActive.id}' has no associated WG ID. Free spaces not updated.")
            }

            wgActiveRepository.deleteById(id)
            return true
        } else {
            println("Error: WGActive with ID '$id' not found for deletion.")
            return false
        }
    }
}