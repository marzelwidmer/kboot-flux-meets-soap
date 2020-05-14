package ch.keepcalm.demo.ws.service

import ch.keepcalm.demo.ws.webservice.SoapClient
import ch.keepcalm.demo.ws.wsdl.GetCountryResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CountryReactiveService  (private val soapClient: SoapClient) {

    fun getCountryByName(name: String): Mono<GetCountryResponse> {
        return soapClient.getCountryReactive(name)
    }
}
