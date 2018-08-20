package com.test.doctor.service.validator;

import java.util.Date;

import com.test.doctor.dto.ConsultaDTO;
import com.test.doctor.repository.ConsultaRepository;
import com.test.doctor.service.exception.RegistrationPermissionDeniedException;
import com.test.doctor.service.utils.UtilsData;

public class ConsultorioDisponivelParaMaisConsultasValidator extends ConsultaValidator {

	ConsultorioDisponivelParaMaisConsultasValidator(ConsultaDTO consulta, ConsultaRepository repository) {
		super(consulta, repository);
	}

	private int limiteDeConsultas = 12; // Limite de consultas diárias em um consultório

	@Override
	public void validar() {
		Date dataIni = UtilsData.dataComHoraInicial(consulta.getDataConsulta()).getData();
		Date dataFin = UtilsData.dataComHoraFinal(consulta.getDataConsulta()).getData();
		if (!(repository.numConsultasConsultorio(consulta.getConsultorio(), dataIni, dataFin) < limiteDeConsultas)) {
			throw new RegistrationPermissionDeniedException(
					"O número de consultas para esse consultório atingiu o limite de " + limiteDeConsultas
							+ " consultas diárias.");
		}
	}

}
