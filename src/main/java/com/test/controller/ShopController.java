package com.test.controller;

import com.test.model.Location;
import com.test.model.Shop;
import com.test.service.ShopService;
import com.test.service.impl.InMemoryShopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
/**
 * Created by jose on 05/04/2017.
 */
@RequestMapping("/shops")
@RestController
public class ShopController {

    @Autowired
    ShopService service;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Shop putShop(@RequestBody Shop shop) {

        //Names should not be empty or null.
        String name = shop.getName();
        if(name == null || name.isEmpty()) throw new InvalidShopException();

        //Address postalCode should not be empty or null.
        if(shop.getAddress() == null ||
                shop.getAddress().getPostCode() == null ||
                shop.getAddress().getPostCode().isEmpty())
            throw new InvalidShopException();

        shop.add(linkTo(methodOn(ShopController.class).getShop(shop.getName())).withSelfRel());
        shop.add(linkTo(methodOn(ShopController.class).listShops()).withRel("all"));

        return service.putShop(shop);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Shop getShop(@RequestParam String name){
        Shop shop = this.service.getShop(name);
        if(shop == null) throw new ShopNotFoundException();
        return shop;
    }

    @GetMapping("list")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Collection<Shop> listShops(){
        Collection<Shop> shops = this.service.getShops();
        return shops;
    }

    @GetMapping("/locate")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Shop getShopGeoLocation(@RequestParam double lat, @RequestParam double lon){
        Location userLocation = new Location(lat, lon);
        Shop shop = service.getShop(userLocation);
        if(shop == null) throw new ShopNotFoundException();
        return shop;
    }

    //Extra
    //@RequestMapping(method = RequestMethod.GET)
    /*public Shop getShop(@RequestParam String name){
        return service.getShopByName(name);
    }*/

    public class ShopNotFoundException extends RuntimeException {}

    public class InvalidShopException extends RuntimeException {}
}
