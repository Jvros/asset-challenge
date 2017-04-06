package com.test.service;

import com.test.model.Shop;

/**
 * Created by jose on 05/04/2017.
 */
public interface GeolocationService {

    void enrichWithLocationAsync(Shop shop);

}
