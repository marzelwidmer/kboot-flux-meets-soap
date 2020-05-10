package ch.keepcalm.demo.ws.webservice.producer

import ch.keepcalm.demo.ws.repository.CountryRepository
import ch.keepcalm.demo.ws.wsdl.GetCountryRequest
import ch.keepcalm.demo.ws.wsdl.GetCountryResponse
import org.slf4j.LoggerFactory
import org.springframework.ws.server.endpoint.annotation.Endpoint
import org.springframework.ws.server.endpoint.annotation.PayloadRoot
import org.springframework.ws.server.endpoint.annotation.RequestPayload
import org.springframework.ws.server.endpoint.annotation.ResponsePayload


@Endpoint
class CountryEndpoint(private val countryRepository: CountryRepository) {

    private val log = LoggerFactory.getLogger(javaClass)

    companion object {
        private const val NAMESPACE_URI = "http://keepcalm.ch/web-services"
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    fun getCountry(@RequestPayload request: GetCountryRequest): GetCountryResponse {
        return GetCountryResponse()
            .apply {
                val badBlockedCode = countryRepository.findCountryByName(request.name)
                    ?.block()
                    ?.toValueObject()
                this.country = badBlockedCode
            }.also {
                log.info("Result form Service : $it")
            }
    }

}