package com.employee.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public Employee createEmployee(Employee employee) {

		return employeeRepository.save(employee);
	}

	@Override
	public Employee updateEmployee(Employee employee, int id) {
		Optional<Employee> emp = employeeRepository.findById(employee.getId());
		Employee e = emp.get();
		e.setId(employee.getId());
		e.setEmail(employee.getEmail());
		e.setFirstname(employee.getFirstname());
		e.setLastname(employee.getLastname());

		return employeeRepository.save(e);
	}

	@Override
	public Object deleteEmployee(int id) {

		this.employeeRepository.deleteById(id);
		return "Employee SuccessFully Deleted : id";

	}

	@Override
	public Optional<Employee> findEmployeeById(int id) {

		return employeeRepository.findById(id);
	}

	@Override
	public List<Employee> findAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public List<Employee> findByFirstname(String firstname) {

		return employeeRepository.findByFirstnameContainsAllIgnoreCase(firstname);
	}

	@Override
	public List<Employee> findAllEmployees(String sortBy, String sortDir) {
		Sort sort = null;
		if (sortDir.trim().toLowerCase().equals("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}
		Pageable pageable = PageRequest.of(0, 10, sort);
		Page<Employee> allPages = this.employeeRepository.findAll(pageable);
		List<Employee> allEmployees = allPages.getContent();
		return allEmployees;
	}

}
