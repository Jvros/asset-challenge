package com.test.service;

import com.test.model.Location;
import com.test.model.Shop;
import com.test.model.ShopAddress;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.BDDMockito.*;

/**
 * Created by jose on 05/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopServiceTest {

    public static int CONCURRENT_USERS = 5;

    @Autowired
    ShopService service;

    @MockBean
    GeolocationService mockedLocationService;


    @Before
    public void purgeData(){
        this.service.clearData();
    }

    private Shop initialiseTestShop(String name){
        return initialiseTestShop(name, 0.0,0.0);
    }

    private Shop initialiseTestShop(String name, double lat, double lon){
        Shop testShop = new Shop();
        testShop.setName(name);
        testShop.setDescription("TestDescription");
        testShop.setAddress(new ShopAddress());
        testShop.getAddress().setNumber(0);
        testShop.getAddress().setPostCode("12345");
        testShop.setLocation(new Location(lat,lon));
        return testShop;
    }


    @Test
    public void testConcurrentSave() throws InterruptedException {
        Shop initialShop = initialiseTestShop("Test");

        this.service.saveShop(initialShop);

        final Shop[] shopsToInsert = new Shop[CONCURRENT_USERS];
        final Shop[] shopsToReturn = new Shop[CONCURRENT_USERS];
        //Thread[] users = new Thread[CONCURRENT_USERS];
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch end = new CountDownLatch(CONCURRENT_USERS);

        for(int i=0;i<CONCURRENT_USERS;i++){
            final int index = i;
            shopsToInsert[index] = initialiseTestShop("Test");
            Thread user = new Thread(() -> {
                try {
                    start.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                shopsToReturn[index] = this.service.saveShop(shopsToInsert[index]);
                end.countDown();
            });
            user.start();
        }

        start.countDown();
        end.await();

        //Check that only one user got initial shop results
        assertEquals("Only one user can get the initial shop",1, Arrays.stream(shopsToReturn).filter(shop -> shop == initialShop).count());
        assertEquals("All users but one mush have got a previous inserted shop",CONCURRENT_USERS -1,Arrays.stream(shopsToReturn).filter(
                returnedShop -> Arrays.stream(shopsToInsert).filter(insertedShop -> insertedShop == returnedShop).count() == 1
        ).count());

    }

    @Test
    public void checkGetByLocation(){
        Location userlocation = new Location(39.473507, -0.378838);
        Shop near = initialiseTestShop("Near",39.472699, -0.380503);
        Shop far = initialiseTestShop("Far", 39.470993, -0.381672);

        this.service.saveShop(near);
        this.service.saveShop(far);

        assertNotEquals(far, this.service.getShopByLocation(userlocation));
        assertEquals(near, this.service.getShopByLocation(userlocation));


    }
}
