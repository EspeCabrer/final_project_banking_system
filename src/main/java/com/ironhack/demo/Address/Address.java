package com.ironhack.demo.Address;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String postAddress;
    private String City;
    private Integer postalCode;

    public Address(String postAddress, String city, Integer postalCode) {
        this.postAddress = postAddress;
        City = city;
        this.postalCode = postalCode;
    }

    public Address() {
    }

    public String getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }
}
