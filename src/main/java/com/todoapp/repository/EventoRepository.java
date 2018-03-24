package com.todoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.todoapp.model.Evento;

public interface EventoRepository extends CrudRepository<Evento, String>{
	Evento findByCodigo(Long codigo);
}
