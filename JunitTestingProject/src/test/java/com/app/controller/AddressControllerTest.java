package com.app.controller;

import com.app.dto.AddressDto;
import com.app.entities.Address;
import com.app.service.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {
    @Mock
    AddressService addressService;
    @InjectMocks
    AddressController addressController;
    private MockMvc mockMvc;
    ObjectMapper mapper=new ObjectMapper();
    com.fasterxml.jackson.databind.ObjectWriter objectWriter=mapper.writer();
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
    }

    @Test
    void getAllAdreessOfPerson() throws Exception {
        Address a1=new Address(1,"Pune","Pune","910239");
        Address a2=new Address(2,"Solapur","Solapur","283432");
        Address a3=new Address(1,"Mumbai","Mumbai","3432323");
        List<Address> list=new ArrayList<>(Arrays.asList(a1,a2,a3));
        when(addressService.getPersonAddress(1)).thenReturn(ResponseEntity.ok(list));
        mockMvc.perform(MockMvcRequestBuilders.get("/address/get/{id}",1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));

    }

    @Test
    void getAllAddress()throws Exception {
        Address a1=new Address(1,"Pune","Pune","910239");
        Address a2=new Address(2,"Solapur","Solapur","283432");
        Address a3=new Address(1,"Mumbai","Mumbai","3432323");
        List<Address> list=new ArrayList<>(Arrays.asList(a1,a2,a3));
        when(addressService.getAllAddressList()).thenReturn(ResponseEntity.ok(list));
        mockMvc.perform(MockMvcRequestBuilders.get("/address/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));

    }

    @Test
    void addPersonAddressById()throws Exception {
        AddressDto addressDto=new AddressDto(1,"Pune","Pune","328498");
        when(addressService.addAddressById(eq(1),any(AddressDto.class))).thenReturn(ResponseEntity.ok(addressDto));
        mockMvc.perform(MockMvcRequestBuilders.post("/address/add/{id}",1).content(mapper.writeValueAsString(addressDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Pune"));
    }
}