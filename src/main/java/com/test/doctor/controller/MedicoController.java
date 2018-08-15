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

import com.test.doctor.model.Medico;
import com.test.doctor.service.MedicoService;

@RestController
@RequestMapping(value = "/medicos")
public class MedicoController {

	@Autowired
	private MedicoService service;

	@GetMapping(value = "/")
	public ResponseEntity<List<Medico>> listar() {
		return ResponseEntity.ok(service.list());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Medico> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(service.find(id));
	}
	
	@PostMapping(value = "/{id}")
	public ResponseEntity<Medico> cadastrar(@RequestBody Medico obj) {
		return new ResponseEntity<Medico>(service.insert(obj), HttpStatus.CREATED);
	}
}
