package com.test.doctor.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Service;

import com.test.doctor.model.Consulta;
import com.test.doctor.repository.ConsultaRepository;
import com.test.doctor.service.exception.ObjectNotFoundException;
import com.test.doctor.service.exception.RegistrationPermissionDeniedException;
import com.test.doctor.service.utils.UtilsData;

@Service
public class ConsultaService {
	@Autowired
	private ConsultaRepository repository;
	private int limiteDeConsultas = 12; // limite de consultas diárias em um consultório
	private int intervaloEntreConsultas = 15; // intervalo de tempo entre consultas em um consultório

	public List<Consulta> list() {
		return repository.findAll();
	}

	public Consulta find(Integer id) {
		Optional<Consulta> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Consulta.class.getName()));

	}

	public Consulta insert(Consulta obj) {
		try {

			consultorioEstaDisponivelParaMaisConsultas(obj);
			pacienteJaTemConsultaMarcadaParaHoje(obj);
			consultaRespeitaIntervaloDeQuinzeMinutos(obj);

		} catch (PermissionDeniedDataAccessException | RegistrationPermissionDeniedException e) {
			e.printStackTrace();
			throw new PermissionDeniedDataAccessException(e.getMessage(), e);
		}
		return repository.save(obj);
	}

	public Consulta update(Consulta obj) {
		return repository.save(obj);
	}

	public void delete(Integer id) {
		find(id);
//		try {
		repository.deleteById(id);
//		} catch (DataIntegrityViolationException e) {
//			throw new DataIntegrityViolationException("Não é possível excluir um médico com consultas associadas");
//		}

	}

	private void consultaRespeitaIntervaloDeQuinzeMinutos(Consulta consulta)
			throws RegistrationPermissionDeniedException {

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

	private void consultorioEstaDisponivelParaMaisConsultas(Consulta consulta)
			throws RegistrationPermissionDeniedException {
		Date dataIni = UtilsData.dataComHoraInicial(consulta.getDataConsulta()).getData();
		Date dataFin = UtilsData.dataComHoraFinal(consulta.getDataConsulta()).getData();
		if (!(repository.numConsultasConsultorio(consulta.getConsultorio(), dataIni, dataFin) < limiteDeConsultas)) {
			throw new RegistrationPermissionDeniedException(
					"O número de consultas para esse consultório atingiu o limite de " + limiteDeConsultas
							+ " consultas diárias.");
		}
	}

	private void pacienteJaTemConsultaMarcadaParaHoje(Consulta consulta) throws RegistrationPermissionDeniedException {
		Date dataIni = UtilsData.dataComHoraInicial(consulta.getDataConsulta()).getData();
		Date dataFin = UtilsData.dataComHoraFinal(consulta.getDataConsulta()).getData();
		if (!repository.encontrarConsultasPorPacienteEntreDuasDatas(dataIni, dataFin, consulta.getNomePaciente())
				.isEmpty()) {
			throw new RegistrationPermissionDeniedException("O paciente já tem consulta marcada para o dia de hoje");
		}
	}

}
