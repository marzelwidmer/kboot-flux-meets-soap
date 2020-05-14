package ch.keepcalm.demo.ws

import ch.keepcalm.demo.ws.service.CountryService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.router
import reactor.blockhound.BlockHound

@SpringBootApplication
class FluxClient

fun main(args: Array<String>) {
//	BlockHound.install()
	runApplication<FluxClient>(*args) {
		addInitializers(
			beans {
				bean {
					router {
						"api".nest {
							GET("/lockdown/{name}") {
								val countryService = ref<CountryService>()
								ok().body(BodyInserters.fromValue(
									countryService.getCountryByName(it.pathVariable("name")))
								)
							}
						}
					}
				}
			}
		)
	}
}