package ch.keepcalm.demo.ws.repository

import ch.keepcalm.demo.ws.domain.Country
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono


interface CountryRepository : ReactiveCrudRepository<Country, String> {
    fun findCountryByName(name: String): Mono<Country?>?
}