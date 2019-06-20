package com.age.springbootbackendapirest.models.services;

import java.util.List;

import com.age.springbootbackendapirest.models.entity.Cliente;

public interface IClienteService {
	
	public List<Cliente> findAll();
}
