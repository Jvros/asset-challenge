##### The Asset Management Digital Challenge ####

##### Summary
a) Develop a RESTful web service to manage "Shops" with 2 basic operations:
- Register new shop in the system.
- Get the nearest shop

b) When adding new shops:
- Enrich the shop with map coordinates (using Google Maps API)
- Return previous version of the shop to the client when replacing shops. (Thread-safe)

##### My solution

1.- Packages
There are have 3 main package groups:
- controller: Contains all web controllers which will take care of all client-server interaction. In this case there is only have one, ShopController but there could be more in the future.
- model: Contains all POJO classes with represent the data stored in the application. As this is a small application, we use this model as DTO objects too.
    But this can change if we add persistence or the data model becomes more complex. Then, it could be better to create different objects for JPA entities and DTOs.
- service: Contains all classes with some business logic.

2- Technical decisions made.
- Persistence:
I decided to use a very simple ConcurrentHashMap object to store "Shop" objects in memory. If we wanted to keep in-memory persistence but adding escalation,
we could have used Hazelcast to get the same behaviour but spreading the "Shop" objects among all instances. This very easy to do as Spring Boot only needs you to define the config file or class
after adding the proper dependency. Then replace my concurrent map with a Hazelcast map.
If this micro-service were more complex or we have had to persist the data on a database we could have use JPA annotations.

- Non-blocking operations
When saving a "shop", the service needs to get its map coordinates. This call needs to be done in the background.

- Hypermedia-Driven RESTful Web Service
Although current service contains few operations, adding Spring HATEOAS its very easy and provides self-explanatory service.
I added typical "self" link pointing to the object itself and "all" link pointing to the URL where client can get all shops.

- Testing
I added Spring Test for testing and used mockito to mock out of scope classes.
There are Unit tests (InMemoryShopServiceTest) and Integration tests (GoogleMapsGeolocationServiceTest, ShopControllerTest)

3- Challenge questions:
How you would expand this solution, given a longer development period?
It's difficult to say without knowing what the client wants but...
- we can add user authentication and authorization (Spring Security)
- we can add a distributed cache (Hazelcast)
- we can add database persistence with JPA (Hibernate)


How would you go about testing this solution?
I keep using Spring Test framework to create Unit and Integration test.

How would you integrate this solution into an existing collection of solutions used by the Retail Manager?
Current service is HATEOAS, we can add new links to other services and link other service to this, the client just needs to follow them on each response.

How would you go about deploying this solution to production systems?
I am used to work with current tools in the bank. So I would use Teamcity and uDeploy for that purpose.


4- Some real usage:
(Google API key could be blocked, add your own key :)

Add some shops:

POST /shops/
{
  "name": "Mercado Central",
  "address": {
    "number": 0,
    "postCode": "Mercado Central Valencia"
  }
}

POST /shops/
{
  "name": "Mercado Colon",
  "address": {
    "number": 0,
    "postCode": "Mercado Colon Valencia"
  }
}

POST /shops/
{
  "name": "Mercado Rojas Clemente",
  "address": {
    "number": 0,
    "postCode": "Mercado Rojas Clemente Valencia"
  }
}

Get the list of shops:

GET http://localhost:8080/shops/list
[
  {
    "name": "Mercado Rojas Clemente",
    "address": {
      "number": 0,
      "postCode": "Mercado Rojas Clemente Valencia"
    },
    "location": {
      "lat": 39.4728728,
      "lon": -0.3852884
    },
    "links": [
      {
        "rel": "self",
        "href": "http://localhost:8080/shops/?name=Mercado%20Rojas%20Clemente"
      },
      {
        "rel": "all",
        "href": "http://localhost:8080/shops/list"
      }
    ]
  },
  {
    "name": "Mercado Colon",
    "address": {
      "number": 0,
      "postCode": "Mercado Colon Valencia"
    },
    "location": {
      "lat": 39.4688691,
      "lon": -0.3686455
    },
    "links": [
      {
        "rel": "self",
        "href": "http://localhost:8080/shops/?name=Mercado%20Colon"
      },
      {
        "rel": "all",
        "href": "http://localhost:8080/shops/list"
      }
    ]
  },
  {
    "name": "Mercado Central",
    "address": {
      "number": 0,
      "postCode": "Mercado Central Valencia"
    },
    "location": {
      "lat": 39.4735895,
      "lon": -0.3789726
    },
    "links": [
      {
        "rel": "self",
        "href": "http://localhost:8080/shops/?name=Mercado%20Central"
      },
      {
        "rel": "all",
        "href": "http://localhost:8080/shops/list"
      }
    ]
  }
]

Check which Market is closest to your location if:

a) you are on Valencia's Town hall. You will get "Mercado Central"
GET /shops/locate?lat=39.469796&lon=-0.377001
{
  "name": "Mercado Central",
  "address": {
    "number": 0,
    "postCode": "Mercado Central Valencia"
  },
  "location": {
    "lat": 39.4735895,
    "lon": -0.3789726
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/shops/?name=Mercado%20Central"
    },
    "all": {
      "href": "http://localhost:8080/shops/list"
    }
  }
}

b) or if you are on "Torres de Quart", you will get "Mercado Rojas Clemente"
http://localhost:8080/shops/locate?lat=39.475785&lon=-0.383810
{
  "name": "Mercado Rojas Clemente",
  "address": {
    "number": 0,
    "postCode": "Mercado Rojas Clemente Valencia"
  },
  "location": {
    "lat": 39.4728728,
    "lon": -0.3852884
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/shops/?name=Mercado%20Rojas%20Clemente"
    },
    "all": {
      "href": "http://localhost:8080/shops/list"
    }
  }
}

Fill free to add more places :)



