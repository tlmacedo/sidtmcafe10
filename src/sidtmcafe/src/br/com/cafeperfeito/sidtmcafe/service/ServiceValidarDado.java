package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static br.com.cafeperfeito.sidtmcafe.service.ServiceVariavelSistema.USUARIO_LOGADO_APELIDO;

public class ServiceValidarDado implements Constants {
    static final int[] pesoCpf = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    static final int[] pesoCnpj = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    static Pattern p;
    static Matcher m;

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

    public static boolean isEmailHomePageValido(final String value, boolean isEmail, boolean getMsgFaill) {
        if (isEmail)
            p = Pattern.compile(REGEX_EMAIL, Pattern.CASE_INSENSITIVE);
        else
            p = Pattern.compile(REGEX_HOME_PAGE, Pattern.CASE_INSENSITIVE);
        m = p.matcher(value);
        if (m.find())
            return true;
        if (getMsgFaill) {
            ServiceAlertMensagem alertMensagem = new ServiceAlertMensagem();
            alertMensagem.setCabecalho("Dados inválidos");
            alertMensagem.setStrIco("ic_msg_alerta_triangulo_white_24dp.png");
            alertMensagem.setPromptText(String.format("%s, %s: [%s], é %s!", USUARIO_LOGADO_APELIDO,
                    isEmail ? "o email informado" : "a home page informada", value, isEmail ? "inválido" : "inválida"));
            alertMensagem.getRetornoAlert_OK();
        }
        return false;
    }

    public static List<String> getEmailsList(final String value) {
        p = Pattern.compile(REGEX_EMAIL, Pattern.CASE_INSENSITIVE);
        m = p.matcher(value);
        List<String> mail = new ArrayList<>();
        while (m.find())
            mail.add(m.group());
        return mail;
    }

    public static boolean isTelefoneValido(final String value, boolean getMsgFaill) {
        p = Pattern.compile(REGEX_TELEFONE);
        m = p.matcher(value);
        if (m.find())
            return true;
        if (getMsgFaill) {
            ServiceAlertMensagem alertMensagem = new ServiceAlertMensagem();
            alertMensagem.setCabecalho("Dados inválidos");
            alertMensagem.setStrIco("ic_msg_alerta_triangulo_white_24dp.png");
            alertMensagem.setPromptText(String.format("%s, telefone informado: [%s], é inválido!",
                    USUARIO_LOGADO_APELIDO,
                    value));
            alertMensagem.getRetornoAlert_OK();
        }
        return false;
    }

    public static List<Pair<String, Integer>> getTelefoneList(final String value) {
        p = Pattern.compile(REGEX_TELEFONE);
        m = p.matcher(value);

        List<Pair<String, Integer>> telefone = new ArrayList<>();
        String fone = "";
        while (m.find()) {
            fone = m.group().replaceAll("\\D", "");
            if (fone.charAt(0) >= 8)
                fone = "9" + fone;
            telefone.add(new Pair<>(fone, 2));
        }
        return telefone;
    }

}
