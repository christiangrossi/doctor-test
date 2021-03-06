package com.test.doctor.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.doctor.dto.ConsultaDTO;
import com.test.doctor.model.Consulta;
import com.test.doctor.service.ConsultaService;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/consultas")
public class ConsultaController {

	@Autowired
	private ConsultaService service;

	@GetMapping(value = "")
	public ResponseEntity<List<ConsultaDTO>> listar() {
		List<Consulta> consultas = service.list();
		List<ConsultaDTO> consultasDTO = consultas.stream().map(c -> new ConsultaDTO(c)).collect(Collectors.toList());
		return ResponseEntity.ok(consultasDTO);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Consulta> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(service.find(id));
	}

	@PostMapping()
	public ResponseEntity<ConsultaDTO> cadastrar(@Valid @RequestBody ConsultaDTO obj) {
		return new ResponseEntity<ConsultaDTO>(
				new ConsultaDTO(service.insert(ConsultaDTO.toConsulta(obj))), HttpStatus.CREATED);
	}
}
