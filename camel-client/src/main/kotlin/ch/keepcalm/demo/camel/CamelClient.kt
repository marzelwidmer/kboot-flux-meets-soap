package ch.keepcalm.demo.camel

import ch.keepcalm.demo.soap.NumberConversion
import ch.keepcalm.demo.soap.NumberConversionSoapType
import org.apache.camel.CamelContextAware
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.component.cxf.CxfEndpoint
import org.apache.camel.component.cxf.DataFormat
import org.apache.camel.component.cxf.common.message.CxfConstants
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
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
//                        justDoIt.theSoapCall()
                    }
                }
            }
        )
    }
}

@Component
class JustDoIt(){

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


//    val endpoint = "https://www.dataaccess.com/webservicesserver/NumberConversion.wso"
//    val url = URI.create(endpoint).toURL()
//    val service = NumberConversion(url)
//    val soapService: NumberConversionSoapType = service.getNumberConversionSoap()
//    var cxfAddressLine = ("cxf:$endpoint" //
//            + "&dataFormat=POJO" //
//            + "&serviceClass=$service" //
//            + "&serviceName=NumberConversion" //
//            + "&synchronous=true" //
//            + "&loggingFeatureEnabled=true" //
//            + "&portName=NumberConversionSoap")
//

    @Throws(Exception::class)
    override fun configure() {

        val cxfEndpoint: CxfEndpoint = CxfEndpoint()
        cxfEndpoint.address="https://www.dataaccess.com/webservicesserver/NumberConversion.wso"
        cxfEndpoint.camelContext = getCamelContext()
        cxfEndpoint.dataFormat = DataFormat.PAYLOAD
        cxfEndpoint.serviceClass= NumberConversion::class.java
        cxfEndpoint.wsdlURL= "https://www.dataaccess.com/webservicesserver/NumberConversion.wso?WSDL"
        camelContext.addEndpoint("camel-ws-call", cxfEndpoint);

        from("direct:camel-ws-call")
            .setHeader(CxfConstants.METHOD, constant("numberToWords"))
            .setBody(constant(BigInteger.valueOf(111)))
//            .bean(NumberConversion::class.java, "getNumberConversionSoap")
            .to("cxf://camel-ws-call?dataFormat=PAYLOAD")
            .log("//-------------------------> : ${body()}")


//        from("direct:camel-ws-call")
//            .id("camel-ws-call")
//            .log(LoggingLevel.DEBUG, "\${body}")
//            .setBody(constant(BigInteger.valueOf(111)))
//            .bean(NumberToWords::class.java)
//            .setHeader(CxfConstants.METHOD, constant("numberToWords"))
//            .setHeader(CxfConstants.OPERATION_NAMESPACE, constant("http://www.dataaccess.com/webservicesserver/")) // Invoke our test service using CXF
//            .to("cxf://https://www.dataaccess.com/webservicesserver/NumberConversion.wso"
//                    + "?serviceClass=ch.keepcalm.demo.soap.NumberConversion")
//                    + "&wsdlURL=/wsdl/BookService.wsdl") // You can retrieve fields from the response using the Simple language
//            .log("-------------------------s: \${body}")


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
    fun getCurrentTime(msg : String) = "getCurrentTime -> $msg ${LocalDateTime.now()}"
}
