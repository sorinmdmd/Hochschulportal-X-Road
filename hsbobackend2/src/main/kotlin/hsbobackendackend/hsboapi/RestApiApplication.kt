package hsbobackend.hsboapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RestApiApplication

fun main(args: Array<String>) {
	println("HALLO")
	runApplication<RestApiApplication>(*args)
}
