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
    String strMascara;

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

    public static String getValorFormatado(String value, String tipMascara) {
        String strValue = value.replaceAll("\\W", "");
        String mascara = gerarMascara(tipMascara);
        if (strValue.length() > 0)
            try {
                if (mascara.contains("#,##0")) {
                    return new DecimalFormat(mascara).format(Double.parseDouble(value));
                } else {
                    MaskFormatter formatter = new MaskFormatter(mascara);
                    formatter.setValueContainsLiteralCharacters(false);
                    return formatter.valueToString(strValue).trim();
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        return null;
    }

    static String getFormato(int tamMascara) {
        return "%0" + tamMascara + "d";
    }

    public static String gerarMascara(String tipMascara) {
        String mask = tipMascara.replaceAll("\\d", "");
        if (!mask.equals("TEXTO") && !mask.equals("Texto")) mask = mask.toLowerCase();
        int digMask = tipMascara.replaceAll("\\D", "").length() > 0
                ? Integer.parseInt(tipMascara.replaceAll("\\D", ""))
                : 0;
        String formato = (digMask == 0) ? getFormato(120) : getFormato(digMask);
        if (mask == null) return String.format(formato, 0).replace("0", CARACTER_UPPER);
        if (mask.length() >= 2 && mask.substring(0, 2).equals("ie")) {
            return getMascaraIE(mask.length() >= 4 ? mask.substring(2).toUpperCase() : "");
        }
        switch (mask) {
            case "TEXTO":
                return String.format(formato, 0).replace("0", CARACTER_UPPER);
            case "Texto":
                return String.format(formato, 0).replace("0", CARACTER_ASTERISCO);
            case "texto":
                return String.format(formato, 0).replace("0", CARACTER_LOWER);
            case "email":
            case "homepage":
                return String.format(formato, 0).replace("0", CARACTER_LOWER);
            case "cnpj":
                formato = getFormato(14);
                return String.format(formato, 0).replaceAll(REGEX_FS_CNPJ.getKey(), REGEX_FS_CNPJ.getValue()).replace("0", CARACTER_DIGITO);
            case "cpf":
                formato = getFormato(11);
                return String.format(formato, 0).replaceAll(REGEX_FS_CPF.getKey(), REGEX_FS_CPF.getValue()).replace("0", CARACTER_DIGITO);
            case "rg":
                formato = getFormato(14);
                return String.format(formato, 0).replace("0", CARACTER_DIGITO);
            case "telefone":
            case "celular":
                return String.format(formato, 0).replaceAll(REGEX_FS_TELEFONE.getKey(), REGEX_FS_TELEFONE.getValue()).replace("0", CARACTER_DIGITO);
            case "ean":
            case "barcode":
            case "barras":
            case "codbarra":
            case "codbarras":
            case "codigobarra":
            case "codigobarras":
                formato = getFormato(13);
                return String.format(formato, 0).replace("0", CARACTER_DIGITO);
            case "numero":
                System.out.printf("retorno de formato numero:[%s]\n", String.format(formato, 0).replace("0", CARACTER_DIGITO));
                return String.format(formato, 0).replace("0", CARACTER_DIGITO);
            case "moeda":
            case "valor":
            case "peso":
                String maskMoeda = "#,###,###,##0" + (digMask > 0 ? "." + String.format("%0" + digMask + "d", 0) : "");
                System.out.printf("retorno de formato moeda:[%s]\n", String.format("%s;-%s", maskMoeda, maskMoeda));
                return String.format("%s;-%s", maskMoeda, maskMoeda);
            case "cep":
                formato = getFormato(8);
                return String.format(formato, 0).replaceAll(REGEX_FS_CEP.getKey(), REGEX_FS_CEP.getValue()).replace("0", CARACTER_DIGITO);
            case "ncm":
                formato = getFormato(8);
                return String.format(formato, 0).replaceAll(REGEX_FS_NCM.getKey(), REGEX_FS_NCM.getValue()).replace("0", CARACTER_DIGITO);
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
                formato = getFormato(7);
                return String.format(formato, 0).replaceAll(REGEX_FS_CEST.getKey(), REGEX_FS_CEST.getValue()).replace("0", CARACTER_DIGITO);
            case "nfechave":
                formato = getFormato(44);
                return String.format(formato, 0).replaceAll(REGEX_FS_NFE_CHAVE.getKey(), REGEX_FS_NFE_CHAVE.getValue()).replace("0", CARACTER_DIGITO);
            case "nfenumero":
                formato = getFormato(9);
                return String.format(formato, 0).replaceAll(REGEX_FS_NFE_CHAVE.getKey(), REGEX_FS_NFE_CHAVE.getValue()).replace("0", CARACTER_DIGITO);
            case "nfedocorigem":
                formato = getFormato(12);
                return String.format(formato, 0).replaceAll(REGEX_FS_NFE_DOC_ORIGEM.getKey(), REGEX_FS_NFE_DOC_ORIGEM.getValue()).replace("0", CARACTER_DIGITO);
        }
        formato = getFormato(60);
        return String.format(formato, 0).replace("0", CARACTER_ASTERISCO);
    }

    public String getStrMascara() {
        return strMascara;
    }

    public void setStrMascara(String tipOrMascara) {
        this.strMascara = gerarMascara(tipOrMascara);
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

//    public void maskField(JFXTextField textField, String strMask) {
//        if (strMask.length() > 0)
//            setStrMascara(strMask);
//        textField.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
//            if (textField.getText() == null) textField.setText("");
//            String value;
//            Pattern p = Pattern.compile(REGEX_PONTUACAO);
//            Matcher m = p.matcher(getStrMascara());
//            if (m.find())
//                value = textField.getText().replaceAll("\\W", "");
//            else
//                value = textField.getText();//.replaceAll("", "");
//            try {
//                MaskFormatter formatter = new MaskFormatter(getStrMascara());
//                System.out.printf("campo[%s]    getStrMascara[%s]   value:[%s]\n", textField.getId(), getStrMascara(), value);
//                //formatter.setAllowsInvalid(false);//.setValueContainsLiteralCharacters(false);//.setAllowsInvalid(false);
//                textField.setText(formatter.valueToString(value));
////                textField.setText(getValorFormatado(value, getStrMascara()));
//            } catch (Exception ex) {
//                if (!(ex instanceof ParseException))
//                    ex.printStackTrace();
//            }
//        });
//    }

    public void maskField(JFXTextField textField, String tipMascara) {
        if (tipMascara.length() > 0)
            setStrMascara(tipMascara);
        System.out.printf("maskField[%s]: [%s]", textField.getId(), getStrMascara());
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (textField.getText() == null) textField.setText("");
            String value;
            Pattern p = Pattern.compile(REGEX_PONTUACAO);
            Matcher m = p.matcher(getStrMascara());
            if (m.find())
                value = textField.getText().replaceAll("\\W", "");
            else
                value = textField.getText();//.replaceAll("", "");
            StringBuilder resultado = new StringBuilder();
            if (newValue.length() <= 0) textField.setText("");
            if (newValue.length() > 0) {
                if (textField.getText().length() <= getStrMascara().length()) {
                    int digitado = 0;
                    for (int i = 0; i < getStrMascara().length(); i++) {
                        if (digitado < value.length()) {
                            switch (getStrMascara().substring(i, i + 1)) {
                                case "#":
                                    if (Character.isDigit(value.charAt(digitado)) || Character.isSpaceChar(value.charAt(digitado)))
                                        resultado.append(value.substring(digitado, digitado + 1));
                                    digitado++;
                                    break;
                                case "U":
                                    if (Character.isLetterOrDigit(value.charAt(digitado)) || Character.isSpaceChar(value.charAt(digitado)))
                                        resultado.append(value.substring(i, i + 1).toUpperCase());
                                    digitado++;
                                    break;
                                case "L":
                                    if (Character.isLetterOrDigit(value.charAt(digitado)) || Character.isSpaceChar(value.charAt(digitado)))
                                        resultado.append(value.substring(i, i + 1).toLowerCase());
                                    digitado++;
                                    break;
                                case "A":
                                    if (Character.isLetterOrDigit(value.charAt(digitado)) || Character.isSpaceChar(value.charAt(digitado)))
                                        resultado.append(value.substring(i, i + 1).toUpperCase());
                                    digitado++;
                                    break;
                                case "?":
                                case "*":
                                    resultado.append(value.substring(i, i + 1));
                                    digitado++;
                                    break;
                                default:
                                    resultado.append(getStrMascara().substring(i, i + 1));
                                    break;
                            }
                        }
                    }
                    Platform.runLater(() -> {
                        textField.setText(resultado.toString());
                        textField.positionCaret(newValue.length());
                    });
                } else {
                    textField.setText(textField.getText(0, getStrMascara().length()));
                }
            } else {
                textField.setText("");
            }
        });
//        textField.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
//            if (textField.getText() == null) textField.setText("");
//            String value;
//            Pattern p = Pattern.compile(REGEX_PONTUACAO);
//            Matcher m = p.matcher(getStrMascara());
//            if (m.find())
//                value = textField.getText().replaceAll("\\W", "");
//            else
//                value = textField.getText();//.replaceAll("", "");
//            StringBuilder resultado = new StringBuilder();
//            if (newValue.intValue() > 0) {
//                if (textField.getText().length() <= getStrMascara().length()) {
//                    int digitado = 0;
//                    for (int i = 0; i < getStrMascara().length(); i++) {
//                        if (digitado < value.length()) {
//                            switch (getStrMascara().substring(i, i + 1)) {
//                                case "#":
//                                    if (Character.isDigit(value.charAt(digitado)))
//                                        resultado.append(value.substring(digitado, digitado + 1));
//                                    digitado++;
//                                    break;
//                                case "U":
//                                    if (Character.isLetterOrDigit(value.charAt(digitado)))
//                                        resultado.append(value.substring(i, i + 1).toUpperCase());
//                                    digitado++;
//                                    break;
//                                case "L":
//                                    if (Character.isLetterOrDigit(value.charAt(digitado)))
//                                        resultado.append(value.substring(i, i + 1).toLowerCase());
//                                    digitado++;
//                                    break;
//                                case "A":
//                                    if (Character.isLetterOrDigit(value.charAt(digitado)))
//                                        resultado.append(value.substring(i, i + 1).toUpperCase());
//                                    digitado++;
//                                    break;
//                                case "?":
//                                case "*":
//                                    resultado.append(value.substring(i, i + 1));
//                                    digitado++;
//                                    break;
//                                default:
//                                    resultado.append(strMascara.substring(i, i + 1));
//                                    break;
//                            }
//                        }
//                    }
//                    textField.setText(resultado.toString());
//                } else {
//                    textField.setText(textField.getText(0, getStrMascara().length()));
//                }
//            } else {
//                textField.setText("");
//            }
//        });
    }

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
        for (String strAccessibleText : accessibleText.split(", ")) {
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
