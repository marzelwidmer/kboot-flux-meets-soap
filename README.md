# Flux Client
[Avoiding Reactor Meltdown](https://www.youtube.com/watch?v=xCu73WVg8Ps&t=7s)

There are two `API` who call a blocking `SOAP` endpoint the one `lockdown` and `easing` who demostrate with the `Blockhound` 
PlugIn the [Avoiding Reactor Meltdown](https://www.youtube.com/watch?v=xCu73WVg8Ps&t=7s) show case how to manage `Blocking API`
in a `Reactive World`.

## API Lockdown Switzerland
```bash
http :8080/api/lockdown/Switzerland
```

## API easing Switzerland
```bash
http :8080/api/easing/Switzerland
```



## Blockhound
### Dependency 
```xml
<!-- Blockhound	-->
<dependency>
    <groupId>io.projectreactor.tools</groupId>
    <artifactId>blockhound</artifactId>
    <version>${blockhound.version}</version>
</dependency>
```
### Installation
Add `BlockHound.install()` in the main function.
````kotlin
fun main(args: Array<String>) {
	BlockHound.install()
	runApplication<FluxClient>(*args)
}
````




# SOAP Server

[Kotlin DSL in under an hour](https://www.youtube.com/watch?v=zYNbsVv9oN0)
[Do Super Language with Kotlin](https://www.youtube.com/watch?v=hYXAFO3q3qU)

## WSDL 
`http://localhost:8888/ws/countries.wsdl`

## EndPoint
`http://localhost:8888/ws`

## Request 
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

## Call Service with `HTTPie`

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