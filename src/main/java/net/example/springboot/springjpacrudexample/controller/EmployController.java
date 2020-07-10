package net.example.springboot.springjpacrudexample.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.example.springboot.springjpacrudexample.exception.ResourceNotFoundException;
import net.example.springboot.springjpacrudexample.model.Employee;
import net.example.springboot.springjpacrudexample.repository.EmployeeRepository;

//To enable CORS on the server, add a @CrossOrigin annotation to the EmployeeController
@RestController 
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/api/v1")
public class EmployController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(path="/employees")
    public List<Employee> getAllEmployees(){
       return  employeeRepository.findAll();
    }
   
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeByid(@PathVariable(value = "id") Long employeeId) throws ResourceNotFoundException{

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
            () -> new ResourceNotFoundException("Employee not found with this ID :: "+employeeId));
        return ResponseEntity.ok().body(employee);

    }

    @PostMapping("/employees")
    public Employee createEmployee(@Valid @RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId, 
        @Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException{

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
            () -> new ResourceNotFoundException("Employee not found with this ID :: "+employeeId));

        employee.setEmailId(employeeDetails.getEmailId());
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);        

    }

    @DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId) throws ResourceNotFoundException{

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
            () -> new ResourceNotFoundException("Employee not found with this ID :: "+employeeId));
        
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;

    }
}