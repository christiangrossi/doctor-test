package com.test.doctor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.test.doctor.dto.ConsultaDTO;
import com.test.doctor.model.Consulta;
import com.test.doctor.repository.ConsultaRepository;
import com.test.doctor.service.exception.DataIntegrityException;
import com.test.doctor.service.exception.ObjectNotFoundException;
import com.test.doctor.service.validator.ValidadorDeConsulta;

@Service
public class ConsultaService {
	@Autowired
	private ConsultaRepository repository;
	public List<Consulta> list() {
		return repository.findAllByOrderByDataConsultaDesc();
	}

	public Consulta find(Integer id) {
		Optional<Consulta> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Consulta.class.getName()));

	}

	public Consulta insert(Consulta obj) {
			new ValidadorDeConsulta(new ConsultaDTO(obj), repository).aplicarRegras();
			return repository.save(obj);
	}

	public Consulta update(Consulta obj) {
		return repository.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		try {
		repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um médico com consultas associadas");
		}
	}

}
