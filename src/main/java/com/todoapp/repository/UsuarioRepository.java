package com.todoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.todoapp.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{

	Usuario findByLogin(String login);
}