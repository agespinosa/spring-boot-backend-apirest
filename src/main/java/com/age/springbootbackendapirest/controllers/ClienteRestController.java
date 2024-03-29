package com.age.springbootbackendapirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.age.springbootbackendapirest.models.entity.Cliente;
import com.age.springbootbackendapirest.models.services.IClienteService;

//@CrossOrigin(origins = {"http://localhost:4200"})
@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api")
public class ClienteRestController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/clientes")
	public List<Cliente> list(){
		return clienteService.findAll();
	}
	
	@GetMapping("/clientes/page/{page}")
	public Page<Cliente> list(@PathVariable Integer page){
		Pageable pageable= PageRequest.of(page, 10);
		return clienteService.findAll(pageable);
	}
	
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Cliente cliente= null;
		Map<String, Object> response= new HashMap<>();
		
		try {
			cliente= clienteService.findById(id);
			if(cliente==null) {
				response.put("mensaje", "No existe el cliente: ".concat(id.toString()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}else {
				response.put("mensaje", "Cliente encontrado");
				response.put("cliente", cliente);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Hubo un poblema al mostrar el cliente: ".concat(id.toString()));
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
						
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/clientes")
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {		
		Cliente clienteCreated= null;
		Map<String, Object> response= new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors= result.getFieldErrors()
					.stream()
					.map(err-> "El campo '"+ err.getField()+"' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			clienteCreated= clienteService.save(cliente);
			response.put("mensaje", "El cliente se grabo correctamente");
			response.put("cliente", clienteCreated);
		} catch (DataAccessException e) {
			response.put("mensaje", "Hubo un poblema al guardar el cliente: ".concat(cliente.getNombre()));
			response.put("error",e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result,  @PathVariable Long id) {
		Cliente clienteUpdated= null;
		Map<String, Object> response= new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors= result.getFieldErrors()
					.stream()
					.map(err-> "El campo '"+ err.getField()+"' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			clienteUpdated= clienteService.findById(id);
			if(clienteUpdated==null) {
				response.put("mensaje", "No existe el cliente: ".concat(id.toString()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}else {
				clienteUpdated.setApellido(cliente.getApellido());
				clienteUpdated.setEmail(cliente.getEmail());
				clienteUpdated.setNombre(cliente.getNombre());
				
				clienteService.save(clienteUpdated);
				
				response.put("mensaje", "Cliente actualizado con éxito!");
				response.put("cliente", clienteUpdated);
			}
		}catch (DataAccessException e) {
			response.put("mensaje", "Hubo un poblema al actualizar el cliente: ".concat(cliente.getNombre()));
			response.put("error",e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		 Map<String, Object> response= new HashMap<>();
		 try {
				clienteService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Hubo un poblema al eliminar el cliente: ".concat(id.toString()));
			response.put("error",e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		response.put("mensaje", "El cliente id : ".concat(id.toString().concat(" fue eliminado con éxito")));
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
