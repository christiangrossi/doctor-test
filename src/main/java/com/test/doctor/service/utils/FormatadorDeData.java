package com.test.doctor.service.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatadorDeData {
	private String pattern = "dd/MM/yyyy HH:mm";
	private Date data;

	public FormatadorDeData(Date data) {
		this.data = data;

	}

	public FormatadorDeData(Date data, String pattern) {
		this.data = data;
		this.pattern = pattern;

	}

	public Date getData() {
		return data;
	}
	
	public String getPattern() {
		return pattern;
	}

	public String stringFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(data);
	}

}
