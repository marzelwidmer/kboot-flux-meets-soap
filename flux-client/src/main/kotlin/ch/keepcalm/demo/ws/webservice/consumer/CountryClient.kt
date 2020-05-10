package ch.keepcalm.demo.ws.webservice.consumer

import ch.keepcalm.demo.ws.wsdl.GetCountryRequest
import ch.keepcalm.demo.ws.wsdl.GetCountryResponse
import org.slf4j.LoggerFactory
import org.springframework.ws.client.core.support.WebServiceGatewaySupport
import org.springframework.ws.soap.client.core.SoapActionCallback

class CountryClient : WebServiceGatewaySupport() {

    private val log = LoggerFactory.getLogger(javaClass)

    fun getCountry(country: String): GetCountryResponse {
        val request = GetCountryRequest()
        request.name = country
        log.info("Requesting location for $country")
        return webServiceTemplate
            .marshalSendAndReceive("http://localhost:8888/ws/countries", request,
                SoapActionCallback(
                    "http://keepcalm.ch/web-services/GetCountryRequest")) as GetCountryResponse
    }

}