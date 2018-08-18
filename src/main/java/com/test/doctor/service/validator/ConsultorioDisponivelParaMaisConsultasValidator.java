package com.test.doctor.service.validator;

import java.util.Date;

import com.test.doctor.model.Consulta;
import com.test.doctor.repository.ConsultaRepository;
import com.test.doctor.service.exception.RegistrationPermissionDeniedException;
import com.test.doctor.service.utils.UtilsData;

public class ConsultorioDisponivelParaMaisConsultasValidator implements ConsultaValidator{

	private int limiteDeConsultas = 12; // Limite de consultas diárias em um consultório
	private ConsultaValidator proximo;

	@Override
	public void validar(Consulta consulta, ConsultaRepository repository) {
		Date dataIni = UtilsData.dataComHoraInicial(consulta.getDataConsulta()).getData();
		Date dataFin = UtilsData.dataComHoraFinal(consulta.getDataConsulta()).getData();
		if (!(repository.numConsultasConsultorio(consulta.getConsultorio(), dataIni, dataFin) < limiteDeConsultas)) {
			throw new RegistrationPermissionDeniedException(
					"O número de consultas para esse consultório atingiu o limite de " + limiteDeConsultas
							+ " consultas diárias.");
		}
		proximo.validar(consulta, repository);
	}

	@Override
	public void setProximo(ConsultaValidator proximo) {
		this.proximo = proximo;
		
	}

}
