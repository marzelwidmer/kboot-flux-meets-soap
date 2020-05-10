package ch.keepcalm.demo.ws.adapter

import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.xml.bind.annotation.adapters.XmlAdapter



class LocalDateTimeAdapter : XmlAdapter<String, LocalDateTime>() {

    companion object {
        private val LOG = LoggerFactory.getLogger(LocalDateTimeAdapter::class.java)
    }

    override fun unmarshal(xmlGregorianCalendar: String): LocalDateTime? {
        try {
            return LocalDateTime.parse(xmlGregorianCalendar, DateTimeFormatter.ISO_DATE_TIME)
        } catch (ex: DateTimeParseException) {
            LOG.error("Could not parse date time: $xmlGregorianCalendar", ex)
            return null
        }
    }

    override fun marshal(dateTime: LocalDateTime): String {
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME)
    }
}
