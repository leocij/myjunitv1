package com.lemelo.utils;

import java.util.Calendar;
import java.util.Date;

public class MyDates {
    public static Date aumentaDias(Date data, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.DAY_OF_MONTH, dias);
        return calendar.getTime();
    }

    public static Date getDataDiferente(int dias) {
        return aumentaDias(new Date(), dias);
    }

    public static boolean isDataIgual(Date dataUm, Date dataDois) {
        Calendar calendarUm = Calendar.getInstance();
        calendarUm.setTime(dataUm);
        Calendar calendarDois = Calendar.getInstance();
        calendarDois.setTime(dataDois);
        return (calendarUm.get(Calendar.DAY_OF_MONTH) == calendarDois.get(Calendar.DAY_OF_MONTH))
                && (calendarUm.get(Calendar.MONTH) == calendarDois.get(Calendar.MONTH))
                && (calendarUm.get(Calendar.YEAR) == calendarDois.get(Calendar.YEAR));
    }

    public static boolean validarDiaSemana(Date data, int diaSemana) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        return calendar.get(Calendar.DAY_OF_WEEK) == diaSemana;
    }
}
