

Add some shops (They are real sites):

#######################################################
POST /shops HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Cache-Control: no-cache
{
  "name": "Mercado Central",
  "description": null,
  "address": {
    "number": 0,
    "postCode": "Mercado Central Valencia 46001"
  }
}
#######################################################
POST http://localhost:8080/shops
{
  "name": "Mercado Colon",
  "description": null,
  "address": {
    "number": 0,
    "postCode": "Mercado Colon Valencia"
  }
}

#######################################################
POST http://localhost:8080/shops
{
  "name": "Mercado Rojas Clemente",
  "description": null,
  "address": {
    "number": 0,
    "postCode": "Mercado Rojas Clemente Valencia"
  }
}

#######################################################

Check which Market is closest to your location if:

a) you are on Valencia's Town hall. You will get "Mercado Central"
http://localhost:8080/shops?lat=39.469796&lon=-0.377001

b) or if you are on "Torres de Quart", you will get "Mercado Rojas Clemente"
http://localhost:8080/shops?lat=39.475785&lon=-0.383810

Fill free to add more places :)



Challenge questions:
How you would expand this solution, given a longer development period?

How would you go about testing this solution?

How would you integrate this solution into an existing collection of solutions used by the Retail Manager?

How would you go about deploying this solution to production systems?