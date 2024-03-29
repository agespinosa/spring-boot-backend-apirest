package com.age.springbootbackendapirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="clientes")
public class Cliente implements Serializable{


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "no puede ser vacio")
	@Size(min = 4, max = 20, message = "El campo nombre debe tener entre 4 y 20 caracteres")
	@Column(nullable = false)
	private String nombre;
	
	@NotEmpty(message = " no puede ser vacio")
	private String apellido;
	
	@NotEmpty(message = "no puede ser vacio")
	@Email(message = "el email es invalido")
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(name="created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	

	public Cliente() {
		this.createdAt= new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
