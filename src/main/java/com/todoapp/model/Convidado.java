package com.todoapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "td_convidado")
public class Convidado extends GenericModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 100)
	@NotEmpty
	private String email;

	@Column(nullable = false, length = 20)
	@NotEmpty
	private String telefone;

	@Column(nullable = false, length = 150)
	@NotEmpty
	private String nome;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Evento evento;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

}
