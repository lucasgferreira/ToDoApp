package com.todoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.todoapp.model.Convidado;
import com.todoapp.model.Evento;

public interface ConvidadoRepository extends CrudRepository<Convidado, String>{
	Iterable<Convidado> findByEvento(Evento evento);
	Convidado findByCodigo(Long codigo);
}
