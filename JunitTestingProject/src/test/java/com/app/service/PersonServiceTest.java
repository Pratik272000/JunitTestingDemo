package com.app.service;

import com.app.dao.AddressDao;
import com.app.dao.PersonDao;
import com.app.dto.AddressDto;
import com.app.dto.PersonDto;
import com.app.entities.Address;
import com.app.entities.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @Mock
    PersonDao personDao;
    @Mock
    AddressDao addressDao;
    @InjectMocks
    PersonService personService;

    @Test
    void addPerson() {
        Person person=new Person(1,"Pratik","male","90134913");
        PersonDto p=new PersonDto(1,"Pratik","male","90134913");
        when(personDao.save(any(Person.class))).thenReturn(person);
        PersonDto result=personService.addPerson(p);
        assertNotNull(result);
        assertEquals(p.getName(),result.getName());
        verify(personDao).save(any(Person.class));
    }

    @Test
    void getPersonById() {
        Person person=new Person(1,"Pratik","male","90134913");
        when(personDao.findById(person.getId())).thenReturn(Optional.of(person));
        Optional<PersonDto> result=personService.getPersonById(1);
        assertNotNull(result);
        assertNotEquals(result.get().getName(),"Shubham");
    }

    @Test
    void getAllPersonList() {
        Person p1=new Person(1,"Pratik","male","90134913");
        Person p2=new Person(2,"Shubham","male","83821098");
        Person p3=new Person(1,"Manthan","male","804109321");
        List<Person> list=new ArrayList<>(Arrays.asList(p1,p2,p3));
        when(personDao.findAll()).thenReturn(list);
        List<PersonDto> result = personService.getAllPersonList();
        assertNotNull(result);
        assertEquals(result.size(),3);
        assertEquals(result.get(0).getName(),"Pratik");
        verify(personDao).findAll();


    }

    @Test
    void deletePersonById() {
        Person p1 = new Person(1, "Pratik", "male", "90134913");
 when(personDao.findById(1)).thenReturn(Optional.of(p1));
 String result = personService.deletePersonById(1);
 assertNotNull(result);
 verify(personDao).findById(1);
 verify(personDao).delete(any(Person.class));

    }

    @Test
    void updatePersonDetails() {
        Person p1 = new Person(1, "Pratik", "male", "90134913");
        PersonDto personDto=new PersonDto(1,"Pratik","male","p@gmail.com");
        when(personDao.findById(1)).thenReturn(Optional.of(p1));
        ResponseEntity result = personService.updatePersonDetails(1, personDto);
        assertNotNull(result);
       verify(personDao).save(any(Person.class));
    }


    @Test
    void addPersonAddress() {
        AddressDto addressDto=new AddressDto(1,"pune","pune","238403");

        Address address=new Address(1,"Pune","Pune","912032");
        Person person=new Person(1,"Pratik","male","90134913");

        when(personDao.findById(1)).thenReturn(Optional.of(person));
        person.setAddress(new ArrayList<>(Arrays.asList(address)));
        when(personDao.save(any(Person.class))).thenReturn(person);
        ResponseEntity result = personService.addPersonAddress(1, addressDto);
        assertNotNull(result);
        System.out.println(result);
        assertEquals(((AddressDto)result.getBody()).getCity(),"pune");
        verify(personDao).save(any(Person.class));

    }

}