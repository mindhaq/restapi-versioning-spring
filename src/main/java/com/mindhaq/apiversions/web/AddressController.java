package com.mindhaq.apiversions.web;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mindhaq.apiversions.domain.Address;
import com.mindhaq.apiversions.service.AddressService;

@Controller
public class AddressController {

    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/apiurl/v1/address", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public AddressParamV1 getAddressUrlV1() {
        Address address = addressService.load();
        return convertToV1(address);
    }

    @RequestMapping(value = "/apiurl/v2/address", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public AddressParamV2 getAddressUrlV2() {
        Address address = addressService.load();
        return convertToV2(address);
    }

    @RequestMapping(
        value = "/apiheader/address", method = GET, produces = APPLICATION_JSON_VALUE, headers = "X-API-Version=v1"
    )
    @ResponseBody
    public AddressParamV1 getAddressHeaderV1() {
        Address address = addressService.load();
        return convertToV1(address);
    }

    @RequestMapping(
        value = "/apiheader/address", method = GET, produces = APPLICATION_JSON_VALUE, headers = "X-API-Version=v2"
    )
    @ResponseBody
    public AddressParamV2 getAddressHeaderV2() {
        Address address = addressService.load();
        return convertToV2(address);
    }

    @RequestMapping(value = "/apiaccept/address", method = GET, produces = "application/vnd.company.app-v1+json")
    @ResponseBody
    public AddressParamV1 getAddressAcceptV1() {
        Address address = addressService.load();
        return convertToV1(address);
    }

    @RequestMapping(value = "/apiaccept/address", method = GET, produces = "application/vnd.company.app-v2+json")
    @ResponseBody
    public AddressParamV2 getAddressAcceptV2() {
        Address address = addressService.load();
        return convertToV2(address);
    }

    private AddressParamV1 convertToV1(final Address address) {
        AddressParamV1 addressParamV1 = new AddressParamV1();
        addressParamV1.setAddress(address.getZip() + ' ' + address.getTown());
        return addressParamV1;
    }

    private AddressParamV2 convertToV2(final Address address) {
        AddressParamV2 addressParamV2 = new AddressParamV2();
        addressParamV2.setZip(address.getZip());
        addressParamV2.setTown(address.getTown());
        return addressParamV2;
    }

}
