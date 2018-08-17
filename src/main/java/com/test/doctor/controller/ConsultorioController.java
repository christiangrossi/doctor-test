package com.test.doctor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.doctor.model.Consultorio;
import com.test.doctor.service.ConsultorioService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/consultorios")
public class ConsultorioController {

	@Autowired
	private ConsultorioService service;

	@GetMapping()
	public ResponseEntity<List<Consultorio>> listar() {
		return ResponseEntity.ok(service.list());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Consultorio> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(service.find(id));
	}
	
	@PostMapping(value = "/{id}")
	public ResponseEntity<Consultorio> cadastrar(@RequestBody Consultorio obj) {
		return new ResponseEntity<Consultorio>(service.insert(obj), HttpStatus.CREATED);
	}
}
