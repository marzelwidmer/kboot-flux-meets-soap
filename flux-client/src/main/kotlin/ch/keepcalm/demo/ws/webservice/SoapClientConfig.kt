package ch.keepcalm.demo.ws.webservice

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.oxm.jaxb.Jaxb2Marshaller

@Configuration
class SoapClientConfig {

    @Bean
    fun marshaller(): Jaxb2Marshaller? = Jaxb2Marshaller()
        .also {
            // this package must match the package in the <generatePackage> specified in
            // pom.xml
            it.contextPath = "ch.keepcalm.demo.ws.wsdl"
        }

    @Bean
    fun countryClient(marshaller: Jaxb2Marshaller?): SoapClient? = SoapClient()
        .also {
            it.defaultUri = "http://localhost:8888/ws"
            it.marshaller = marshaller
            it.unmarshaller = marshaller
        }
}