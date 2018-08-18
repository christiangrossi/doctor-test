package com.test.doctor.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.doctor.model.Consultorio;
import com.test.doctor.model.Medico;
import com.test.doctor.service.validator.ConsultaInsert;

public class ConsultaDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message="Campo Obrigaório")
	@Length(min=3, message="O campo deve ter no mínimo 3 caracteres")
	private String nomePaciente;
	
	@NotEmpty(message="Campo Obrigaório")
	private String especialidadeMedica;

	private Medico medico;

	@NotNull(message="Campo Obrigaório")
	private Consultorio consultorio;
	
	@FutureOrPresent(message="Data/hora devem ser superiores a atual")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date dataConsulta;

	public ConsultaDTO() {
	}

	public ConsultaDTO(String nomePaciente, String especialidadeMedica, Medico medico, Consultorio consultorio,
			Date dataConsulta) {
		this.nomePaciente = nomePaciente;
		this.especialidadeMedica = especialidadeMedica;
		this.medico = medico;
		this.consultorio = consultorio;
		this.dataConsulta = dataConsulta;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomePaciente() {
		return nomePaciente;
	}

	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	public String getEspecialidadeMedica() {
		return especialidadeMedica;
	}

	public void setEspecialidadeMedica(String especialidadeMedica) {
		this.especialidadeMedica = especialidadeMedica;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Consultorio getConsultorio() {
		return consultorio;
	}

	public void setConsultorio(Consultorio consultorio) {
		this.consultorio = consultorio;
	}

	public Date getDataConsulta() {
		return dataConsulta;
	}

	public void setDataConsulta(Date dataConsulta) {
		this.dataConsulta = dataConsulta;
	}


}
