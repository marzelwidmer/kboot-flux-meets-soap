package ch.keepcalm.demo.ws.domain

import ch.keepcalm.demo.ws.wsdl.Currency
import ch.keepcalm.demo.ws.wsdl.Country as CountryWS
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Country(
        @Id val id: String = UUID.randomUUID().toString(),
        val name: String,
        val population: Int = 0,
        val capital: String = "",
        val currency: String = ""
) {

    companion object {
        fun fromValueObject(countryWS: CountryWS) = Country(id = countryWS.id, name = countryWS.name,
            population = countryWS.population, capital = countryWS.capital, currency = countryWS.capital)
    }

    fun toValueObject() = CountryWS().apply {
        name = this@Country.name
        population = this@Country.population
        capital = this@Country.capital
        currency = Currency.valueOf(this@Country.currency)
    }
}