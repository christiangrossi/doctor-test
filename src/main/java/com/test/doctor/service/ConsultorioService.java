package com.test.doctor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.test.doctor.model.Consultorio;
import com.test.doctor.repository.ConsultorioRepository;
import com.test.doctor.service.exception.ObjectNotFoundException;

@Service
public class ConsultorioService {
	@Autowired
	private ConsultorioRepository repository;

	public List<Consultorio> list() {
		return repository.findAll();
	}

	public Consultorio find(Integer id) {
		Optional<Consultorio> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Consultorio.class.getName()));

	}

	public Consultorio insert(Consultorio obj) {
		return repository.save(obj);
	}

	public Consultorio update(Consultorio obj) {
		return repository.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível excluir um consultório com consultas associadas");
		}

	}

}
