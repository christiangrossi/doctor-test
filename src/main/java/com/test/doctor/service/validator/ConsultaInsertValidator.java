package com.test.doctor.service.validator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.test.doctor.controller.exception.FieldMessage;
import com.test.doctor.model.Consulta;
import com.test.doctor.model.dto.ConsultaDTO;
import com.test.doctor.repository.ConsultaRepository;
import com.test.doctor.service.exception.RegistrationPermissionDeniedException;
import com.test.doctor.service.utils.UtilsData;

public class ConsultaInsertValidator implements ConstraintValidator<ConsultaInsert, ConsultaDTO> {

	private int limiteDeConsultas = 12; // Limited de consultas diárias em um consultório
	private int intervaloEntreConsultas = 15; // intervalo de tempo entre consultas em um consultório

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ConsultaRepository repository;

	@Override
	public void initialize(ConsultaInsert ann) {
	}

	@Override
	public boolean isValid(ConsultaDTO objDto, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

		Integer urlId = Integer.parseInt(map.get("id"));

		List<FieldMessage> list = new ArrayList<>();
//
//		Cliente aux = repository.findByEmail(objDto.getEmail());
//		if (aux != null &&! aux.getId().equals(urlId)) {
//			list.add(new FieldMessage("email", "Esse endereço de e-mail já está sendo utilizado"));
//		}

		// inclua os testes aqui, inserindo erros na lista
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFildName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

	private void consultorioEstaDisponivelParaMaisConsultas(Consulta consulta) {
		Date dataIni = UtilsData.dataComHoraInicial(consulta.getDataConsulta()).getData();
		Date dataFin = UtilsData.dataComHoraFinal(consulta.getDataConsulta()).getData();
		if (!(repository.numConsultasConsultorio(consulta.getConsultorio(), dataIni, dataFin) < limiteDeConsultas)) {
			throw new RegistrationPermissionDeniedException(
					"O número de consultas para esse consultório atingiu o limite de " + limiteDeConsultas
							+ " consultas diárias.");
		}
	}

	private void pacienteJaTemConsultaMarcadaParaHoje(Consulta consulta) {
		Date dataIni = UtilsData.dataComHoraInicial(consulta.getDataConsulta()).getData();
		Date dataFin = UtilsData.dataComHoraFinal(consulta.getDataConsulta()).getData();
		if (!repository.encontrarConsultasPorPacienteEntreDuasDatas(dataIni, dataFin, consulta.getNomePaciente())
				.isEmpty()) {
			throw new RegistrationPermissionDeniedException("O paciente já tem consulta marcada para o dia de hoje");
		}
	}

	private void consultaRespeitaIntervaloDeQuinzeMinutos(Consulta consulta) {

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

	private void verificarDisponibilidadeDoConsultorio(Consulta consulta) {
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