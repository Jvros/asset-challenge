package com.test.service.impl;

import com.test.TestUtils;
import com.test.model.Location;
import com.test.model.Shop;
import com.test.model.Address;
import com.test.service.GeolocationService;
import com.test.service.impl.InMemoryShopServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by jose on 05/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InMemoryShopServiceTest {

    public static int CONCURRENT_USERS = 5;

    @Autowired
    InMemoryShopServiceImpl service;

    @MockBean
    GeolocationService mockedLocationService;


    @Before
    public void purgeData(){
        this.service.clearData();
    }

    @Test
    public void testConcurrentSave() throws InterruptedException {
        Shop initialShop = TestUtils.initialiseTestShop("Test","AAAA",0.0,0.0);

        this.service.putShop(initialShop);

        final Shop[] shopsToInsert = new Shop[CONCURRENT_USERS];
        final Shop[] shopsToReturn = new Shop[CONCURRENT_USERS];
        //Thread[] users = new Thread[CONCURRENT_USERS];
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch end = new CountDownLatch(CONCURRENT_USERS);

        for(int i=0;i<CONCURRENT_USERS;i++){
            final int index = i;
            shopsToInsert[index] = TestUtils.initialiseTestShop("Test","AAAA",0.0,0.0);
            Thread user = new Thread(() -> {
                try {
                    start.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                shopsToReturn[index] = this.service.putShop(shopsToInsert[index]);
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

        Shop near = TestUtils.initialiseTestShop("Near","AAAA",39.472699, -0.380503);
        Shop far = TestUtils.initialiseTestShop("Far","AAAA", 39.470993, -0.381672);


        this.service.putShop(near);
        this.service.putShop(far);

        assertNotEquals(far.getName(), this.service.getShop(userlocation).getName());
        assertEquals(near.getName(), this.service.getShop(userlocation).getName());


    }
}
