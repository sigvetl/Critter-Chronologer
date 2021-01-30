package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Pet savePet(Pet pet, Long customerId){
        Customer c = customerRepository.getOne(customerId); //get customer
        pet.setCustomer(c); //set customer for pet

        //pet needs to be persisted before added to customer
        Pet savedPet = petRepository.save(pet); //save pet
        c.addPet(savedPet); //set pet for customer
        customerRepository.save(c); //save customer
        return savedPet;
    }

    public Pet findPet(Long id){
        Optional<Pet> pet = Optional.of(petRepository.getOne(id));
        return pet.orElse(null);
    }

    public List<Pet> findPets(){
        return petRepository.findAll();
    }

    public List<Pet> findPetsById(List<Long> ids){
        return petRepository.findAllById(ids);
    }

    public List<Pet> findPetsByOwner(long ownerId){
        Optional<Customer> c = Optional.of(customerRepository.getOne(ownerId));
        return c.get().getPets();
    }
}
