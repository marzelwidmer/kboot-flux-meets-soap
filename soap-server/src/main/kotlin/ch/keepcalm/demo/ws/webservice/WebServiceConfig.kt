package ch.keepcalm.demo.ws.webservice

import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.ws.config.annotation.EnableWs
import org.springframework.ws.config.annotation.WsConfigurerAdapter
import org.springframework.ws.transport.http.MessageDispatcherServlet
import org.springframework.ws.wsdl.WsdlDefinition
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition
import org.springframework.xml.xsd.SimpleXsdSchema
import org.springframework.xml.xsd.XsdSchema
import javax.servlet.Servlet

@EnableWs
@Configuration
class WebServiceConfig : WsConfigurerAdapter() {

    @Bean
    fun messageDispatchServlet(context: ApplicationContext): ServletRegistrationBean<Servlet> =
        ServletRegistrationBean(
            MessageDispatcherServlet()
                .apply {
                    setApplicationContext(context)
                    isTransformWsdlLocations = true
                },
            "/ws/*")


    @Bean(name = ["countries"])
    fun defaultWsdl11Definition(countriesSchema: XsdSchema?): DefaultWsdl11Definition = DefaultWsdl11Definition()
        .apply {
            setPortTypeName("CountriesPort")
            setLocationUri("/ws")
            setTargetNamespace("http://keepcalm.ch/web-services")
            setSchema(countriesSchema)
        }


    @Bean
    fun publishWsdl(): WsdlDefinition? =
        SimpleWsdl11Definition(ClassPathResource("ws/countries.wsdl"))

    @Bean
    fun countriesSchema(): XsdSchema =
        SimpleXsdSchema(ClassPathResource("ws/countries.xsd"))
}