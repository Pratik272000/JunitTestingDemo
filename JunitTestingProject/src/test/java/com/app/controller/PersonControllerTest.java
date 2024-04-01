package com.app.controller;

import com.app.dao.PersonDao;
import com.app.dto.AddressDto;
import com.app.dto.PersonDto;
import com.app.entities.Address;
import com.app.entities.Person;
import com.app.service.PersonService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class PersonControllerTest {

    @Mock
    PersonService personService;
    @Mock
    PersonDao personDao;
    @InjectMocks
    PersonController personController;
    private MockMvc mockMvc;
    ObjectMapper mapper=new ObjectMapper();
    com.fasterxml.jackson.databind.ObjectWriter objectWriter=mapper.writer();
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    void getPersonById() throws Exception {
        PersonDto person=new PersonDto(1,"Pratik","Male","932094208");
        when(personService.getPersonById(person.getId())).thenReturn(Optional.of(person));
        mockMvc.perform(MockMvcRequestBuilders.get("/person/get/{id}",1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Pratik"));
    }

    @Test
    void getAllPersonList() throws Exception {
        PersonDto p1=new PersonDto(1,"Pratik","Male","932094208");
        PersonDto p2=new PersonDto(2,"Shubham","Male","932094208");
        PersonDto p3=new PersonDto(3,"Sahil","Male","932094208");
        PersonDto p4=new PersonDto(4,"Vaibhav","Male","932094208");
        when(personService.getAllPersonList()).thenReturn(new ArrayList<>(Arrays.asList(p1,p2,p3,p4)));
        mockMvc.perform(MockMvcRequestBuilders.get("/person/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Pratik"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].contact").value("932094208"));

    }

    @Test
    void addPerson() throws Exception{
        PersonDto p1=new PersonDto(1,"Pratik","Male","932094208");
        when(personService.addPerson(any(PersonDto.class))).thenReturn(p1);
        mockMvc.perform(MockMvcRequestBuilders.post("/person/add").content(mapper.writeValueAsString(p1)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Pratik"));
        verify(personService).addPerson(any(PersonDto.class));
    }

    @Test
    void updatePerson() throws Exception{
        PersonDto p1=new PersonDto(1,"Pratik","Male","932094208");
//        when(personDao.findById(1)).thenReturn(Optional.of(new Person(1, "Pratik", "Male", "932094208")));
        when(personService.updatePersonDetails(eq(1),any(PersonDto.class))).thenReturn(ResponseEntity.ok(p1));
        mockMvc.perform(MockMvcRequestBuilders.put("/person/update/1").content(mapper.writeValueAsString(p1)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.name").value("Pratik"));

    }

    @Test
    void deletePerson() throws Exception{
        String response="Person deleted Succefully";
        when(personService.deletePersonById(1)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.delete("/person/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(response));

    }

    @Test
    void addPersonAddress() throws Exception{
        AddressDto addressDto=new AddressDto(1,"Pune","Pune","892322");
        when(personService.addPersonAddress(eq(1),any(AddressDto.class))).thenReturn(ResponseEntity.ok(addressDto));
        mockMvc.perform(MockMvcRequestBuilders.post("/person/addAddress/{id}",1).content(mapper.writeValueAsString(addressDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.city").value("Pune"));

    }
}