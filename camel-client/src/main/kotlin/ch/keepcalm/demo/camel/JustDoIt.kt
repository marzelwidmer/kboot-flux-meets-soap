package ch.keepcalm.demo.camel

import ch.keepcalm.demo.soap.NumberConversion
import ch.keepcalm.demo.soap.NumberConversionSoapType
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.BigInteger
import java.net.URI

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
