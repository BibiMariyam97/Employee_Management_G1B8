package com.employee.serviceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.model.Role;
import com.employee.model.User;
import com.employee.repository.RoleRepository;
import com.employee.repository.UserRepository;
import com.employee.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Override
	public User addUser(User user) {

		List<Role> roles = user.getRoles();

		Set<Role> rolesFromRepo = new HashSet<Role>();
		for (Role role : roles) {
			Role roleUser = roleRepository.findById(role.getId()).get();
			rolesFromRepo.add(roleUser);
		}
		user.getRoles().clear();
		for (Role role : rolesFromRepo) {
			user.addRole(role);
		}

		return userRepository.save(user);
	}
}