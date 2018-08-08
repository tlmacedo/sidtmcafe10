package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.util.Callback;
import javafx.util.Pair;

import javax.swing.text.MaskFormatter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceFormatarDado implements Constants {
    String mascara;

//    public static String getValueMoeda(String valor, int casaDecimal) {
//        String value = String.valueOf(Long.parseLong(valor.replaceAll("[\\D]", "")));
//        for (int i = value.length(); i < (casaDecimal + 1); i++)
//            value = "0" + value;
//        value = value.replaceAll("(\\d{1})(\\d{" + (casaDecimal + 18) + "})$", "$1.$2");
//        value = value.replaceAll("(\\d{1})(\\d{" + (casaDecimal + 15) + "})$", "$1.$2");
//        value = value.replaceAll("(\\d{1})(\\d{" + (casaDecimal + 12) + "})$", "$1.$2");
//        value = value.replaceAll("(\\d{1})(\\d{" + (casaDecimal + 9) + "})$", "$1.$2");
//        value = value.replaceAll("(\\d{1})(\\d{" + (casaDecimal + 6) + "})$", "$1.$2");
//        value = value.replaceAll("(\\d{1})(\\d{" + (casaDecimal + 3) + "})$", "$1.$2");
//        if (casaDecimal > 0)
//            value = value.replaceAll("(\\d{1})(\\d{" + casaDecimal + "})$", "$1,$2");
//        if (valor.contains("-")) value = "-" + value;
//
//        return value;
//    }

    public static String getValorFormatado(String value, String mascara) {
        String strValue = value.replaceAll("\\W", "");
        String strMasc = gerarMascara(mascara,0);
        if (strValue.length() > 0)
            try {
                if (mascara.contains("#,##0")){
//                tipOrMascara.replaceAll("\\d", "").toLowerCase().equals("moeda") ||
//                        tipOrMascara.replaceAll("\\d", "").toLowerCase().equals("peso") ||
//                        tipOrMascara.replaceAll("\\d", "").toLowerCase().equals("numero")) {
//                    int qtdDigitos = 0;
//                    if (!(tipOrMascara.replaceAll("\\D", "").equals("")))
//                        qtdDigitos = Integer.parseInt(tipOrMascara.replaceAll("\\D", ""));
////                    return new DecimalFormat("#,##" + strMasc + ";-#,##" + strMasc).format(Double.parseDouble(strValue.replaceAll("(\\d{1})(\\d{" + qtdDigitos + "})$", "$1.$2")));
                    return new DecimalFormat(strMasc).format(Double.parseDouble(value));
                } else {
//                    strMasc = strMasc.replace("0", "#");
//                    if (tipOrMascara.replaceAll("\\d", "").toLowerCase().equals("nfenumero"))
//                        return String.format("%09d", Integer.parseInt(strValue)).replaceAll("(\\d{3})", ".$1").substring(1);
                    MaskFormatter formatter = new MaskFormatter(strMasc);
                    formatter.setValueContainsLiteralCharacters(false);
                    return formatter.valueToString(strValue);
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        return null;
    }

    static String getFormato(int tamMascara) {
        return "%0" + tamMascara + "d";
    }

    public static String gerarMascara(String tipMascara, int tamMascara) {
        String formato = getFormato(tamMascara);
        String mask = tipMascara.replaceAll("\\d", "");
        if (!mask.equals("TEXTO") && !mask.equals("Texto")) mask = mask.toLowerCase();
        int digMask = tipMascara.replaceAll("\\D", "").length() > 0
                ? Integer.parseInt(tipMascara.replaceAll("\\D", ""))
                : 0;
        if (mask == null) return String.format(formato, 0).replace("0", CARACTER_UPPER).trim();
        if (mask.length() >= 2 && mask.substring(0, 2).equals("ie")) {
            return getMascaraIE(mask.length() >= 4 ? mask.substring(2).toUpperCase() : "");
        }
        switch (mask) {
            case "TEXTO":
                return String.format(formato, 0).replace("0", CARACTER_UPPER).trim();
            case "Texto":
                return String.format(formato, 0).replace("0", CARACTER_ASTERISCO).trim();
            case "texto":
                return String.format(formato, 0).replace("0", CARACTER_LOWER).trim();
            case "email":
            case "homepage":
                return String.format("%0" + tamMascara + "d", 0).replace("0", CARACTER_LOWER).trim();
            case "cnpj":
                if (tamMascara == 0) formato = getFormato(14);
                return String.format(formato, 0).replaceAll(REGEX_FS_CNPJ.getKey(), REGEX_FS_CNPJ.getValue()).replace("0", CARACTER_DIGITO).trim();
            case "cpf":
                if (tamMascara == 0) formato = getFormato(11);
                return String.format(formato, 0).replaceAll(REGEX_FS_CPF.getKey(), REGEX_FS_CPF.getValue()).replace("0", CARACTER_DIGITO).trim();
            case "telefone":
                if (tamMascara == 0) formato = getFormato(8);
            case "celular":
                if (tamMascara == 0) formato = getFormato(9);
                return String.format(formato, 0).replaceAll(REGEX_FS_TELEFONE.getKey(), REGEX_FS_TELEFONE.getValue()).replace("0", CARACTER_DIGITO).trim();
            case "ean":
            case "barcode":
            case "barras":
            case "codbarra":
            case "codbarras":
            case "codigobarra":
            case "codigobarras":
                if (tamMascara == 0) formato = getFormato(13);
                return String.format(formato, 0).replace("0", CARACTER_DIGITO).trim();
            case "moeda":
            case "numero":
            case "peso":
                String maskMoeda = "#,###,###,##0" + (digMask > 0 ? "." + String.format("%0" + digMask + "d", 0) : "");
                return String.format("%s;-%s", maskMoeda, maskMoeda);
            case "cep":
                if (tamMascara == 0) formato = getFormato(8);
                return String.format(formato, 0).replaceAll(REGEX_FS_CEP.getKey(), REGEX_FS_CEP.getValue()).replace("0", CARACTER_DIGITO).trim();
            case "ncm":
                if (tamMascara == 0) formato = getFormato(8);
                return String.format(formato, 0).replaceAll(REGEX_FS_NCM.getKey(), REGEX_FS_NCM.getValue()).replace("0", CARACTER_DIGITO).trim();
//                if (tamMascara == 0) return "0";
//                if (tamMascara <= 4) {
//                    return String.format("%0" + (tamMascara) + "d", 0).replaceAll("(\\d{4})$", "$1"); //.replace("0", caracter);
//                } else {
//                    if (qtdDigitos <= 6) {
//                        return String.format("%0" + (qtdDigitos) + "d", 0).replaceAll("(\\d{4})(\\d{1,2})$", "$1.$2"); //.replace("0", caracter);
//                    } else {
//                        if (qtdDigitos > 8) {
//                            return String.format("%08d", 0).replaceAll("(\\d{4})(\\d{2})(\\d{2})$", "$1.$2.$3");
//                        } else {
//                            return String.format("%0" + (qtdDigitos) + "d", 0).replaceAll("(\\d{4})(\\d{2})(\\d{1,2})$", "$1.$2.$3"); //.replace("0", caracter);
//                        }
//                    }
//                }
            case "cest":
                if (tamMascara == 0) formato = getFormato(7);
                return String.format(formato, 0).replaceAll(REGEX_FS_CEST.getKey(), REGEX_FS_CEST.getValue()).replace("0", CARACTER_DIGITO).trim();
            case "nfechave":
                if (tamMascara == 0) formato = getFormato(44);
                return String.format(formato, 0).replaceAll(REGEX_FS_NFE_CHAVE.getKey(), REGEX_FS_NFE_CHAVE.getValue()).replace("0", CARACTER_DIGITO).trim();
            case "nfenumero":
                if (tamMascara == 0) formato = getFormato(9);
                return String.format(formato, 0).replaceAll(REGEX_FS_NFE_CHAVE.getKey(), REGEX_FS_NFE_CHAVE.getValue()).replace("0", CARACTER_DIGITO).trim();
            case "nfedocorigem":
                if (tamMascara == 0) formato = getFormato(12);
                return String.format(formato, 0).replaceAll(REGEX_FS_NFE_DOC_ORIGEM.getKey(), REGEX_FS_NFE_DOC_ORIGEM.getValue()).replace("0", CARACTER_DIGITO).trim();
        }
        if (tamMascara == 0) formato = getFormato(60);
        return String.format(formato, 0).replace("0", CARACTER_ASTERISCO);
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String tipOrMascara) {
//        if (tipOrMascara.contains("##"))
        this.mascara = tipOrMascara;
//        else
//            this.mascara = gerarMascara(tipOrMascara, 0, "#");
    }

    static String getMascaraIE(String uf) {
        String caracter = CARACTER_DIGITO;
        switch (uf) {
            case "AC":
                return String.format("%013d", 0).replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{3})(\\d{2})$", "$1.$2.$3/$4-$5").replace("0", caracter);
            case "AL":
                return String.format("%09d", 0).replace("0", caracter);
            case "AM":
                return String.format("%09d", 0).replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{1})$", "$1.$2.$3-$4").replace("0", caracter);
            case "AP":
                return String.format("%09d", 0).replace("0", caracter);
            case "BA":
                return String.format("%09d", 0).replaceAll("(\\d{3})(\\d{3})(\\d{2})(\\d{1})$", "$1.$2.$3-$4").replace("0", caracter);
            case "CE":
                return String.format("%09d", 0).replaceAll("(\\d{8})(\\d{1})$", "$1-$2").replace("0", caracter);
            case "DF":
                return String.format("%013d", 0).replaceAll("(\\d{11})(\\d{2})$", "$1-$2").replace("0", caracter);
            case "ES":
                return String.format("%09d", 0).replaceAll("(\\d{3})(\\d{3})(\\d{2})(\\d{1})$", "$1.$2.$3-$4").replace("0", caracter);
            case "GO":
                return String.format("%09d", 0).replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{1})$", "$1.$2.$3-$4").replace("0", caracter);
            case "MA":
                return String.format("%09d", 0).replace("0", caracter);
            case "MG":
                return String.format("%013d", 0).replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{4})$", "$1.$2.$3/$4").replace("0", caracter);
            case "MS":
                return String.format("%09d", 0).replace("0", caracter);
            case "MT":
                return String.format("%09d", 0).replace("0", caracter);
            case "PA":
                return String.format("%09d", 0).replaceAll("(\\d{2})(\\d{6})(\\d{1})$", "$1-$2-$3").replace("0", caracter);
            case "PB":
                return String.format("%09d", 0).replaceAll("(\\d{8})(\\d{1})$", "$1-$2").replace("0", caracter);
            case "PE":
                return String.format("%014d", 0).replaceAll("(\\d{2})(\\d{1})(\\d{3})(\\d{7})(\\d{1})$", "$1.$2.$3.$4-$5").replace("0", caracter);
            case "PI":
                return String.format("%09d", 0).replace("0", caracter);
            case "PR":
                return String.format("%010d", 0).replaceAll("(\\d{8})(\\d{2})$", "$1-$2").replace("0", caracter);
            case "RJ":
                return String.format("%08d", 0).replaceAll("(\\d{2})(\\d{3})(\\d{2})(\\d{1})$", "$1.$2.$3-$4").replace("0", caracter);
            case "RN":
                return String.format("%09d", 0).replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{1})$", "$1.$2.$3-$4").replace("0", caracter);
            case "RO":
                return String.format("%09d", 0).replaceAll("(\\d{3})(\\d{5})(\\d{1})$", "$1.$2-$3").replace("0", caracter);
            case "RR":
                return String.format("%09d", 0).replaceAll("(\\d{8})(\\d{1})$", "$1-$2").replace("0", caracter);
            case "RS":
                return String.format("%010d", 0).replaceAll("(\\d{3})(\\d{7})$", "$1-$2").replace("0", caracter);
            case "SC":
                return String.format("%09d", 0).replaceAll("(\\d{3})(\\d{3})(\\d{3})$", "$1.$2.$3").replace("0", caracter);
            case "SE":
                return String.format("%010d", 0).replaceAll("(\\d{9})(\\d{1})$", "$1-$2").replace("0", caracter);
            case "SP":
                return String.format("%012d", 0).replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{3})$", "$1.$2.$3.$4").replace("0", caracter);
            case "TO":
                return String.format("%011d", 0).replace("0", caracter);
            default:
                return String.format("%014d", 0).replace("0", caracter);
        }
    }

    public void maskField(JFXTextField textField, String strMask) {
        if (strMask.length() > 0)
            setMascara(strMask);
        textField.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (textField.getText() == null) textField.setText("");
            String value;
            Pattern p = Pattern.compile(REGEX_PONTUACAO);
            Matcher m = p.matcher(getMascara());
            if (m.find())
                value = textField.getText().replaceAll("\\W", "");
            else
                value = textField.getText();//.replaceAll("", "");
            try {
                MaskFormatter formatter = new MaskFormatter(getMascara());
                System.out.printf("campo[%s]    getMascara[%s]   value:[%s]\n", textField.getId(), getMascara(), value);
                formatter.setValueContainsLiteralCharacters(false);//.setAllowsInvalid(false);
                textField.setText(formatter.valueToString(value));
            } catch (Exception ex) {
                if (!(ex instanceof ParseException))
                    ex.printStackTrace();
            }
        });
    }

