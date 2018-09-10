package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import com.google.common.base.Splitter;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Pos;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.image.Image;
import javafx.util.Callback;
import javafx.util.Pair;
import org.checkerframework.checker.units.qual.Length;

import javax.imageio.ImageIO;
import javax.swing.text.MaskFormatter;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    value = String.format(getFormato(decimal + 1), Long.parseLong(value));
                    if (value.length() >= 12) value = value.substring(strValue.length() - 12);
                    value = value.replaceAll("(\\d+)(\\d{" + decimal + "})", "$1.$2");
                    return new DecimalFormat(mask).format(new BigDecimal(value).setScale(decimal));
                }
                MaskFormatter formatter = new MaskFormatter(mask);
                formatter.setValueContainsLiteralCharacters(false);
                return formatter.valueToString(value).trim();
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
        //int len = 0, decimal = 0;
        String mask = "", strLen = "", strDec = "";
        if ((mask = tipMascara.replaceAll("\\d", "")).equals(""))
            mask = "TEXTO";
        if (!mask.equals("TEXTO") && !mask.equals("Texto")) mask = mask.toLowerCase();
        if ((strLen = tipMascara.substring(0, 3).replaceAll("\\D", "")).equals(""))
            switch (mask.toLowerCase()) {
                case "texto":
                case "email":
                case "homepage":
                    strLen = "120";
                    break;
                default:
                    strLen = "0";
                    break;
            }
        int len = Integer.valueOf(strLen);
        if ((strDec = tipMascara.substring(tipMascara.length() - 1).replaceAll("\\D", "")).equals(""))
            strDec = "0";
        int decimal = Integer.parseInt(strDec);
        if (mask.length() >= 2 && mask.substring(0, 2).equals("ie")) {
            return getMascaraIE(mask.length() >= 4 ? mask.substring(2).toUpperCase() : "");
        }
        switch (mask) {
            case "TEXTO":
                return String.format(getFormato(len), 0).replace("0", CARACTER_UPPER);
            case "Texto":
                return String.format(getFormato(len), 0).replace("0", CARACTER_ASTERISCO);
            case "texto":
                return String.format(getFormato(len), 0).replace("0", CARACTER_LOWER);
            case "email":
            case "homepage":
                return String.format(getFormato(len), 0).replace("0", CARACTER_LOWER);
            case "cnpj":
                len = 14;
                return String.format(getFormato(len), 0).replaceAll(REGEX_FS_CNPJ.getKey(), REGEX_FS_CNPJ.getValue()).replace("0", CARACTER_DIGITO);
            case "cpf":
                len = 11;
                return String.format(getFormato(len), 0).replaceAll(REGEX_FS_CPF.getKey(), REGEX_FS_CPF.getValue()).replace("0", CARACTER_DIGITO);
            case "rg":
                len = 11;
                return String.format(getFormato(len), 0).replace("0", CARACTER_DIGITO);
            case "telefone":
            case "celular":
                len = decimal;
                return String.format(getFormato(len), 0).replaceAll(REGEX_FS_TELEFONE.getKey(), REGEX_FS_TELEFONE.getValue()).replace("0", CARACTER_DIGITO);
            case "ean":
            case "barcode":
            case "barras":
            case "codbarra":
            case "codbarras":
            case "codigobarra":
            case "codigobarras":
                len = 13;
                return String.format(getFormato(len), 0).replace("0", CARACTER_DIGITO);
            case "numero":
                return String.format(getFormato(len), 0).replace("0", CARACTER_DIGITO);
            case "moeda":
            case "valor":
            case "peso":
                String maskMoeda = "#,##0" + (decimal > 0 ? "." + String.format("%0" + decimal + "d", 0) : "");
                return String.format("%s;-%s", maskMoeda, maskMoeda);
            case "cep":
                len = 8;
                return String.format(getFormato(len), 0).replaceAll(REGEX_FS_CEP.getKey(), REGEX_FS_CEP.getValue()).replace("0", CARACTER_DIGITO);
            case "nfencm":
                len = 8;
                return String.format(getFormato(len), 0).replaceAll(REGEX_FS_NCM.getKey(), REGEX_FS_NCM.getValue()).replace("0", CARACTER_DIGITO);
            case "nfecest":
                len = 7;
                return String.format(getFormato(len), 0).replaceAll(REGEX_FS_CEST.getKey(), REGEX_FS_CEST.getValue()).replace("0", CARACTER_DIGITO);
            case "nfechave":
                len = 44;
                return String.format(getFormato(len), 0).replaceAll(REGEX_FS_NFE_CHAVE.getKey(), REGEX_FS_NFE_CHAVE.getValue()).replace("0", CARACTER_DIGITO);
            case "nfenumero":
                len = 9;
                return String.format(getFormato(len), 0).replaceAll(REGEX_FS_NFE_NUMERO.getKey(), REGEX_FS_NFE_NUMERO.getValue()).replace("0", CARACTER_DIGITO);
            case "nfedocorigem":
                len = 12;
                return String.format(getFormato(len), 0).replaceAll(REGEX_FS_NFE_DOC_ORIGEM.getKey(), REGEX_FS_NFE_DOC_ORIGEM.getValue()).replace("0", CARACTER_DIGITO);
        }
        len = 60;
        return String.format(getFormato(len), 0).replace("0", CARACTER_ASTERISCO);
    }

    static Pair<Integer, Integer> getLenDecimal(String strLen) {
        int decimal = 0, len = 12;
        decimal = Integer.parseInt(strLen.substring(strLen.length() - 1));
        if (strLen.length() > 1)
            len = Integer.parseInt(strLen.substring(0, strLen.length() - 1));
        return new Pair<>(len, decimal);
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
                textField.getId();
                textField.setText(getValorFormatado(value, tipMascara));//.replace(",","$").replace(".", ",").replace("$", ".")
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
//                    Platform.runLater(() -> {
                    textField.setText(resultado.toString());
                    textField.positionCaret(resultado.length());
//                    });
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
