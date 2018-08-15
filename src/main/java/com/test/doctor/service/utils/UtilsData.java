package com.test.doctor.service.utils;

import java.util.Calendar;
import java.util.Date;

public class UtilsData {

	public static  FormatadorDeData dataComHoraInicial(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(data.getTime()));
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		return new FormatadorDeData(calendar.getTime());
	}

	public static FormatadorDeData dataComHoraFinal(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(data.getTime()));
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		return new FormatadorDeData(calendar.getTime());
	}
}
