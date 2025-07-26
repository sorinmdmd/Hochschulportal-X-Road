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

    fun getLanguageCourses(): ResponseEntity<String> {
        val url =
            "http://localhost:1080/r1/DEBO/EDU/BOECOSYSTEM/ZFARUB-PROVIDER/ZFARUB-SERVICE/api/languageCourses"

        val headers = HttpHeaders()
        headers.set("X-Road-Client", "DEBO/EDU/BOECOSYSTEM/ZFARUB-CONSUMER")

        val entity = HttpEntity<String>(headers)

        return restTemplate.exchange(url, HttpMethod.GET, entity, String::class.java)
    }

    fun bookLanguageCourse(courseId: String): ResponseEntity<String> {
        val url =
            "http://localhost:1080/r1/DEBO/EDU/BOECOSYSTEM/ZFARUB-PROVIDER/ZFARUB-SERVICE/api/booking?studentId=${SecurityContext.getStudentId()}&courseId=$courseId"

        val headers = HttpHeaders()
        headers.set("X-Road-Client", "DEBO/EDU/BOECOSYSTEM/ZFARUB-CONSUMER")

        val entity = HttpEntity<String>(headers)

        return restTemplate.exchange(url, HttpMethod.POST, entity, String::class.java)
    }

}