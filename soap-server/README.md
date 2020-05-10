# WSDL 
`http://localhost:8888/ws/countries.wsdl`

# EndPoint
`http://localhost:8888/ws`

# Request 
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
				  xmlns:gs="http://keepcalm.ch/web-services">
   <soapenv:Header/>
   <soapenv:Body>
      <gs:getCountryRequest>
         <gs:name>Switzerland</gs:name>
      </gs:getCountryRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

# Call Service with `HTTPie`

```bash
printf '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                                  xmlns:gs="http://keepcalm.ch/web-services">
   <soapenv:Header/>
   <soapenv:Body>
      <gs:getCountryRequest>
         <gs:name>Switzerland</gs:name>
      </gs:getCountryRequest>
   </soapenv:Body>
</soapenv:Envelope>'| http  --follow --timeout 3600 POST http://localhost:8888/ws \
 Content-Type:'text/xml'
```