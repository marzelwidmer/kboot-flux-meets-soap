# kboot-flux-meets-soap

This will demonstrate how we can deal with a `Blocking API` in a `Reactive World`.

This Sample provides a `soap-server` who demonstrate the blocking downstream `API`.
The `flux-client` have two `REST API` who call the blocking `SOAP` endpoint the `lockdown` with no special implementation,
where the [Blockhound](https://github.com/reactor/BlockHound) will throw an exception. 

The `easing` `REST API` have implemented [Avoiding Reactor Meltdown](https://www.youtube.com/watch?v=xCu73WVg8Ps&t=7s) show case how to manage `Blocking API`.

With this approach to manage `Blocking API` in the same service in not in a separate service we have all the nice features like `retry` `filter` `map` and so on in our `Servive A` from the `Reactive Streams API`. 
We also not have to manage no other service and have less network hops who are sometimes increase complexity and so on.


![FluxMeetsSoap.png](/img/FluxMeetsSoap.png)


# Flux Client
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
The Server have an implementation with a demonstration how we can write own `Kotlin DSL`.

## DSL
```kotlin
 country {
    name = "Switzerland"
    capital = "Bern"
    population = 8_603_900
    currency = "CHF"
}
```


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


 
> **_References:_**  
>[Kotlin DSL in under an hour](https://www.youtube.com/watch?v=zYNbsVv9oN0)
>[Do Super Language with Kotlin](https://www.youtube.com/watch?v=hYXAFO3q3qU)
>[Blockhound](https://github.com/reactor/BlockHound
>[Avoiding Reactor Meltdown](https://www.youtube.com/watch?v=xCu73WVg8Ps&t=7s)
 

 

