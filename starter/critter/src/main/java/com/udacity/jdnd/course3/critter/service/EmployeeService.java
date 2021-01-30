package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee save(Employee e){
        return employeeRepository.save(e);
    }

    public Employee findEmployeeById(long id){
        Optional<Employee> e = employeeRepository.findById(id);
        return e.orElse(null);
    }

    public List<Employee> findEmployeesById(List<Long> ids){
        return employeeRepository.findAllById(ids);
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }




}
