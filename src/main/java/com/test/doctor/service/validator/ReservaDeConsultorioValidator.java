package com.test.doctor.service.validator;

import java.util.Date;
import java.util.List;

import com.test.doctor.dto.ConsultaDTO;
import com.test.doctor.model.Consulta;
import com.test.doctor.repository.ConsultaRepository;
import com.test.doctor.service.utils.UtilsData;

public class ReservaDeConsultorioValidator extends ConsultaValidator {
	


	ReservaDeConsultorioValidator(ConsultaDTO consulta, ConsultaRepository repository) {
		super(consulta, repository);
	}

	@Override
	public void validar() {
		Date dataIni = UtilsData.dataComHoraInicial(consulta.getDataConsulta()).getData();
		Date dataFin = UtilsData.dataComHoraFinal(consulta.getDataConsulta()).getData();
		List<Consulta> consultas = repository.encontrarConsultasEntreDuasDatas(dataIni, dataFin);

		consultas.forEach((c) -> {
			// verifica se médico tem consulta naquela data
			if (c.getMedico().getId() == consulta.getMedico().getId()) {
				/*
				 * se o médico tiver um consulta agendada, verifica se o consultório da consulta
				 * é igual ao consultório para o qual está sendo agendada a consulta. se for
				 * diferente, não agenda, pois o médico só pode atender no mesmo consultório
				 * naquele dia e a especialidade não for cirurgia
				 */
				if (!consulta.getEspecialidadeMedica().equalsIgnoreCase("Cirurgia")
						&& !c.getConsultorio().getId().equals(consulta.getConsultorio().getId())) {
					throw new RuntimeException(
							"O médico já está atendendo no consultorio " + c.getConsultorio().getId());
				}
			}
		});

	}
}
