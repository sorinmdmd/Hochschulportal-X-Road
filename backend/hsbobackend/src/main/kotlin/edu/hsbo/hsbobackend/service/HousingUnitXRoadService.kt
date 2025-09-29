package edu.hsbo.hsbobackend.service

import edu.hsbo.hsbobackend.SecurityContext
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import java.time.Instant

@Service
class HousingUnitXRoadService(
    private val restTemplate: RestTemplate
) {
    /**
     * Ruft die Liste der verfügbaren Wohneinheiten vom X-Road-Dienst ab.
     * Stellt eine GET-Anfrage an den AKAFOE-ACCOMODATION-PROVIDER.
     * @return ResponseEntity<String> Die Antwort des externen Dienstes, typischerweise eine JSON-Liste als String.
     */
    fun getHousingUnitsList(): ResponseEntity<String> {
        val url =
            "http://localhost:1080/r1/DEBO/EDU/BOECOSYSTEM/AKAFOE-ACCOMODATION-PROVIDER/ACCOMODATIONSERVICE/api/housingUnits"

        // Setzt den für X-Road erforderlichen Client-Header
        val headers = HttpHeaders()
        headers.set("X-Road-Client", "DEBO/EDU/BOECOSYSTEM/AKAFOE-ACCOMMODATION-CONSUMER")

        val entity = HttpEntity<String>(headers)

        // Führt die Anfrage aus und gibt die Antwort zurück
        return restTemplate.exchange(url, HttpMethod.GET, entity, String::class.java)
    }

    /**
     * Bucht eine bestimmte Wohneinheit für den aktuell angemeldeten Studenten.
     * Stellt eine POST-Anfrage an den AKAFOE-ACCOMODATION-PROVIDER. Die Studenten-ID wird aus dem SecurityContext geholt.
     * @param accommodationID Die ID der zu buchenden Wohneinheit.
     * @param startDate Das Startdatum für die Buchung.
     * @return ResponseEntity<String> Die Antwort des externen Dienstes, z.B. eine Buchungsbestätigung.
     */
    fun bookHousingUnit(accommodationID: String, startDate: Instant): ResponseEntity<String> {
        val url =
            "http://localhost:1080/r1/DEBO/EDU/BOECOSYSTEM/AKAFOE-ACCOMODATION-PROVIDER/ACCOMODATIONSERVICE/api/housingUnitActivate/${SecurityContext.getStudentId()}/$accommodationID/$startDate"

        val headers = HttpHeaders()
        headers.set("X-Road-Client", "DEBO/EDU/BOECOSYSTEM/AKAFOE-ACCOMMODATION-CONSUMER")

        val entity = HttpEntity<String>(headers)

        return restTemplate.exchange(url, HttpMethod.POST, entity, String::class.java)
    }

    /**
     * Ruft die Log-Einträge für den aktuell angemeldeten Studenten vom X-Road-Dienst ab.
     * Stellt eine GET-Anfrage an den AKAFOE-ACCOMODATION-PROVIDER.
     * @return ResponseEntity<String> Die Antwort des externen Dienstes mit den Log-Daten.
     */
    fun getStudentLogs(): ResponseEntity<String> {
        val url =
            "http://localhost:1080/r1/DEBO/EDU/BOECOSYSTEM/AKAFOE-ACCOMODATION-PROVIDER/ACCOMODATIONSERVICE/api/getHousingLogs/${SecurityContext.getStudentId()}"

        val headers = HttpHeaders()
        headers.set("X-Road-Client", "DEBO/EDU/BOECOSYSTEM/AKAFOE-ACCOMMODATION-CONSUMER")

        val entity = HttpEntity<String>(headers)

        return restTemplate.exchange(url, HttpMethod.GET, entity, String::class.java)
    }

    /**
     * Ruft die Details der aktuellen Buchung für den angemeldeten Studenten ab.
     * Stellt eine GET-Anfrage an den AKAFOE-ACCOMODATION-PROVIDER.
     * @return ResponseEntity<String> Die Antwort des externen Dienstes mit den Buchungsdetails.
     */
    fun getMyBooking(): ResponseEntity<String> {
        val url =
            "http://localhost:1080/r1/DEBO/EDU/BOECOSYSTEM/AKAFOE-ACCOMODATION-PROVIDER/ACCOMODATIONSERVICE/api/housingUnitOccupancyByStudentId/${SecurityContext.getStudentId()}"

        val headers = HttpHeaders()
        headers.set("X-Road-Client", "DEBO/EDU/BOECOSYSTEM/AKAFOE-ACCOMMODATION-CONSUMER")

        val entity = HttpEntity<String>(headers)

        return restTemplate.exchange(url, HttpMethod.GET, entity, String::class.java)
    }

    fun deleteBooking(): ResponseEntity<String> {
        val url =
            "http://localhost:1080/r1/DEBO/EDU/BOECOSYSTEM/AKAFOE-ACCOMODATION-PROVIDER/ACCOMODATIONSERVICE/api/housingUnitOccupancy/${SecurityContext.getStudentId()}"

        val headers = HttpHeaders()
        headers.set("X-Road-Client", "DEBO/EDU/BOECOSYSTEM/AKAFOE-ACCOMMODATION-CONSUMER")

        val entity = HttpEntity<String>(headers)

        return restTemplate.exchange(url, HttpMethod.DELETE, entity, String::class.java)
    }
}