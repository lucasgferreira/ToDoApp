package com.todoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.todoapp.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByRole(String role);
}
