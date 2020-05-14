package ch.keepcalm.demo.ws.webservice

import ch.keepcalm.demo.ws.wsdl.GetCountryRequest
import ch.keepcalm.demo.ws.wsdl.GetCountryResponse
import org.slf4j.LoggerFactory
import org.springframework.ws.client.core.support.WebServiceGatewaySupport
import org.springframework.ws.soap.client.core.SoapActionCallback
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

class SoapClient : WebServiceGatewaySupport() {

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


    fun getCountryReactive(country: String): Mono<GetCountryResponse> {
        val request = GetCountryRequest()
        request.name = country
        log.info("Requesting location for $country")
        return Mono.fromCallable {
            webServiceTemplate
                .marshalSendAndReceive("http://localhost:8888/ws/countries", request,
                    SoapActionCallback(
                        "http://keepcalm.ch/web-services/GetCountryRequest")) as GetCountryResponse
        }
            // properly schedule above blocking call on
            // scheduler meant for blocking tasks
            .subscribeOn(Schedulers.boundedElastic())
    }

}