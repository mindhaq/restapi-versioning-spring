# restapi-versioning-spring

How to implement the different approaches at REST API versioning with Spring MVC

## use case

v1 of API requires address data as a single address parameter in the (german) format

(five digit zip code)(one space character)(name of town)

v2 changes that, requiring a (five digit) zip parameter and a (alphanumeric) town parameter.

Sending data in a format not matching the used version should therefore result in a HTTP/1.1 400

Also, getting the data will provide JSON data accordingly.

Internal storage is considered to always have two fields.

Additionally, there is one simple /hello resource available, which did not change between versions, but is available in both.

## resource definitions

### /address (version 1)

    GET /address
    {
        address: "10243 Berlin"
    }

    POST /address
    address=10243+Berlin

### /address (version 2)

    GET /address
    {
        zip: "10243",
        town: "Berlin"
    }

    POST /address
    zip=10243&town=Berlin

### /hello (version 1 and 2)

    GET /hello
    {
        hello: "world"
    }

## api detection approaches

### URL based:

    /apiurl/v1/address
    
versus
        
    /apiurl/v2/address

### Custom HTTP header:

    /apiheader/address
    
with

    X-API-Version: 1.0
    
versus
   
    X-API-Version: 2.0

### Accept header with vendor and version

    /apiaccept/address

with

    Accept: application/vnd.company.app-v1+json
    
versus

    Accept: application/vnd.company.app-v2+json
