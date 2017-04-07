package com.asset.challenge.geo.conf;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jose on 06/04/2017.
 */
@Configuration
public class GoogleAPIConfig {

    @Value("${google.api.key}")
    private String apiKey;

    @Bean
    public GeoApiContext googleGeoApiContext(){
        GeoApiContext ctx = new GeoApiContext();
        ctx.setApiKey(this.apiKey);
        return ctx;

    }
}
