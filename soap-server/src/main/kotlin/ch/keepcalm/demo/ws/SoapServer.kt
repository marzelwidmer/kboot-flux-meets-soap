package ch.keepcalm.demo.ws

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SoapServer

fun main(args: Array<String>) {
	runApplication<SoapServer>(*args)
}
