package com.mindhaq.apiversions.web;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mindhaq.apiversions.domain.Hello;

@Controller
public class HelloController {

    private enum ValidVersion {
        v1,
        v2
    }

    @ResponseBody
    @RequestMapping(value = "/apiurl/{version}/hello", method = GET, produces = APPLICATION_JSON_VALUE)
    public Hello sayHelloWorldUrl(@PathVariable final ValidVersion version) {
        return new Hello();
    }

    @ResponseBody
    @RequestMapping(value = "/apiheader/hello", method = GET, produces = APPLICATION_JSON_VALUE)
    public Hello sayHelloWorldHeader(@RequestHeader("X-API-Version") final ValidVersion version) {
        return new Hello();
    }

    @ResponseBody
    @RequestMapping(
        value = "/apiaccept/hello", method = GET,
        produces = {"application/vnd.company.app-v1+json", "application/vnd.company.app-v2+json"}
    )
    public Hello sayHelloWorldAccept() {
        return new Hello();
    }
}
