package com.test.doctor.service.validator;

import com.test.doctor.model.Consulta;
import com.test.doctor.repository.ConsultaRepository;

public class FimValidacao implements ConsultaValidator {

	@Override
	public void validar(Consulta consulta, ConsultaRepository repository) {
	}

	@Override
	public void setProximo(ConsultaValidator proximo) {
	}

}
