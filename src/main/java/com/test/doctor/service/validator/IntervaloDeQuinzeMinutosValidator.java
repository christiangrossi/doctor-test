package com.test.doctor.service.validator;

import java.util.Calendar;
import java.util.Date;

import com.test.doctor.dto.ConsultaDTO;
import com.test.doctor.repository.ConsultaRepository;
import com.test.doctor.service.exception.RegistrationPermissionDeniedException;

public class IntervaloDeQuinzeMinutosValidator extends ConsultaValidator {

	IntervaloDeQuinzeMinutosValidator(ConsultaDTO consulta, ConsultaRepository repository) {
		super(consulta, repository);
	}

	private int intervaloEntreConsultas = 15; // intervalo de tempo entre consultas em um consultório

	@Override
	public void validar() {
		Calendar quinzeMinutosDepois = Calendar.getInstance();
		Calendar quinzeMinutosAntes = Calendar.getInstance();

		quinzeMinutosAntes.setTime(new Date(consulta.getDataConsulta().getTime()));
		quinzeMinutosDepois.setTime(new Date(consulta.getDataConsulta().getTime()));
		quinzeMinutosAntes.add(Calendar.MINUTE, -intervaloEntreConsultas);
		quinzeMinutosDepois.add(Calendar.MINUTE, intervaloEntreConsultas);

		if (!repository.encontrarConsultasEntreIntervalos(quinzeMinutosAntes.getTime(), quinzeMinutosDepois.getTime(),
				consulta.getConsultorio()).isEmpty()) {
			throw new RegistrationPermissionDeniedException("As consultas devem ter invervalos de 15 min");
		}
	}

}
