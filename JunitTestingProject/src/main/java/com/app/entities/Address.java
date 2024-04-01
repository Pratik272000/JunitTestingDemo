package com.app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String city;
    String lane;
    String pincode;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    Person person;

    public Address(int id, String city, String lane, String pincode) {
        this.id = id;
        this.city = city;
        this.lane = lane;
        this.pincode = pincode;
    }
}
