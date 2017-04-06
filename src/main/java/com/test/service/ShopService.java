package com.test.service;

import com.test.model.Location;
import com.test.model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jose on 05/04/2017.
 */
@Component
public class ShopService {

    private ConcurrentHashMap<String,Shop> inMemmoryCache = new ConcurrentHashMap<>();

    @Autowired
    GeolocationService geolocationService;

    public Shop saveShop(Shop shop) {
        geolocationService.enrichWithLocation(shop);
        return inMemmoryCache.put(shop.getName(),shop);
    }

    public Shop getShopByName(String name) {
        return inMemmoryCache.get(name);
    }

    public Shop getShopByLocation(Location location) {
        DistancePair reducedDistance = inMemmoryCache.reduce(100,
                (name, shop) -> new DistancePair(shop, location.distance(shop.getLocation())),
                (distancePair, distancePair2) -> (distancePair.dist < distancePair2.dist) ? distancePair : distancePair2);

        return (reducedDistance!=null)?reducedDistance.s:null;
    }

    public void clearData(){
        this.inMemmoryCache.clear();
    }

    private class DistancePair{
        Shop s;
        double dist;

        public DistancePair(Shop s, double dist) {
            this.s = s;
            this.dist = dist;
        }
    }
}
