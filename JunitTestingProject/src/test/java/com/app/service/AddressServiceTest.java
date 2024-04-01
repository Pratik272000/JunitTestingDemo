package com.app.service;

import com.app.dao.AddressDao;
import com.app.dao.PersonDao;
import com.app.dto.AddressDto;
import com.app.entities.Address;
import com.app.entities.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    AddressDao addressDao;
    @Mock
    PersonDao personDao;
    @InjectMocks
    AddressService addressService;
    @Test
    void getPersonAddress() {
        Person person=new Person(1,"Pratik","Male","2834082");
        Address address=new Address(1,"Pune","Pune","2093213");
        person.setAddress(new ArrayList<>(Arrays.asList(address)));
        when(personDao.findById(1)).thenReturn(Optional.of(person));
        ResponseEntity result = addressService.getPersonAddress(1);
        assertNotNull(result);
        assertNotEquals(result,"not found");
        assertEquals(((List<Address>)result.getBody()).get(0).getCity(),"Pune");

    }

    @Test
    void getAllAddressList() {
        Address a1=new Address(1,"Pune","Pune","2093213");
        Address a2=new Address(2,"Mumbai","Mumbai","2093213");
        Address a3=new Address(3,"Kolkata","Kolkata","2093213");
        when(addressDao.findAll()).thenReturn(new ArrayList<>(Arrays.asList(a1,a2,a3)));
        ResponseEntity result = addressService.getAllAddressList();
        assertNotNull(result);
        assertEquals(((List<Address>)result.getBody()).size(),3);
        verify(addressDao).findAll();

    }

    @Test
    void addAddressById() {
        Person person = new Person(1, "Pratik", "Male", "2834082");
        when(personDao.findById(1)).thenReturn(Optional.of(person));

        Address address = new Address(1, "Pune", "Pune", "2093213");
        address.setPerson(person);

        // Ensure that the Person object passed to save() matches exactly with the one in the stubbing configuration
        when(addressDao.save(argThat(savedAddress -> savedAddress.getPerson().equals(person)))).thenReturn(address);

        ResponseEntity<AddressDto> responseEntity = addressService.addAddressById(1, new AddressDto(1, "Pune", "Pune", "2093213"));
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getBody().getCity(), "Pune");
    }
}