package com.asset.challenge.service;

import com.asset.challenge.model.Shop;
import com.asset.challenge.model.Location;

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
