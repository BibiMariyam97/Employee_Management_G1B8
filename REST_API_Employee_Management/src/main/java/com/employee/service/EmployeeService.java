package com.employee.service;

import java.util.List;
import java.util.Optional;

import com.employee.model.Employee;

public interface EmployeeService {

	Employee createEmployee(Employee employee);

	Employee updateEmployee(Employee employee, int id);

	Object deleteEmployee(int id);

	Optional<Employee> findEmployeeById(int id);

	List<Employee> findByFirstname(String firstname);

	List<Employee> findAllEmployees();

	List<Employee> findAllEmployees(String sortBy, String sortDir);

}