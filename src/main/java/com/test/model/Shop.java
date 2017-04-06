package com.test.model;

/**
 * Created by jose on 05/04/2017.
 */

public class Shop {
    private String name;
    private String description;
    private ShopAddress address;
    private Location location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ShopAddress getAddress() {
        return address;
    }

    public void setAddress(ShopAddress address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
