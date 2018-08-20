package com.test.doctor.service.validator;

import java.util.Date;

import com.test.doctor.dto.ConsultaDTO;
import com.test.doctor.repository.ConsultaRepository;
import com.test.doctor.service.exception.RegistrationPermissionDeniedException;
import com.test.doctor.service.utils.UtilsData;

public class PacienteJaTemConsultaParaADataValidator extends ConsultaValidator{

	PacienteJaTemConsultaParaADataValidator(ConsultaDTO consulta, ConsultaRepository repository) {
		super(consulta, repository);
	}

	@Override
	public void validar() {
		Date dataIni = UtilsData.dataComHoraInicial(consulta.getDataConsulta()).getData();
		Date dataFin = UtilsData.dataComHoraFinal(consulta.getDataConsulta()).getData();
		if (!repository.encontrarConsultasPorPacienteEntreDuasDatas(dataIni, dataFin, consulta.getNomePaciente())
				.isEmpty()) {
			throw new RegistrationPermissionDeniedException("O paciente já tem consulta marcada para essa data: "+ consulta.getDataConsulta());
		}
	}



}
