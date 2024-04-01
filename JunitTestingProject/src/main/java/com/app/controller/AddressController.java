package com.app.controller;

import com.app.dto.AddressDto;
import com.app.service.AddressService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    AddressService addressService;
    @GetMapping("/get/{id}")
    public ResponseEntity getAllAdreessOfPerson(@PathVariable int id){
        return addressService.getPersonAddress(id);
    }
    @GetMapping("/all")
    public ResponseEntity getAllAddress(){
        return addressService.getAllAddressList();
    }
    @PostMapping("/add/{id}")
    public ResponseEntity addPersonAddressById(@PathVariable int id,@RequestBody AddressDto addressDto){
        return addressService.addAddressById(id,addressDto);
    }
}
