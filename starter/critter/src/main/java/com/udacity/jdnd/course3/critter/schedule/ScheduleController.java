package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO);
        return convertScheduleToScheduleDTO(scheduleService.saveSchedule(schedule));
        //throw new UnsupportedOperationException();
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules().stream().map(this::convertScheduleToScheduleDTO).collect(Collectors.toList());
        //throw new UnsupportedOperationException();
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> scheduleMatch = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getAllSchedules();
        for (Schedule schedule : schedules){
            if (schedule.getPets().contains(petService.findPet(petId))){
                scheduleMatch.add(schedule);
            }
        }
        return scheduleMatch.stream().map(this::convertScheduleToScheduleDTO).collect(Collectors.toList());
        //throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> scheduleMatch = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getAllSchedules();
        for (Schedule schedule : schedules){
            if (schedule.getEmployees().contains(employeeService.findEmployeeById(employeeId))){
                scheduleMatch.add(schedule);
            }
        }
        return scheduleMatch.stream().map(this::convertScheduleToScheduleDTO).collect(Collectors.toList());
        //throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> scheduleMatch = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getAllSchedules();
        Customer customer = customerService.findCustomer(customerId);
        List<Pet> pets = customer.getPets();
        for (Schedule schedule : schedules){
            for (Pet pet : pets){
                if (schedule.getPets().contains(pet)){
                    scheduleMatch.add(schedule);
                }
            }
        }
        return scheduleMatch.stream().map(this::convertScheduleToScheduleDTO).collect(Collectors.toList());
        //throw new UnsupportedOperationException();
    }

    Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        if (scheduleDTO.getPetIds() != null){
            schedule.setPets(petService.findPetsById(scheduleDTO.getPetIds()));
        }
        if (scheduleDTO.getEmployeeIds() != null){
            schedule.setEmployees(employeeService.findEmployeesById(scheduleDTO.getEmployeeIds()));
        }
        return schedule;
    }

    ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        if (schedule.getPets() != null){
            scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        }
        if (schedule.getEmployees() != null){
            scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        }
        return scheduleDTO;
    }
}
