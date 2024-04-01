package com.app.service;

import com.app.dao.PersonDao;
import com.app.dto.AddressDto;
import com.app.dto.PersonDto;
import com.app.entities.Address;
import com.app.entities.Person;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService {
    @Autowired
    PersonDao personDao;
    public PersonDto addPerson(PersonDto pd){
        Person person=new Person(pd.getId(),pd.getName(),pd.getGender(),pd.getContact());
        Person p=personDao.save(person);
        return personToPersonDtoConverter(p);
    }
    public Optional<PersonDto> getPersonById(int id){
        Optional<Person> p=personDao.findById(id);
        return Optional.of(personToPersonDtoConverter(p.get()));
    }
    public List<PersonDto> getAllPersonList(){
        List<Person> list=personDao.findAll();
        List<PersonDto> result=new ArrayList<>();
        for(Person p:list){
            result.add(personToPersonDtoConverter(p));
        }
        return result;
    }
    public String deletePersonById(int id){
        Optional<Person> person=personDao.findById(id);
        if(!person.isEmpty()){
            personDao.delete(person.get());
            return "Person deleted succefully";
        }else{
            return "Person not found";
        }
    }
    public ResponseEntity updatePersonDetails(int id, PersonDto p){
        Optional<Person> op=personDao.findById(id);
        if(op.isEmpty())
            return ResponseEntity.ok("Person Not found");
        op.get().setId(p.getId());
        op.get().setName(p.getName());
        op.get().setGender(p.getGender());
        op.get().setContact(p.getContact());
       Person result= personDao.save(op.get());
       return ResponseEntity.ok(personToPersonDtoConverter(result));

    }

    public ResponseEntity addPersonAddress(int id,AddressDto a){
        Optional<Person> person=personDao.findById(id);
        if(person.isEmpty())
            return ResponseEntity.ok("Person not found");
        Address address=new Address(a.getId(),a.getCity(),a.getLane(),a.getPincode());
        person.get().setAddress(new ArrayList<>(Arrays.asList(address)));
        personDao.save(person.get());
        return ResponseEntity.ok(a);
    }

    private PersonDto personToPersonDtoConverter(Person p) {
        return new PersonDto(p.getId(),p.getName(),p.getGender(),p.getContact());
    }


}
