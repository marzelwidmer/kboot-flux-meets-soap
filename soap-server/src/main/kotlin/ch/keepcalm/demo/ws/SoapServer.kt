package ch.keepcalm.demo.ws

import ch.keepcalm.demo.ws.Country.Companion.country
import ch.keepcalm.demo.ws.repository.CountryRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import reactor.kotlin.core.publisher.toFlux

@SpringBootApplication
class SoapServer

fun main(args: Array<String>) {
    val log = LoggerFactory.getLogger(SoapServer::class.java)

    runApplication<SoapServer>(*args) {
        addInitializers(
            beans {
                bean {
                    ApplicationRunner {
                        val countryRepository = ref<CountryRepository>()

                        countryRepository
                            // first cleanUp Database
                            .deleteAll()
                            // create a list of Countries
                            .thenMany(
                                // Kotlin DSL
                                countries(
                                    country {
                                        name = "Switzerland"
                                        capital = "Bern"
                                        population = 8_603_900
                                        currency = "CHF"
                                    },
                                    country {
                                        name = "Spain"
                                        capital = "Madrid"
                                        population = 47_100_396
                                        currency = "EUR"
                                    }
/*
                                listOf(
                                    Country(
                                        name = "Switzerland",
                                        capital = "Bern",
                                        population = 8_603_900,
                                        currency = "CHF"),
                                    Country(
                                        name = "Spain",
                                        capital = "Madrid",
                                        population = 47_100_396 ,
                                        currency = "EUR")
*/

                                ).toFlux()
                            )
                            .map {
                                country {
                                    name = it.name
                                    capital = it.capital
                                    population = it.population
                                    currency = it.currency
                                }
                            }
                            // Save it to the Database
                            .flatMap {
                                countryRepository.save(it.copy()) // copy the values from the DSL class in the Database Layer Class
                            }
                            // Search all entries
                            .thenMany(countryRepository.findAll())
                            // subscribe - let`s do the work...
                            .subscribe { log.info("--> $it") }
                    }
                }
            }
        )
    }
}
