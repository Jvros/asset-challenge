package com.test.controller;
import com.test.model.Location;
import com.test.model.Shop;
import com.test.model.ShopAddress;
import com.test.service.ShopService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by jose on 05/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShopControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService;

    private Shop initialiseTestShop(double lat, double lon){
        Shop testShop = new Shop();
        testShop.setName("TestShop");
        testShop.setDescription("TestDescription");
        testShop.setAddress(new ShopAddress());
        testShop.getAddress().setNumber(0);
        testShop.getAddress().setPostCode("12345");
        testShop.setLocation(new Location(lat,lon));
        return testShop;
    }


    /*@Test
    public void getShopReturnsExistingShop() throws Exception {
        Shop testShop = initialiseTestShop();
        given(shopService.getShopByName(testShop.getName())).willReturn(testShop);

        //Test if "TestShop" exists
        this.mockMvc.perform(get("/shops").param("name", testShop.getName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value(testShop.getName()))
                .andExpect(jsonPath("$.description").value(testShop.getDescription()))
                .andExpect(jsonPath("$.address.number").value(testShop.getAddress().getNumber()))
                .andExpect(jsonPath("$.address.postCode").value(testShop.getAddress().getPostCode()));

        //Test that "OtherShop" don't exists

    }*/

    /*@Test
    public void getShopReturnsNullWhenShopNotExist() throws Exception {
        Shop testShop = initialiseTestShop();

        given(shopService.getShopByName(testShop.getName())).willReturn(testShop);

        this.mockMvc.perform(get("/shops").param("name", "somethingNotFound"))
                .andExpect(status().isOk())
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(""));
    }*/

    @Test
    public void testGetRequestWithUserLocation() throws Exception {
        Shop testShop = initialiseTestShop(1.0,1.0);
        given(shopService.getShopByLocation(any(Location.class))).willReturn(testShop);
        //Test if "TestShop" exists
        this.mockMvc.perform(get("/shops")
                        .param("lat", String.valueOf(testShop.getLocation().getLat()))
                        .param("lon",String.valueOf(testShop.getLocation().getLon())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value(testShop.getName()))
                .andExpect(jsonPath("$.description").value(testShop.getDescription()))
                .andExpect(jsonPath("$.address.number").value(testShop.getAddress().getNumber()))
                .andExpect(jsonPath("$.address.postCode").value(testShop.getAddress().getPostCode()))
                .andExpect(jsonPath("$.location.lat").value(testShop.getLocation().getLat()))
                .andExpect(jsonPath("$.location.lon").value(testShop.getLocation().getLon()));



    }

    @Test
    public void testPostRequestToSaveShop() throws Exception {
        Shop testShop = initialiseTestShop(0,0);
        given(shopService.saveShop(any(Shop.class))).will(invocation -> {
            Shop newShop = invocation.getArgumentAt(0,Shop.class);
            newShop.setName("Prev"+newShop.getName());
            return newShop;
        });

        this.mockMvc.perform(
                post("/shops").contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("{\"name\": \"TestShop\",\"description\": \"NewDescription\",\"address\": {\"number\": 0,\"postCode\": \"00000\"}}".getBytes(Charset.forName("UTF8"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value("Prev"+testShop.getName()));
    }



}
