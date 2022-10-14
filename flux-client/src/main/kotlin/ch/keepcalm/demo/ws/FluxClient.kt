package ch.keepcalm.demo.ws

import ch.keepcalm.demo.ws.service.CountryReactiveService
import ch.keepcalm.demo.ws.service.CountryService
import ch.keepcalm.demo.ws.wsdl.GetCountryResponse
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.router

@SpringBootApplication
class FluxClient

fun main(args: Array<String>) {

//    BlockHound.install()

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
                            GET("/easing/{name}") {
                                val countryReactiveService = ref<CountryReactiveService>()
                                ok().body(
                                    BodyInserters.fromPublisher(countryReactiveService.getCountryByName(it.pathVariable("name")), GetCountryResponse::class.java)
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}
