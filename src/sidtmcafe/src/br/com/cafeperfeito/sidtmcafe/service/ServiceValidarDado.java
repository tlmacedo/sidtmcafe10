package br.com.cafeperfeito.sidtmcafe.service;

public class ServiceValidarDado {
    static final int[] pesoCpf = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    static final int[] pesoCnpj = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    public static boolean isCnpjCpfValido(final String value) {
        value.replaceAll("\\W", "");

        if ((value == null) || (value.length() != 11 && value.length() != 14)
                || (value.matches(value.charAt(0) + "{11}") && value.matches(value.charAt(0) + "{14}")))
            return false;
        String base = value.substring(0, value.length() - 2);
        String dv = value.substring(value.length() - 2);
        int[] peso;
        if (value.length() == 14)
            peso = pesoCnpj;
        else peso = pesoCpf;

        return dv.equals(calculaDv(base, peso));
    }

    static String calculaDv(final String base, final int[] peso) {
        Integer[] digitoDV = {0, 0};
        String valor = base;
        for (int i = 0; i < 2; i++) {
            int soma = 0;
            if (i == 1)
                valor += digitoDV[0];
            for (int indice = valor.length() - 1, digito; indice >= 0; indice--) {
                digito = Integer.parseInt(valor.substring(indice, indice + 1));
                soma += digito * peso[peso.length - valor.length() + indice];
            }
            soma = 11 - soma % 11;
            digitoDV[i] = soma > 9 ? 0 : soma;
        }
        return digitoDV[0].toString() + digitoDV[1].toString();
    }
}
