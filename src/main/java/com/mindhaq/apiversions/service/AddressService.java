package com.mindhaq.apiversions.service;

import org.springframework.stereotype.Service;

import com.mindhaq.apiversions.domain.Address;

@Service
public class AddressService {

    private Address address;

    public Address load() {
        return address;
    }

    public void save(final Address address) {
        this.address = address;
    }
}
