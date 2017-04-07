package com.asset.challenge.controller;
import com.asset.challenge.TestUtils;
import com.asset.challenge.model.Shop;
import com.asset.challenge.service.impl.InMemoryShopServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.asset.challenge.model.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
    private InMemoryShopServiceImpl shopService;

    @Test
    public void putShop() throws Exception {
        Shop testShop = TestUtils.initialiseTestShop("TestShop", "000000",1.0,1.0);
        Shop prevTestShop = TestUtils.initialiseTestShop("TestShop", "1111111",1.0,1.0);

        given(shopService.putShop(any(Shop.class))).willReturn(prevTestShop);
        given(shopService.getShop(testShop.getName())).willReturn(testShop);

        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writeValueAsString(testShop);
        this.mockMvc.perform(
                post("/shops/").contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(string))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address.postCode").value(prevTestShop.getAddress().getPostCode()));
    }

    @Test
    public void getShop() throws Exception {

    }

    @Test
    public void listShops() throws Exception {
    }

    @Test
    public void getShopGeoLocation() throws Exception {
        Shop testShop = TestUtils.initialiseTestShop("TestShop", "12345",1.0,1.0);

        given(shopService.getShop(any(Location.class))).willReturn(testShop);

        this.mockMvc.perform(get("/shops/locate")
                .param("lat", String.valueOf(testShop.getLocation().getLat()))
                .param("lon",String.valueOf(testShop.getLocation().getLon())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testShop.getName()))
                .andExpect(jsonPath("$.address.number").value(testShop.getAddress().getNumber()))
                .andExpect(jsonPath("$.address.postCode").value(testShop.getAddress().getPostCode()))
                .andExpect(jsonPath("$.location.lat").value(testShop.getLocation().getLat()))
                .andExpect(jsonPath("$.location.lon").value(testShop.getLocation().getLon()));
    }






    @Test
    public void testGetRequestWithUserLocation() throws Exception {




    }

    @Test
    public void testPostRequestToSaveShop() throws Exception {

    }



}
