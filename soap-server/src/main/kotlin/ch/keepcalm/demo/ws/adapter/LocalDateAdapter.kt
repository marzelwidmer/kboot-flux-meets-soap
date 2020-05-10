package ch.keepcalm.demo.ws.adapter

import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.xml.bind.annotation.adapters.XmlAdapter

class LocalDateAdapter : XmlAdapter<String, LocalDate>() {

    companion object {
        private val LOG = LoggerFactory.getLogger(LocalDateAdapter::class.java)
    }

    override fun unmarshal(xmlGregorianCalendar: String): LocalDate? {
        try {
            return LocalDate.parse(xmlGregorianCalendar, DateTimeFormatter.ISO_DATE)
        } catch (ex: DateTimeParseException) {
            LOG.error("Could not parse date: $xmlGregorianCalendar", ex)
            return null
        }
    }

    override fun marshal(dateTime: LocalDate): String {
        return dateTime.format(DateTimeFormatter.ISO_DATE)
    }
}