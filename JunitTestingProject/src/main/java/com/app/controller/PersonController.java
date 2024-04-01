package com.app.controller;

import com.app.dto.AddressDto;
import com.app.dto.PersonDto;
import com.app.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonService personService;
    @GetMapping("/get/{id}")
    public ResponseEntity getPersonById(@PathVariable ("id")int id){
        return ResponseEntity.ok(personService.getPersonById(id));
    }
    @GetMapping("/all")
    public ResponseEntity getAllPersonList(){
        return ResponseEntity.ok(personService.getAllPersonList());
    }
    @PostMapping("/add")
    public ResponseEntity addPerson(@RequestBody PersonDto personDto){
        return ResponseEntity.ok(personService.addPerson(personDto));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updatePerson(@PathVariable("id")int id,@RequestBody PersonDto personDto){
        return ResponseEntity.ok(personService.updatePersonDetails(id,personDto));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePerson(@PathVariable ("id")int id){
        return ResponseEntity.ok(personService.deletePersonById(id));
    }
    @PostMapping("addAddress/{id}")
    public ResponseEntity addPersonAddress(@PathVariable int id,@RequestBody AddressDto addressDto){
      return   ResponseEntity.ok(personService.addPersonAddress(id,addressDto));
    }

}
