package br.com.tlmacedo.cafeperfeito.service;

import java.time.LocalDate;
import java.time.Period;

public class ServiceDataHora {

    public static String getIntervaloData(LocalDate data1, LocalDate data2) {
        if (data1 == null) return null;
        if (data2 == null) data2 = LocalDate.now();
        Period period = Period.between(data1, data2);
        String strPeriodo = String.format("%s%s%s", period.getYears() < 1 ? "" : String.format(" %d %s", period.getYears(), period.getYears() > 1 ? "anos" : "ano"),
                period.getMonths() < 1 ? "" : String.format(" %d %s", period.getMonths(), period.getMonths() > 1 ? "meses" : "mês"),
                period.getDays() < 1 ? "" : String.format(" %d %s", period.getDays(), period.getDays() > 1 ? "dias" : "dia"));
        if (period.isZero())
            strPeriodo = "hoje";
        return strPeriodo;
    }
}
