package edu.hsbo.hsbobackend.service

import edu.hsbo.hsbobackend.SecurityContext
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class LanguageCourseXRoadService(private val restTemplate: RestTemplate) {

    /**
     * Ruft eine Liste aller verfügbaren Sprachkurse vom X-Road-Dienst ab.
     * Stellt eine GET-Anfrage an den ZFARUB-PROVIDER.
     * @return ResponseEntity<String> Die Antwort des externen Dienstes, die eine Liste der Kurse als String enthält.
     */
    fun getLanguageCourses(): ResponseEntity<String> {
        val url =
            "http://localhost:1080/r1/DEBO/EDU/BOECOSYSTEM/ZFARUB-PROVIDER/ZFARUB-SERVICE/api/languageCourses"

        // Setzt den für X-Road erforderlichen Client-Header
        val headers = HttpHeaders()
        headers.set("X-Road-Client", "DEBO/EDU/BOECOSYSTEM/ZFARUB-CONSUMER")

        val entity = HttpEntity<String>(headers)

        // Führt die Anfrage aus und gibt die Antwort zurück
        return restTemplate.exchange(url, HttpMethod.GET, entity, String::class.java)
    }

    /**
     * Bucht einen bestimmten Sprachkurs für den aktuell angemeldeten Studenten.
     * Stellt eine POST-Anfrage an den ZFARUB-PROVIDER. Die Studenten-ID wird aus dem SecurityContext geholt.
     * @param courseId Die ID des zu buchenden Kurses.
     * @return ResponseEntity<String> Die Antwort des externen Dienstes, z.B. eine Buchungsbestätigung.
     */
    fun bookLanguageCourse(courseId: String): ResponseEntity<String> {
        val url =
            "http://localhost:1080/r1/DEBO/EDU/BOECOSYSTEM/ZFARUB-PROVIDER/ZFARUB-SERVICE/api/booking?studentId=${SecurityContext.getStudentId()}&courseId=$courseId"

        val headers = HttpHeaders()
        headers.set("X-Road-Client", "DEBO/EDU/BOECOSYSTEM/ZFARUB-CONSUMER")

        val entity = HttpEntity<String>(headers)

        return restTemplate.exchange(url, HttpMethod.POST, entity, String::class.java)
    }

    /**
     * Findet alle Sprachkurse, die vom aktuell angemeldeten Studenten gebucht wurden.
     * Stellt eine GET-Anfrage an den ZFARUB-PROVIDER.
     * @return ResponseEntity<String> Die Antwort des externen Dienstes mit der Liste der gebuchten Kurse.
     */
    fun findStudentCourses(): ResponseEntity<String> {
        val url =
            "http://localhost:1080/r1/DEBO/EDU/BOECOSYSTEM/ZFARUB-PROVIDER/ZFARUB-SERVICE/api/booking/student/${SecurityContext.getStudentId()}"

        val headers = HttpHeaders()
        headers.set("X-Road-Client", "DEBO/EDU/BOECOSYSTEM/ZFARUB-CONSUMER")

        val entity = HttpEntity<String>(headers)

        return restTemplate.exchange(url, HttpMethod.GET, entity, String::class.java)
    }
}