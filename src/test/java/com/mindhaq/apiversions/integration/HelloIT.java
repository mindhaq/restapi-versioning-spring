package com.mindhaq.apiversions.integration;

import static org.hamcrest.Matchers.is;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.web.context.WebApplicationContext;

import com.mindhaq.apiversions.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HelloIT {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void initMockMvc() {
        this.mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void should_respond_with_hello_json_on_v1_url() throws Exception {
        mockMvc.perform(get("/apiurl/v1/hello").accept(APPLICATION_JSON)) //
               .andExpect(status().isOk())                                //
               .andExpect(content().contentType(APPLICATION_JSON))        //
               .andExpect(jsonPath("$.hello", is("world")));
    }

    @Test
    public void should_respond_with_hello_json_on_v2_url() throws Exception {
        mockMvc.perform(get("/apiurl/v2/hello").accept(APPLICATION_JSON)) //
               .andExpect(status().isOk())                                //
               .andExpect(content().contentType(APPLICATION_JSON))        //
               .andExpect(jsonPath("$.hello", is("world")));
    }

    @Test
    public void should_respond_with_400_on_v3_url() throws Exception {
        mockMvc.perform(get("/apiurl/v3/hello").accept(APPLICATION_JSON)) //
               .andExpect(status().isBadRequest());
    }

    /* ------------------------------------------------------------------- */

    @Test
    public void should_respond_with_hello_json_with_v1_header() throws Exception {
        mockMvc.perform(get("/apiheader/hello").accept(APPLICATION_JSON).header("X-API-Version", "v1")) //
               .andExpect(status().isOk())                                                              //
               .andExpect(content().contentType(APPLICATION_JSON))                                      //
               .andExpect(jsonPath("$.hello", is("world")));
    }

    @Test
    public void should_respond_with_hello_json_with_v2_header() throws Exception {
        mockMvc.perform(get("/apiheader/hello").accept(APPLICATION_JSON).header("X-API-Version", "v2")) //
               .andExpect(status().isOk())                                                              //
               .andExpect(content().contentType(APPLICATION_JSON))                                      //
               .andExpect(jsonPath("$.hello", is("world")));
    }

    @Test
    public void should_respond_with_bad_request_with_v3_header() throws Exception {
        mockMvc.perform(get("/apiheader/hello").accept(APPLICATION_JSON).header("X-API-Version", "v3")) //
               .andExpect(status().isBadRequest());
    }

    @Test
    public void should_respond_with_bad_request_without_version_header() throws Exception {
        mockMvc.perform(get("/apiheader/hello").accept(APPLICATION_JSON)) //
               .andExpect(status().isBadRequest());
    }

    /* ------------------------------------------------------------------- */

    @Test
    public void should_respond_with_hello_json_with_v1_accept() throws Exception {
        mockMvc.perform(get("/apiaccept/hello").accept("application/vnd.company.app-v1+json")) //
               .andExpect(status().isOk())                                                     //
               .andExpect(content().contentType("application/vnd.company.app-v1+json"))        //
               .andExpect(jsonPath("$.hello", is("world")));
    }

    @Test
    public void should_respond_with_hello_json_with_v2_accept() throws Exception {
        mockMvc.perform(get("/apiaccept/hello").accept("application/vnd.company.app-v2+json")) //
               .andExpect(status().isOk())                                                     //
               .andExpect(content().contentType("application/vnd.company.app-v2+json"))        //
               .andExpect(jsonPath("$.hello", is("world")));
    }

    @Test
    public void should_respond_with_not_acceptable_with_v3_accept() throws Exception {
        mockMvc.perform(get("/apiaccept/hello").accept("application/vnd.company.app-v3+json")) //
               .andExpect(status().isNotAcceptable());
    }

}
