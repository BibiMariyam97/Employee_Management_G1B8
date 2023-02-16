package com.employee.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.model.Employee;
import com.employee.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;

	@PostMapping("/createEmployee")

	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee, Authentication auth) {

		return ResponseEntity.status(HttpStatus.CREATED).body((employeeService.createEmployee(employee)));
	}

	@GetMapping("/listAllEmployee")
	public List<Employee> getAllEmployees() {
		List<Employee> employees = employeeService.findAllEmployees();
		return employees;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int id) {
		return ResponseEntity.ok().body(employeeService.findEmployeeById(id).get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") int id, @RequestBody Employee employee) {
		return ResponseEntity.ok().body(employeeService.updateEmployee(employee, id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") int id) {
		employeeService.deleteEmployee(id);
		return new ResponseEntity<>("User ID " + id + " has been successfully deleted!", HttpStatus.OK);
	}

	@GetMapping("/search/{firstname}")
	public ResponseEntity<List<Employee>> getEmployeeFirstname(@PathVariable String firstname) {
		List<Employee> emp = this.employeeService.findByFirstname(firstname);

		return ResponseEntity.ok().body(emp);

	}
	/*
	//this sort method is of hardcode,we need to alternate change the default value to asc or desc order
	@GetMapping("/sort")
	public ResponseEntity<List<Employee>> getAllEmployee(
			@RequestParam(value = "sortBy", defaultValue = "firstname", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		List<Employee> allEmp = this.employeeService.findAllEmployees(sortBy, sortDir);
		return new ResponseEntity<List<Employee>>(allEmp, HttpStatus.OK);
	}
	*/

	//this sort method 
	@GetMapping("/sort")
	public List<Employee> getAllEmployeesAndSort(@RequestParam("order") String order) {
		List<Employee> employees = employeeService.findAllEmployees();
		if ("asc".equalsIgnoreCase(order)) {
			Collections.sort(employees, new Comparator<Employee>() {

				public int compare(Employee o1, Employee o2) {
					return o1.getFirstname().compareTo(o2.getFirstname());
				}
			});
		} else if ("desc".equalsIgnoreCase(order)) {
			Collections.sort(employees, new Comparator<Employee>() {

				public int compare(Employee o1, Employee o2) {
					return o2.getFirstname().compareTo(o1.getFirstname());
				}
			});
		}
		return employees;
	}

}
