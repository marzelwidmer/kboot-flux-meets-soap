package ch.keepcalm.demo.ws

import ch.keepcalm.demo.ws.wsdl.Currency
/*
country {
    name = "Switzerland"
    capital = "Bern"
    population = 8_603_900
    currency = "CHF"
}
*/
@DslMarker
annotation class CountryBuilder

data class Country(val name: String?, val capital: String?, val population: Int, val currency: String?) {

    private constructor(builder: Builder) : this(builder.name, builder.capital, builder.population, builder.currency)

    companion object {
        inline fun country(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    // Mapping
    fun copy() = ch.keepcalm.demo.ws.domain.Country(name = name.toString(), capital = capital.toString(), population = population, currency = Currency.valueOf(currency.toString()).toString())

    // Builder
    @CountryBuilder
    class Builder {
        var name: String? = null
        var capital: String? = null
        var population: Int = 0
        var currency: String? = null
        fun build() = Country(this)
    }
}

//  countries list
fun countries(vararg countries: Country) = countries.toList()
