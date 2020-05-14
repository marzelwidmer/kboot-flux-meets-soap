package ch.keepcalm.demo.ws.service

import ch.keepcalm.demo.ws.webservice.SoapClient
import ch.keepcalm.demo.ws.wsdl.GetCountryResponse
import org.springframework.stereotype.Service

@Service
class CountryService (private val soapClient: SoapClient) {

    fun getCountryByName(name: String): GetCountryResponse {
       return soapClient.getCountry(name)
    }
}
