package com.test.doctor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.test.doctor.model.Medico;
import com.test.doctor.repository.MedicoRepository;
import com.test.doctor.service.exception.ObjectNotFoundException;

@Service
public class MedicoService {
	@Autowired
	private MedicoRepository repository;

	public List<Medico> list() {
		return repository.findAll();
	}

	public Medico find(Integer id) {
		Optional<Medico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Medico.class.getName()));

	}

	public Medico insert(Medico obj) {
		return repository.save(obj);
	}

	public Medico update(Medico obj) {
		return repository.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível excluir um médico com consultas associadas");
		}

	}

}
