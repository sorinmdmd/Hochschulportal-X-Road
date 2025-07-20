package edu.hsbo.akafoewg.service

import edu.hsbo.akafoewg.entities.WG
import edu.hsbo.akafoewg.repository.WGRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class WGService(val wgRepository: WGRepository) {

    /**
     * Adds a new WG to the database.
     * @param wg The WG object to be added.
     * @return The saved WG object.
     */
    fun addWG(wg: WG): WG {
        return wgRepository.save(wg)
    }

    /**
     * Retrieves all WGs from the database.
     * @return A list of all WG objects.
     */
    fun getAllWGs(): List<WG> {
        return wgRepository.findAll()
    }

    /**
     * Retrieves a WG by its ID.
     * @param id The ID of the WG to retrieve.
     * @return An Optional containing the WG if found, or empty if not.
     */
    fun getWGById(id: String): Optional<WG> {
        return wgRepository.findById(id)
    }

    /**
     * Updates an existing WG.
     * @param id The ID of the WG to update.
     * @param updatedWG The WG object containing the updated data.
     * @return The updated WG object, or null if the WG with the given ID was not found.
     */
    fun updateWG(id: String, updatedWG: WG): WG? {
        val existingWGOptional = wgRepository.findById(id)
        return if (existingWGOptional.isPresent) {
            val existingWG = existingWGOptional.get()
            // Create a new WG object with the existing ID and updated fields
            // This ensures the ID remains the same while other fields are updated
            val wgToSave = existingWG.copy(
                name = updatedWG.name,
                description = updatedWG.description,
                category = updatedWG.category,
                city = updatedWG.city,
                zipCode = updatedWG.zipCode,
                address = updatedWG.address,
                freeSpaces = updatedWG.freeSpaces
            )
            wgRepository.save(wgToSave)
        } else {
            null // Return null if the WG does not exist
        }
    }

    /**
     * Deletes a WG by its ID.
     * @param id The ID of the WG to delete.
     * @return True if the WG was deleted, false otherwise.
     */
    fun deleteWG(id: String): Boolean {
        return if (wgRepository.existsById(id)) {
            wgRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}