package com.age.springbootbackendapirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.age.springbootbackendapirest.models.entity.Cliente;
import com.age.springbootbackendapirest.models.services.IClienteService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/clientes")
	public List<Cliente> list(){
		return clienteService.findAll();
	}
	
	@GetMapping("/clientes/id")
	public Cliente show(@PathVariable Long id) {
		return clienteService.findById(id);
	}
	
	@PostMapping("/clientes")
	public Cliente create(Cliente cliente) {
		return clienteService.save(cliente);
	}

	@DeleteMapping("/clientes/id")
	public void delete(@PathVariable Long id) {
		 clienteService.delete(id);
	}
}
