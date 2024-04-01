package com.app.service;

import com.app.dao.AddressDao;
import com.app.dao.PersonDao;
import com.app.dto.AddressDto;
import com.app.entities.Address;
import com.app.entities.Person;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AddressService {
    @Autowired
    AddressDao addressDao;
    @Autowired
    PersonDao personDao;

   public ResponseEntity getPersonAddress(int id){
       Optional<Person> person=personDao.findById(id);
       if(person.isEmpty())
           return ResponseEntity.ok("Person not found");
       if(person.get().getAddress().isEmpty())
           return ResponseEntity.ok("Person has not added any address,please add address");
       return ResponseEntity.ok(person.get().getAddress());
   }
   public ResponseEntity getAllAddressList(){
       HttpHeaders headers = new HttpHeaders();
       headers.add("Custom-Header", "Pratik");
       List<Address> list=addressDao.findAll();
       List<AddressDto> result=new ArrayList<>();
       for(Address a:list){
           result.add(new AddressDto(a.getId(),a.getCity(),a.getLane(),a.getPincode()));
       }
       return ResponseEntity.ok().headers(headers).body(result);
   }
   public ResponseEntity addAddressById(int id,AddressDto a){
       Optional<Person> person=personDao.findById(id);
       if(person.isEmpty())
           return ResponseEntity.ok("Person not found");
       Address address=new Address(a.getId(),a.getCity(),a.getLane(),a.getPincode());
       address.setPerson(person.get());
      addressDao.save(address);
       return ResponseEntity.ok(a);
   }



}
