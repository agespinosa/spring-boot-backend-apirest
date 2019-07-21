package com.age.springbootbackendapirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.age.springbootbackendapirest.models.entity.Cliente;

public interface IClienteDao extends JpaRepository<Cliente, Long>{

}
