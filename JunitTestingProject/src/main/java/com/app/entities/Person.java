package com.app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String gender;
    String contact;
    @OneToMany(mappedBy = "person",cascade = CascadeType.ALL)
    List<Address> address;

    public Person(int id, String name, String gender, String contact) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.contact = contact;
    }
}