//    public void maskField(JFXTextField textField, String strMascara) {
//        if (strMascara.length() > 0)
//            setMascara(strMascara);
//        textField.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
//            if (textField.getText() == null) textField.setText("");
//            String value;
//            Pattern p = Pattern.compile(REGEX_PONTUACAO);
//            Matcher m = p.matcher(getMascara());
//            if (m.find())
//                value = textField.getText().replaceAll("\\W", "");
//            else
//                value = textField.getText();//.replaceAll("", "");
//            StringBuilder resultado = new StringBuilder();
//            if (newValue.intValue() > oldValue.intValue()) {
//                if (textField.getText().length() <= getMascara().length()) {
//                    int digitado = 0;
//                    for (int i = 0; i < getMascara().length(); i++) {
//                        if (digitado < value.length()) {
//                            switch (getMascara().substring(i, i + 1)) {
//                                case "@":
//                                    resultado.append(value.substring(i, i + 1).toUpperCase());
//                                    digitado++;
//                                    break;
//                                case "?":
//                                    resultado.append(value.substring(i, i + 1).toLowerCase());
//                                    digitado++;
//                                    break;
//                                case "#":
////                                    if (Character.isLetterOrDigit(value.charAt(digitado)))
////                                        resultado.append(value.substring(digitado, digitado + 1));
////                                    resultado.append(value.substring(i, i + 1));
////                                    digitado++;
//                                    resultado.append(value.substring(i, i + 1));
//                                    digitado++;
//                                    break;
//                                case "0":
//                                    if (Character.isDigit(value.charAt(digitado)))
//                                        resultado.append(value.substring(digitado, digitado + 1));
//                                    digitado++;
//                                    break;
//                                default:
//                                    resultado.append(mascara.substring(i, i + 1));
//                            }
//                        }
//                    }
//                    textField.setText(resultado.toString());
//                } else {
//                    textField.setText(textField.getText(0, getMascara().length()));
//                }
//            }
//        });
//    }

