package com.test.doctor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.doctor.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Integer>{
	public List<Medico> findAllByOrderByNome();
}
