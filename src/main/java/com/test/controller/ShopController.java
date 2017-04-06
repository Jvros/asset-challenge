package com.test.controller;

import com.test.model.Location;
import com.test.model.Shop;
import com.test.model.ShopAddress;
import com.test.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jose on 05/04/2017.
 */
@RequestMapping("/shops")
@RestController
public class ShopController {

    @Autowired
    ShopService service;


    @RequestMapping(method = RequestMethod.POST)
    public Shop addShop(@RequestBody Shop shop){
        return service.saveShop(shop);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Shop getShopGeoLocation(@RequestParam double lat, @RequestParam double lon){
        Location userLocation = new Location(lat, lon);
        return service.getShopByLocation(userLocation);
    }

    //Extra
    //@RequestMapping(method = RequestMethod.GET)
    /*public Shop getShop(@RequestParam String name){
        return service.getShopByName(name);
    }*/

}