//    public void maskFieldMoeda(JFXTextField textField, int casaDecimal, int lenMax) {
//        textField.setAlignment(Pos.CENTER_RIGHT);
//        textField.textProperty().addListener((observable, oldValue, newValue) -> {
//            Platform.runLater(() -> {
//                if (textField.getLength() > lenMax)
//                    textField.setText(getValueMoeda(newValue.substring(0, lenMax), casaDecimal));
//                else
//                    textField.setText(getValueMoeda(newValue, casaDecimal));
//                textField.positionCaret(newValue.length());
//            });
//        });
//
//    }

    public static void maxField(JFXTextField textField, int tamMax) {
        textField.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > tamMax)
                    textField.setText(textField.getText(0, tamMax));
            }
        });
    }

    public static void fatorarColunaCheckBox(TreeTableColumn colunaGenerica) {
        colunaGenerica.getStyleClass().add("chkbox");
        colunaGenerica.setCellFactory(new Callback<TreeTableColumn, TreeTableCell>() {
            @Override
            public TreeTableCell call(TreeTableColumn param) {
                CheckBoxTreeTableCell cell = new CheckBoxTreeTableCell();
                cell.setAlignment(Pos.TOP_CENTER);
                return cell;
            }
        });
    }

    public static Pair<String, String> getFieldFormat(String accessibleText, String keyFormat) {
        for (String strAccessibleText : accessibleText.toLowerCase().split(", ")) {
            String key = null, value = null;
            for (String detalhe : strAccessibleText.split(":")) {
                if (key == null) key = detalhe.trim();
                else value = detalhe.trim();

                if (key.equals(keyFormat.toLowerCase()) && value != null)
                    return new Pair(key, value);
            }
        }
        return null;
    }
}
