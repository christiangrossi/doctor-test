package com.test.doctor.service.validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.test.doctor.model.Consulta;
import com.test.doctor.repository.ConsultaRepository;


public  interface ConsultaValidator {
	@Autowired
	public void validar(Consulta consulta, ConsultaRepository repository);
	
	public void setProximo(ConsultaValidator proximo);
	
}
