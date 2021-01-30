package com.udacity.jdnd.course3.critter.entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    long id;

    @Nationalized
    String name;

    //Limit length
    String phoneNumber;

    @Nationalized
    String notes;

    @OneToMany(mappedBy = "customer", targetEntity = Pet.class ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Pet> pets;

    public void addPet(Pet pet){
        if (this.pets == null){
            pets = new ArrayList<>();
        }
        pets.add(pet);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
