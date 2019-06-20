package com.age.springbootbackendapirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.age.springbootbackendapirest.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{

}
