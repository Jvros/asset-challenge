package com.test.service.impl;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.PendingResult;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.test.model.Location;
import com.test.model.Shop;
import com.test.service.GeolocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by jose on 05/04/2017.
 */
@Component
public class GoogleMapsGeolocationService implements GeolocationService{


    @Autowired
    GeoApiContext geoApiContext;

    @Override
    public void enrichWithLocationAsync(Shop shop) {
        GeocodingApiRequest geocode = GeocodingApi.geocode(geoApiContext, shop.getAddress().getPostCode());
        geocode.setCallback(new PendingResult.Callback<GeocodingResult[]>() {
            @Override
            public void onResult(GeocodingResult[] result) {
                LatLng gLocation = result[0].geometry.location;

                System.out.println(gLocation);
                Location l = new Location(gLocation.lat,gLocation.lng);
                shop.setLocation(l);
            }

            @Override
            public void onFailure(Throwable e) {
                //Nothing so far.
            }
        });
    }
}
