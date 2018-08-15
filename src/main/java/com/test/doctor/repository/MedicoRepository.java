package com.test.doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.doctor.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Integer>{

}
