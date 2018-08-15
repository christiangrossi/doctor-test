package com.test.doctor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Medico extends Pessoa implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(unique=true)
	private String crm;

	public Medico() {
	}
	
	public Medico(String nome, Integer idade, String crm) {
		super(nome, idade);
		this.crm = crm;
	}

	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
	}
	

}
