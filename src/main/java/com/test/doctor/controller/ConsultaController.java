package com.test.doctor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.doctor.model.Consulta;
import com.test.doctor.service.ConsultaService;

@RestController
@RequestMapping(value = "/consultas")
public class ConsultaController {

	@Autowired
	private ConsultaService service;

	@GetMapping(value = "")
	public ResponseEntity<List<Consulta>> listar() {
		return ResponseEntity.ok(service.list());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Consulta> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(service.find(id));
	}

	@PostMapping()
	public ResponseEntity<Consulta> cadastrar(@RequestBody Consulta obj) {
		//System.err.println("Data da consulta enviada:");
		//System.err.println(obj.getDataConsulta());
		return new ResponseEntity<Consulta>(service.insert(obj), HttpStatus.CREATED);
	}
}
