package ch.keepcalm.demo.camel

import ch.keepcalm.demo.soap.NumberToWords
import java.math.BigInteger


class FooRequest {

    fun createRequest(number: BigInteger): NumberToWords? {
        val of = ch.keepcalm.demo.soap.ObjectFactory()
        val req =  of.createNumberToWords()
        req.ubiNum = number
        return req
    }
}

