package com.test.doctor.service.validator;

import com.test.doctor.dto.ConsultaDTO;
import com.test.doctor.repository.ConsultaRepository;


public  abstract class ConsultaValidator {
	
	protected ConsultaDTO consulta;
	protected ConsultaRepository repository;

	ConsultaValidator(ConsultaDTO consulta, ConsultaRepository repository) {
		this.consulta = consulta;
		this.repository = repository;
	}
	
	public abstract void validar();

}
