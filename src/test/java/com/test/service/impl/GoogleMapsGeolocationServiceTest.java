package com.test.service.impl;

import com.test.model.Shop;
import com.test.model.ShopAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by jose on 06/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GoogleMapsGeolocationServiceTest {
    @Autowired
    GoogleMapsGeolocationService service;

    @Test
    public void enrichWithLocation() throws Exception {
        Shop testShop = new Shop();
        testShop.setName("Mercat Central Valencia");
        testShop.setDescription("Mercat Central Valencia");
        testShop.setAddress(new ShopAddress());
        testShop.getAddress().setPostCode("Mercat Central Valencia, 46001");

        this.service.enrichWithLocation(testShop);

        assertNotNull(testShop.getLocation());
        assertEquals(39.4735895,testShop.getLocation().getLat(),0.1);
        assertEquals(-0.3789726,testShop.getLocation().getLon(),0.1);

    }

}