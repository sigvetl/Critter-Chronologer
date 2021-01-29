package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.pet.PetType;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Pet {

    @Id
    @GeneratedValue
    Long id;

    PetType petType;

    @Nationalized
    String name;

    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY)
    Customer customer;

    LocalDate birthDate;

    @Nationalized
    String notes;

    public Pet(){}

    public Pet(PetType petType, String name, Customer customer, LocalDate birthDate){
        this.petType = petType;
        this.name = name;
        this.customer = customer;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public PetType getPetType() {
        return petType;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getNotes() {
        return notes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
