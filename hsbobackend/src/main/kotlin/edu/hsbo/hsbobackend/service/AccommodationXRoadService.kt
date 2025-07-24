package edu.hsbo.hsbobackend.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity

@Service
class AccommodationXRoadService(
    private val restTemplate: RestTemplate
) {
    fun getAccommodationsList(): ResponseEntity<String> {
        val url = "http://localhost:1080/r1/DEBO/EDU/BOECOSYSTEM/AKAFOE-ACCOMODATION-PROVIDER/ACCOMODATIONSERVICE/api/housingUnits"

        val headers = HttpHeaders()
        headers.set("X-Road-Client", "DEBO/EDU/BOECOSYSTEM/AKAFOE-ACCOMMODATION-CONSUMER")

        val entity = HttpEntity<String>(headers)

        return restTemplate.exchange(url, HttpMethod.GET, entity, String::class.java)
    }
}
