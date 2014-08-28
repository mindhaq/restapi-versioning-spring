package com.mindhaq.apiversions.domain;

public class Address {

    private final String zip;

    private final String town;

    public Address(final String zip, final String town) {
        this.zip = zip;
        this.town = town;
    }

    public String getZip() {
        return zip;
    }

    public String getTown() {
        return town;
    }
}
