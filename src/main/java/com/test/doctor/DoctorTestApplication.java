package com.test.doctor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.test.doctor.model.Consulta;
import com.test.doctor.model.Consultorio;
import com.test.doctor.model.Medico;
import com.test.doctor.repository.ConsultaRepository;
import com.test.doctor.repository.ConsultorioRepository;
import com.test.doctor.repository.MedicoRepository;

@SpringBootApplication
public class DoctorTestApplication implements CommandLineRunner {

	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private ConsultorioRepository consultorioRepository;
	
	@Autowired 
	private ConsultaRepository consultaRepository;

	public static void main(String[] args) {
		SpringApplication.run(DoctorTestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Medico m = new Medico("João Augusto", 45, "0042525/SP");
		Medico m2 = new Medico("Serafim Andrade", 52, "0063525/SP");
		
		List<Consultorio> con = Arrays.asList(new Consultorio(), new Consultorio(), new Consultorio());
		
		medicoRepository.saveAll(Arrays.asList(m,m2));
		consultorioRepository.saveAll(con);
		
		Consulta cons1 = new Consulta("Jonas Zair Vendrame", "Ortopedista", m, con.get(0),new Date());
		Consulta cons2 = new Consulta("Fagner Camargo", "Nutricionista", m2, con.get(1),new Date());
		
		consultaRepository.saveAll(Arrays.asList(cons1,cons2));
		

	}

}
