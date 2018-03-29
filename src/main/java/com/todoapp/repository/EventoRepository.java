package com.todoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.todoapp.model.Evento;
import com.todoapp.model.Usuario;

public interface EventoRepository extends CrudRepository<Evento, String>{
	Iterable<Evento> findByUsuario(Usuario usuario);
	Evento findByCodigo(Long codigo);
}
