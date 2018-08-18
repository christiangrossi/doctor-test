package com.test.doctor.service.validator;

import com.test.doctor.model.Consulta;
import com.test.doctor.repository.ConsultaRepository;

public class ValidadorDeConsulta {
	
	private Consulta consulta;
	private ConsultaRepository repository;

	public ValidadorDeConsulta(Consulta consulta, ConsultaRepository repository){
		this.consulta = consulta;
		this.repository = repository;
		
	}
	
	public void aplicarRegras() {
		ConsultaValidator v1 = new IntervaloDeQuinzeMinutosValidator();
		ConsultaValidator v2 = new PacienteJaTemConsultaParaADataValidator();
		ConsultaValidator v3 = new ConsultorioDisponivelParaMaisConsultasValidator();
		ConsultaValidator v4 = new ReservaDeConsultorioValidator();
		ConsultaValidator v5 = new FimValidacao();
		
		v1.setProximo(v2);
		v2.setProximo(v3);
		v3.setProximo(v4);
		v4.setProximo(v5);
		
		v1.validar(consulta, repository);
		
	}
}
