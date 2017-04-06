package com.test.service;

import com.test.model.Location;
import com.test.model.Shop;

import java.util.Collection;

/**
 * Created by jose on 06/04/2017.
 */
public interface ShopService {
    Shop getShop(String name);
    Shop getShop(Location location);
    Collection<Shop> getShops();
    Shop putShop(Shop newShop);
}
