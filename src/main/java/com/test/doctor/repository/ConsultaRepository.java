package com.test.doctor.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.test.doctor.model.Consulta;
import com.test.doctor.model.Consultorio;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {
	
	@Query("select c from Consulta c where c.dataConsulta BETWEEN :start AND :end")
	List<Consulta> encontrarConsultasEntreDuasDatas(@Param("start") Date start, @Param("end") Date end);

	@Query("select c from Consulta c where c.nomePaciente=:nome and c.dataConsulta BETWEEN :start AND :end")
	List<Consulta> encontrarConsultasPorPacienteEntreDuasDatas(@Param("start") Date start, @Param("end") Date end,
			@Param("nome") String nomePaciente);

	@Query("select c from Consulta c where c.consultorio = :consultorio and c.dataConsulta BETWEEN :start AND :end")
	List<Consulta> encontrarConsultasEntreIntervalos(@Param("start") Date start, @Param("end") Date end,
			Consultorio consultorio);

	@Query("select count(c) from Consulta c where c.consultorio=:consultorio and c.dataConsulta BETWEEN :start AND :end")
	long numConsultasConsultorio(@Param("consultorio") Consultorio consultorio, @Param("start") Date start,
			@Param("end") Date end);
	
	List<Consulta>findAllByOrderByDataConsultaDesc();
}
