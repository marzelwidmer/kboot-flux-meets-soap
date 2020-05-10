package ch.keepcalm.demo.ws.service

import ch.keepcalm.demo.ws.webservice.consumer.CountryClient
import ch.keepcalm.demo.ws.wsdl.GetCountryResponse
import org.springframework.stereotype.Service

@Service
class CountryService (private val countryClient: CountryClient) {
    fun getCountryByName(name: String): GetCountryResponse {
       return countryClient.getCountry(name)
    }
}
