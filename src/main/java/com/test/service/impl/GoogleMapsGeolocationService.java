package com.test.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.test.model.Location;
import com.test.model.Shop;
import com.test.model.ShopAddress;
import com.test.service.GeolocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonJsonParser;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by jose on 05/04/2017.
 */
@Component
public class GoogleMapsGeolocationService implements GeolocationService{

    @Value("${google.api.key}")
    private String apiKey;

    @Value("${google.api.url}")
    private String url;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void enrichWithLocation(Shop shop) {
        //ShopAddress address = shop.getAddress();
        //new GoogleRequest(shop.getAddress().getPostCode(), this.key);
        String response = restTemplate.getForObject(url, String.class, shop.getAddress().getPostCode(), this.apiKey);
        DocumentContext jsonContext = JsonPath.parse(response);
        String status = jsonContext.read("$.status");
        double latitude = jsonContext.read("$.results[0].geometry.location.lat");
        double longitude = jsonContext.read("$.results[0].geometry.location.lng");

        if("OK".equals(status)){
            shop.setLocation(new Location(latitude,longitude));
        }

    }
}
