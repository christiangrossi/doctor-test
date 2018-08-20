package com.test.doctor.service.validator;

import com.test.doctor.dto.ConsultaDTO;
import com.test.doctor.repository.ConsultaRepository;

public class ValidadorDeConsulta {
	private IntervaloDeQuinzeMinutosValidator intervaloDeQuinzeMinutosValidator;
	private PacienteJaTemConsultaParaADataValidator pacienteJaTemConsultaParaADataValidator;
	private ConsultorioDisponivelParaMaisConsultasValidator consultorioDisponivelParaMaisConsultasValidator;
	private ReservaDeConsultorioValidator reservaDeConsultorioValidator;

	public ValidadorDeConsulta(ConsultaDTO consultaDTO, ConsultaRepository repository){
		intervaloDeQuinzeMinutosValidator = new IntervaloDeQuinzeMinutosValidator(consultaDTO, repository );
		pacienteJaTemConsultaParaADataValidator = new PacienteJaTemConsultaParaADataValidator(consultaDTO, repository);
		consultorioDisponivelParaMaisConsultasValidator = new ConsultorioDisponivelParaMaisConsultasValidator(consultaDTO, repository);
		reservaDeConsultorioValidator = new ReservaDeConsultorioValidator(consultaDTO, repository );
	}
	
	public void aplicarRegras() {
		intervaloDeQuinzeMinutosValidator.validar();
		pacienteJaTemConsultaParaADataValidator.validar();
		consultorioDisponivelParaMaisConsultasValidator.validar();
		reservaDeConsultorioValidator.validar();
	}
}
