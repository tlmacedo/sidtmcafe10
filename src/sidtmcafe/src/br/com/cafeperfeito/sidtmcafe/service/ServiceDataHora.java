package br.com.cafeperfeito.sidtmcafe.service;

import java.time.LocalDate;
import java.time.Period;

public class ServiceDataHora {

    public static String getIntervaloData(LocalDate data1, LocalDate data2) {
        if (data1 == null) return null;
        if (data2 == null) data2 = LocalDate.now();
        Period period = Period.between(data1, data2);
        if (period.equals(""))
            return " hoje ";
        String strPeriodo = "";

        if (period.getYears() > 0) {
            strPeriodo += period.getYears();
            if (period.getYears() == 1) strPeriodo += " ano ";
            else strPeriodo += " anos ";
        }
        if (period.getMonths() > 0) {
            strPeriodo += period.getMonths();
            if (period.getMonths() == 1) strPeriodo += " mês ";
            else strPeriodo += " meses ";
        }
        if (period.getDays() > 0) {
            strPeriodo += period.getDays();
            if (period.getDays() == 1) strPeriodo += " dia ";
            else strPeriodo += " dias ";
        }
        return strPeriodo;
    }

//    public static String getIntervaloData(Date data1, Date data2) {
//        if (data2 == null) data2 = new Date();
//        return getIntervaloData(LocalDate.of(data1.getYear(), data1.getMonth(), data1.getDay()), LocalDate.of(data2.getYear(), data2.getMonth(), data2.getDay()));
//    }
}
