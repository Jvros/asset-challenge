package com.asset.challenge.service;

import com.asset.challenge.model.Shop;

/**
 * Created by jose on 05/04/2017.
 */
public interface GeolocationService {

    void enrichWithLocationAsync(Shop shop);

}
