package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer c = convertCDTOToC(customerDTO);
        return convertCToCDTO(customerService.save(c));
        //throw new UnsupportedOperationException();
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> c = customerService.findAllCustomers();
        List<CustomerDTO> cDTO = c.stream().map(this::convertCToCDTO).collect(Collectors.toList());
        return cDTO;
        //throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.findPet(petId);
        Customer c = customerService.findCustomer(pet.getCustomer().getId());
        return convertCToCDTO(c);
        //throw new UnsupportedOperationException();
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return convertEToEDTO(employeeService.save(convertEDTOToE(employeeDTO)));
        //throw new UnsupportedOperationException();
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEToEDTO(employeeService.findEmployeeById(employeeId));
        //throw new UnsupportedOperationException();
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee e = employeeService.findEmployeeById(employeeId);
        e.setDaysAvailable(daysAvailable);
        employeeService.save(e);
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    public CustomerDTO convertCToCDTO(Customer c){
        CustomerDTO cDTO = new CustomerDTO();
        BeanUtils.copyProperties(c, cDTO);
        return cDTO;
    }

    public Customer convertCDTOToC(CustomerDTO cDTO){
        Customer c = new Customer();
        BeanUtils.copyProperties(cDTO, c);
        return c;
    }

    public EmployeeDTO convertEToEDTO(Employee e){
        EmployeeDTO eDTO = new EmployeeDTO();
        BeanUtils.copyProperties(e, eDTO);
        return eDTO;
    }

    public Employee convertEDTOToE(EmployeeDTO eDTO){
        Employee e = new Employee();
        BeanUtils.copyProperties(eDTO, e);
        return e;
    }

}
