package com.asset.challenge.model;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by jose on 05/04/2017.
 */

public class Shop  extends ResourceSupport{
    private String name;
    private Address address;
    private Location location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
