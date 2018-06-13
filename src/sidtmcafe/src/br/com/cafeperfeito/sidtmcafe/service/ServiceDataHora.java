package br.com.cafeperfeito.sidtmcafe.service;

import java.time.LocalDate;
import java.time.Period;

public class ServiceDataHora {

    public static String getIntervaloData(LocalDate data1, LocalDate data2) {
        if (data1 == null) return null;
        if (data2 == null) data2 = LocalDate.now();
        Period period = Period.between(data1, data2);
        String strPeriodo = "";
        if (period.getYears() > 0)
            strPeriodo += period.getYears() + (period.getYears() == 1 ? " ano " : " anos ");
        else if (period.getMonths() > 0)
            strPeriodo += period.getMonths() + (period.getMonths() == 1 ? " mês " : " meses ");
        else if (period.getDays() > 0)
            strPeriodo += period.getDays() + (period.getDays() == 1 ? " dias " : " dia ");
        else
            strPeriodo = " hoje ";
        return strPeriodo;
    }

//    public static String getIntervaloData(Date data1, Date data2) {
//        if (data2 == null) data2 = new Date();
//        return getIntervaloData(LocalDate.of(data1.getYear(), data1.getMonth(), data1.getDay()), LocalDate.of(data2.getYear(), data2.getMonth(), data2.getDay()));
//    }
}
