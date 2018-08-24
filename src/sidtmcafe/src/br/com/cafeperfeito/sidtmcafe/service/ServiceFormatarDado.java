package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.image.Image;
import javafx.util.Callback;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import javax.swing.text.MaskFormatter;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ServiceFormatarDado implements Constants {

    String mascara;

    public static BigDecimal getBigDecimalFromTextField(String value) {
        if (value.equals("") || value == null) return BigDecimal.ZERO;
        return new BigDecimal(value.replace(".", "").replace(",", "."));
    }

    public static String getValorFormatado(String strValue, String tipMascara) {
        String value = strValue.replaceAll("\\W", "");
        String mask = gerarMascara(tipMascara);
        if (value.length() > 0)
            try {
                if (mask.contains("#,##0")) {
                    int decimal = Integer.parseInt(tipMascara.replaceAll("\\D", ""));
                    strValue = String.format(getFormato(decimal + 1), Long.parseLong(value)).replaceAll("(\\d+)(\\d{" + decimal + "})", "$1.$2");
                    if (strValue.length() > 12) strValue = strValue.substring(strValue.length() - 12);
                    String valor = new DecimalFormat(mask).format(new BigDecimal(strValue));
                    return valor;
                }
                MaskFormatter formatter = new MaskFormatter(mask);
                formatter.setValueContainsLiteralCharacters(false);
                return formatter.valueToString(strValue).trim();
            } catch (Exception ex) {
                if (!(ex instanceof NumberFormatException))
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
        int len = tipMascara.replaceAll("\\D", "").length() > 0
                ? Integer.parseInt(tipMascara.replaceAll("\\D", ""))
                : 120;
        String formato = getFormato(len);
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
                return String.format(formato, 0).replace("0", CARACTER_DIGITO);
            case "moeda":
            case "valor":
            case "peso":
                String maskMoeda = "#,##0" + (len > 0 ? "." + String.format("%0" + len + "d", 0) : "");
                return String.format("%s;-%s", maskMoeda, maskMoeda);
            case "cep":
                formato = getFormato(8);
                return String.format(formato, 0).replaceAll(REGEX_FS_CEP.getKey(), REGEX_FS_CEP.getValue()).replace("0", CARACTER_DIGITO);
            case "nfencm":
                formato = getFormato(8);
                return String.format(formato, 0).replaceAll(REGEX_FS_NCM.getKey(), REGEX_FS_NCM.getValue()).replace("0", CARACTER_DIGITO);
            case "nfecest":
                formato = getFormato(7);
                return String.format(formato, 0).replaceAll(REGEX_FS_CEST.getKey(), REGEX_FS_CEST.getValue()).replace("0", CARACTER_DIGITO);
            case "nfechave":
                formato = getFormato(44);
                return String.format(formato, 0).replaceAll(REGEX_FS_NFE_CHAVE.getKey(), REGEX_FS_NFE_CHAVE.getValue()).replace("0", CARACTER_DIGITO);
            case "nfenumero":
                formato = getFormato(9);
                return String.format(formato, 0).replaceAll(REGEX_FS_NFE_NUMERO.getKey(), REGEX_FS_NFE_NUMERO.getValue()).replace("0", CARACTER_DIGITO);
            case "nfedocorigem":
                formato = getFormato(12);
                return String.format(formato, 0).replaceAll(REGEX_FS_NFE_DOC_ORIGEM.getKey(), REGEX_FS_NFE_DOC_ORIGEM.getValue()).replace("0", CARACTER_DIGITO);
        }
        formato = getFormato(60);
        return String.format(formato, 0).replace("0", CARACTER_ASTERISCO);
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String tipOrMascara) {
        this.mascara = gerarMascara(tipOrMascara);
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

    public void maskField(JFXTextField textField, String tipMascara) {
        if (tipMascara.length() > 0)
            setMascara(tipMascara);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String strValue = newValue, value = newValue;
            if (getMascara().contains("#,##0")) {
                if (strValue.equals("") || strValue == null)
                    value = "0";
                textField.setText(getValorFormatado(value, tipMascara));
                textField.positionCaret(textField.getLength());
            } else {
                if (strValue.length() <= 0) {
                    textField.setText("");
                } else {
                    StringBuilder resultado = new StringBuilder();
                    int digitado = 0;
                    Pattern p = Pattern.compile(REGEX_PONTUACAO);
                    Matcher m = p.matcher(getMascara());
                    if (m.find())
                        value = strValue.replaceAll("\\W", "");
                    for (int i = 0; i < getMascara().length(); i++) {
                        if (digitado < value.length()) {
                            switch (getMascara().substring(i, i + 1)) {
                                case "#":
                                    if (!Character.isDigit(value.charAt(digitado))) break;
                                    resultado.append(value.substring(digitado, digitado + 1));
                                    digitado++;
                                    break;
                                case "U":
                                case "A":
                                case "L":
                                    if (!(Character.isLetterOrDigit(value.charAt(digitado))
                                            || Character.isSpaceChar(value.charAt(digitado))
                                            || Character.isDefined(value.charAt(digitado)))) break;
                                    if (getMascara().substring(i, i + 1).equals("L"))
                                        resultado.append(value.substring(i, i + 1).toLowerCase());
                                    else
                                        resultado.append(value.substring(i, i + 1).toUpperCase());
                                    digitado++;
                                    break;
                                case "?":
                                case "*":
                                    resultado.append(value.substring(i, i + 1));
                                    digitado++;
                                    break;
                                default:
                                    resultado.append(getMascara().substring(i, i + 1));
                                    break;
                            }
                        }
                    }
                    Platform.runLater(() -> {
                        textField.setText(resultado.toString());
                        textField.positionCaret(resultado.length());
                    });
                }
            }
        });
    }

//    public void maskFieldMoeda(JFXTextField textField, int casaDecimal, int lenMax) {
//        textField.setAlignment(Pos.CENTER_RIGHT);
//        textField.textProperty().addListener((observable, oldValue, newValue) -> {
//            Platform.runLater(() -> {
//
//                if (getMascara().contains("#,##0"))
//                    System.out.printf("campo: [%s]  newValue:[%s]  tipMascara:[%s]   resultado[%s]\n", textField.getId(), newValue, tipMascara, getValorFormatado(newValue, tipMascara));
//                    resultado.append(getValorFormatado(newValue, tipMascara));
//                if (textField.getLength() > lenMax)
//                    textField.setText(getValueMoeda(newValue.substring(0, lenMax), casaDecimal));
//                else
//                    textField.setText(getValueMoeda(newValue, casaDecimal));
//                textField.positionCaret(newValue.length());
//            });
//        });
//
//    }

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

    public static Pair<String, String> getFieldFormatPair(String accessibleText, String keyFormat) {
        if (accessibleText.equals("")) return null;
        HashMap<String, String> hashMap = getFieldFormatMap(accessibleText);
        return new Pair<String, String>(keyFormat, hashMap.get(keyFormat));
    }

    public static HashMap<String, String> getFieldFormatMap(String accessibleText) {
        if (accessibleText.equals("")) return null;
        return new HashMap<String, String>(Splitter.on(";").omitEmptyStrings().withKeyValueSeparator(Splitter.onPattern("\\:\\:")).split(accessibleText));
    }

    public byte[] getImgToByte(Image image) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write((RenderedImage) image, "png", output);
            return output.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
