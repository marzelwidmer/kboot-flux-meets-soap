package ch.keepcalm.demo.camel

import ch.keepcalm.demo.soap.NumberConversion
import ch.keepcalm.demo.soap.NumberConversionSoapType
import ch.keepcalm.demo.soap.NumberToWordsResponse
import org.apache.camel.CamelContext
import org.apache.camel.CamelContextAware
import org.apache.camel.LoggingLevel
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.component.cxf.CxfComponent
import org.apache.camel.component.cxf.CxfEndpoint
import org.apache.camel.component.cxf.DataFormat
import org.apache.camel.component.cxf.common.message.CxfConstants
import org.apache.camel.model.dataformat.JaxbDataFormat
import org.apache.cxf.transport.common.gzip.GZIPFeature
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.support.beans
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.BigInteger
import java.net.URI
import java.time.LocalDateTime

@SpringBootApplication
class CamelClient


fun main(args: Array<String>) {
    runApplication<CamelClient>(*args) {
        addInitializers(
            beans {
                bean {
                    ApplicationRunner {
                        println("ApplicationRunner ----------------->")
                        val justDoIt = ref<JustDoIt>()
                        justDoIt.theSoapCall()
                    }
                }
            }
        )
    }
}


@Component
class JustDoIt() {

    fun theSoapCall() {
        val endpoint = "https://www.dataaccess.com/webservicesserver/NumberConversion.wso"
        val url = URI.create(endpoint).toURL()
        val service = NumberConversion(url)
        val soapService: NumberConversionSoapType = service.getNumberConversionSoap()

        val number1 = BigInteger.valueOf(111)
        val number2 = BigDecimal.valueOf(15.99)

        val words: String = soapService.numberToWords(number1)
        val dollars: String = soapService.numberToDollars(number2)

        println(String.format("\n%s => %s", number1, words))
        println(String.format("%s => %s\n", number2, dollars))
    }
}


@Component
class InvokeSoapServiceRouteBuilder : RouteBuilder(), CamelContextAware {

    @Throws(Exception::class)
    override fun configure() {

        val jaxb = JaxbDataFormat()
        jaxb.contextPath = "ch.keepcalm.demo.soap"

        from("timer:first-timer")
            .to("direct:camel-ws-call")

        from("direct:camel-ws-call")
            .id("camel-ws-call")
            .log(LoggingLevel.DEBUG, "\${body}")
            .setBody(constant(BigInteger.valueOf(111)))
            .bean(FooRequest::class.java)
            .marshal(jaxb)
            .setHeader(CxfConstants.OPERATION_NAME, constant("NumberToWords"))
            .to("cxf:bean:cxfEndpointBean")
            .unmarshal(jaxb)
            .log("//--> : \${body}")
            .process {
                exchange ->
                val response = exchange.`in`.body as NumberToWordsResponse
                exchange.`in`.body = response.numberToWordsResult
            }
            .log("//-------------------------> : \${body}")
    }
}


@Component
class CxfEndpointConfiguration(val context: CamelContext) {

    @Bean
    fun cxfEndpointBean(): CxfEndpoint? {
        val cxfEndpoint = CxfEndpoint("https://www.dataaccess.com/webservicesserver/NumberConversion.wso", CxfComponent(context))
        cxfEndpoint.wsdlURL = "classpath:wsdl/dataaccess-numberconversion.wsdl"
        cxfEndpoint.isSynchronous = true
        cxfEndpoint.features.add(GZIPFeature())
        cxfEndpoint.portName = "NumberConversionSoap"
        cxfEndpoint.dataFormat = DataFormat.PAYLOAD
        return cxfEndpoint
    }
}

//@Component
class TimerRouter : RouteBuilder() {
    override fun configure() {
        from("timer:first-timer")
            .log("logging  ${body()}")
            .transform().constant("Hello CAMEL")
            .bean(GetCurrentTimeBean::class.java)
            .log("logging ${body()}")
            .to("log:first-timer")
    }
}

@Component
class GetCurrentTimeBean {
    fun getCurrentTime(msg: String) = "getCurrentTime -> $msg ${LocalDateTime.now()}"
}
