package com.asset.challenge;

import com.asset.challenge.model.Shop;
import com.asset.challenge.model.Address;
import com.asset.challenge.model.Location;

/**
 * Created by jose on 06/04/2017.
 */
public class TestUtils {

    public static Shop initialiseTestShop(String name, String postalCode, double lat, double lon){

        Address address = new Address();
        address.setNumber(0);
        address.setPostCode(postalCode);
        Location location = new Location(lat, lon);

        Shop testShop = new Shop();
        testShop.setName(name);
        testShop.setAddress(address);
        testShop.setLocation(location);
        return testShop;
    }
}
