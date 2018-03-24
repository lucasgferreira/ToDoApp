package com.todoapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "td_evento")
public class Evento extends GenericModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 100)
	@NotEmpty
	private String nome;

	@Column(nullable = false, length = 150)
	@NotEmpty
	private String local;

	@Column(nullable = false, length = 10)
	@NotEmpty
	private String data;

	@Column(nullable = false, length = 5)
	@NotEmpty
	private String horario;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

}
