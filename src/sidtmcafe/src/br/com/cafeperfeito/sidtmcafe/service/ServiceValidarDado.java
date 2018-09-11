package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static br.com.cafeperfeito.sidtmcafe.service.ServiceVariavelSistema.USUARIO_LOGADO_APELIDO;

public class ServiceValidarDado implements Constants {
    static final int[] pesoCpf = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    static final int[] pesoCnpj = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    static Pattern p, pt, pd;
    static Matcher m, mt, md;

    public static boolean isCnpjCpfValido(String value) {
        value = value.replaceAll("\\W", "");

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

    public static boolean isEan13Valido(final String value) {
        Integer[] digitoDV = {0, 0};
        digitoDV[0] = Integer.valueOf(value.substring(12));
        String base = value.substring(0, 12);
        int sum = 0;
        for (int i = 0; i < 12; ++i) {
            int val = charToInt(base.charAt(i));
            if ((i + 1) % 2 == 0)
                sum += val * 3;
            else
                sum += val * 1;
        }
        digitoDV[1] = (10 - (sum % 10));

        if (!digitoDV[0].equals(digitoDV[1])) {
            ServiceAlertMensagem alertMensagem = new ServiceAlertMensagem();
            alertMensagem.setCabecalho("Dados inválidos");
            alertMensagem.setStrIco("ic_msg_alerta_triangulo_white_24dp.png");
            alertMensagem.setPromptText(String.format("%s, o código de barras informado: [%s], é inválido!",
                    USUARIO_LOGADO_APELIDO,
                    value));
            alertMensagem.getRetornoAlert_OK();
            return false;
        }
        return true;
    }

    static int charToInt(char c) {
        return Integer.parseInt(String.valueOf(c));
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

    public static List<String> getTelefoneList(final String value) {
        p = Pattern.compile(REGEX_TELEFONE_DDD);
        m = p.matcher(value);
        pt = Pattern.compile(REGEX_TELEFONE);
        pd = Pattern.compile(REGEX_DDD);

        List<String> telefone = new ArrayList<>();
        String ddd = "", fone = "";
        while (m.find()) {
            ddd = "";
            fone = "";
            md = pd.matcher(m.group());
            if (md.find())
                ddd = md.group().replaceAll("\\D", "");
            if (ddd.equals("")) ddd = String.valueOf(DDD_SISTEMA);

            mt = pt.matcher(m.group());
            if (mt.find())
                fone = mt.group().replaceAll("\\D", "");
            if (Integer.parseInt(fone.substring(0, 1)) >= 7) fone = "9" + fone;

            telefone.add(ddd + fone);
        }
        return telefone;
    }

}
