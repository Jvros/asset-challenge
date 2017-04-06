package com.test;

import com.test.model.Address;
import com.test.model.Location;
import com.test.model.Shop;

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
