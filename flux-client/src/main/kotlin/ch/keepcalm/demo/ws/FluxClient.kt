package ch.keepcalm.demo.ws

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.blockhound.BlockHound

@SpringBootApplication
class FluxClient

fun main(args: Array<String>) {
	BlockHound.install()
	runApplication<FluxClient>(*args)
}
