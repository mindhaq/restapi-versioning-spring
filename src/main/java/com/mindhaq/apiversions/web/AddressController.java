package com.mindhaq.apiversions.web;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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

    @RequestMapping(value = "/apiurl/v1/address", method = POST)
    @ResponseBody
    @ResponseStatus(ACCEPTED)
    public void saveAddressUrlV1(@Valid @ModelAttribute final AddressParamV1 addressParamV1) {
        Address address = convertFromV1(addressParamV1);
        addressService.save(address);
    }

    @RequestMapping(value = "/apiurl/v2/address", method = POST)
    @ResponseStatus(ACCEPTED)
    public void saveAddressUrlV2(@Valid @ModelAttribute final AddressParamV2 addressParamV2) {
        Address address = convertFromV2(addressParamV2);
        addressService.save(address);
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

    @RequestMapping(value = "/apiheader/address", method = POST, headers = "X-API-Version=v1")
    @ResponseStatus(ACCEPTED)
    public void saveAddressHeaderV1(@ModelAttribute @Valid final AddressParamV1 addressParamV1) {
        Address address = convertFromV1(addressParamV1);
        addressService.save(address);
    }

    @RequestMapping(value = "/apiheader/address", method = POST, headers = "X-API-Version=v2")
    @ResponseStatus(ACCEPTED)
    public void saveAddressHeaderV2(@ModelAttribute @Valid final AddressParamV2 addressParamV2) {
        Address address = convertFromV2(addressParamV2);
        addressService.save(address);
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

    @RequestMapping(value = "/apiaccept/address", method = POST, headers = "Accept=application/vnd.company.app-v1+json")
    @ResponseStatus(ACCEPTED)
    public void saveAddressAcceptV1(@ModelAttribute @Valid final AddressParamV1 addressParamV1) {
        Address address = convertFromV1(addressParamV1);
        addressService.save(address);
    }

    @RequestMapping(value = "/apiaccept/address", method = POST, headers = "Accept=application/vnd.company.app-v2+json")
    @ResponseStatus(ACCEPTED)
    public void saveAddressAcceptV2(@ModelAttribute @Valid final AddressParamV2 addressParamV2) {
        Address address = convertFromV2(addressParamV2);
        addressService.save(address);
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

    private Address convertFromV1(final AddressParamV1 addressParamV1) {
        final String zip, town;

        Pattern p = Pattern.compile("(\\d{5}) (.*)");
        Matcher m = p.matcher(addressParamV1.getAddress());
        if (!m.matches()) {
            throw new IllegalArgumentException("unparsable address " + addressParamV1.getAddress());
        }

        zip = m.group(1);
        town = m.group(2);
        return new Address(zip, town);
    }

    private Address convertFromV2(final AddressParamV2 addressParamV2) {
        return new Address(addressParamV2.getZip(), addressParamV2.getTown());
    }
}
