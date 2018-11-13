package br.com.tlmacedo.cafeperfeito.service;

import br.com.tlmacedo.cafeperfeito.interfaces.Constants;
import com.google.common.base.Splitter;
import com.jfoenix.controls.JFXTextField;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceFormatarDado implements Constants {

    String mascara;
    int posicao = 0;

    public static BigDecimal getBigDecimalFromTextField(String value) {
        if (value.equals("") || value == null) return BigDecimal.ZERO;
        return new BigDecimal(Double.parseDouble(value.replace(".", "").replace(",", ".")));//.replace(".", "").replace(",", "."));
    }

    public static Double getDoubleFromTextField(String value) {
        if (value.length() > 0 && value.substring(value.length() - 1).equals(","))
            value = value.substring(0, (value.length() - 1));
        if (value.equals("") || value == null) return 0.;
        return Double.parseDouble(value.replace(".", "").replace(",", "."));
    }

    static String getFormato(int tamMascara) {
        return "%0" + tamMascara + "d";
    }

//    public static String getValorFormatado(String strValue, String tipMascara, int decimal) {
//        return getValorFormatado(strValue, 0, tipMascara, decimal);
//    }

    public static String getValorFormatado(String strValue, int len, String tipMascara, int decimal) {
        String value = strValue.replaceAll("\\W", "");
        String mask = gerarMascara(len, tipMascara, decimal);
        if (value.length() > 0)
            try {
                if (mask.contains("#,##0")) {
                    if (len == 0) len = 12;
                    Locale.setDefault(LOCALE_MY);
                    value = String.format(getFormato(decimal + 1), Long.parseLong(value));
                    if (value.length() >= len) value = value.substring(0, len);
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

    public static String gerarMascara(int len, String tipMascara, int decimal) {
        String mask = "";
        if ((mask = tipMascara.replaceAll("\\d", "")).equals(""))
            mask = "TEXTO";
        if (!mask.equals("TEXTO") && !mask.equals("Texto")) mask = mask.toLowerCase();
        if (mask.length() >= 2 && mask.substring(0, 2).equals("ie")) {
            return getMascaraIE(mask.length() >= 4 ? mask.substring(2).toUpperCase() : "");
        }
        switch (mask) {
            case "TEXTO":
                if (len == 0) len = 120;
                return String.format(getFormato(len), 0).replace("0", MASK_CARACTER_UPPER);
            case "Texto":
                if (len == 0) len = 120;
                return String.format(getFormato(len), 0).replace("0", MASK_CARACTER_ASTERISCO);
            case "texto":
                if (len == 0) len = 120;
                return String.format(getFormato(len), 0).replace("0", MASK_CARACTER_LOWER);
            case "email":
            case "homepage":
                if (len == 0) len = 120;
                return String.format(getFormato(len), 0).replace("0", MASK_CARACTER_LOWER);
            case "cnpj":
                len = 14;
                return String.format(getFormato(len), 0).replaceAll(REGEX_FS_CNPJ.getKey(), REGEX_FS_CNPJ.getValue()).replace("0", MASK_CARACTER_DIGITO);
            case "cpf":
                len = 11;
                return String.format(getFormato(len), 0).replaceAll(REGEX_FS_CPF.getKey(), REGEX_FS_CPF.getValue()).replace("0", MASK_CARACTER_DIGITO);
            case "rg":
                len = 11;
                return String.format(getFormato(len), 0).replace("0", MASK_CARACTER_DIGITO);
            case "telefone":
                if (len == 0) len = 8;
                return String.format(getFormato(len), 0).replaceAll(REGEX_FS_TELEFONE.getKey(), REGEX_FS_TELEFONE.getValue()).replace("0", MASK_CARACTER_DIGITO);
            case "celular":
                if (len == 0) len = 9;
                return String.format(getFormato(len), 0).replaceAll(REGEX_FS_CELULAR.getKey(), REGEX_FS_CELULAR.getValue()).replace("0", MASK_CARACTER_DIGITO);
            case "ean":
            case "barcode":
            case "barras":
            case "codbarra":
            case "codbarras":
            case "codigobarra":
            case "codigobarras":
                len = 13;
                return String.format(getFormato(len), 0).replace("0", MASK_CARACTER_DIGITO);
            case "numero":
                if (len == 0) len = 9;
                return String.format(getFormato(len), 0).replace("0", MASK_CARACTER_DIGITO);
            case "moeda":
            case "valor":
            case "peso":
                if (len == 0) len = 11;
                len -= (decimal + 1);
                String maskMoeda = String.format(getFormato(len), 0) + ".";
                maskMoeda = maskMoeda.replaceAll("(\\d{2}\\.)", ",$1");
                int qtdLoop = (maskMoeda.length() - 3) / 3;
                int count = 0;
                while (qtdLoop > count) {
                    count++;
                    maskMoeda = maskMoeda.replaceAll("(\\d+)(\\d{3}\\,)", "$1,$2"); //Coloca o resto dos pontos
                }
                maskMoeda = maskMoeda.substring(0, maskMoeda.length() - 1).replace("0", MASK_CARACTER_DIGITO);
                maskMoeda += "0" + (decimal > 0 ? "." + String.format("%0" + decimal + "d", 0) : "");
                return String.format("%s;-%s", maskMoeda, maskMoeda);
            case "cep":
                len = 8;
                return String.format(getFormato(len), 0).replaceAll(REGEX_FS_CEP.getKey(), REGEX_FS_CEP.getValue()).replace("0", MASK_CARACTER_DIGITO);
            case "nfencm":
                len = 8;
                return String.format(getFormato(len), 0).replaceAll(REGEX_FS_NCM.getKey(), REGEX_FS_NCM.getValue()).replace("0", MASK_CARACTER_DIGITO);
            case "nfecest":
                len = 7;
                return String.format(getFormato(len), 0).replaceAll(REGEX_FS_CEST.getKey(), REGEX_FS_CEST.getValue()).replace("0", MASK_CARACTER_DIGITO);
            case "nfechave":
                len = 44;
                return String.format(getFormato(len), 0).replaceAll(REGEX_FS_NFE_CHAVE.getKey(), REGEX_FS_NFE_CHAVE.getValue()).replace("0", MASK_CARACTER_DIGITO);
            case "nfenumero":
                len = 9;
                return String.format(getFormato(len), 0).replaceAll(REGEX_FS_NFE_NUMERO.getKey(), REGEX_FS_NFE_NUMERO.getValue()).replace("0", MASK_CARACTER_DIGITO);
            case "nfedocorigem":
                len = 12;
                return String.format(getFormato(len), 0).replaceAll(REGEX_FS_NFE_DOC_ORIGEM.getKey(), REGEX_FS_NFE_DOC_ORIGEM.getValue()).replace("0", MASK_CARACTER_DIGITO);
        }
        len = 60;
        return String.format(getFormato(len), 0).replace("0", MASK_CARACTER_ASTERISCO);
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

    public void setMascara(int len, String tipOrMascara, int decimal) {
        this.mascara = gerarMascara(len, tipOrMascara, decimal);
    }

    static String getMascaraIE(String uf) {
        String caracter = MASK_CARACTER_DIGITO;
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

    public void maskMoedaField(JFXTextField textField, int len, String tipMascara, int decimal) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String value = newValue;
            if (value == "" || value == null)
                value = "0";
            String vlrMoedaFormatado = getValorFormatado(value, len, tipMascara, decimal);
            textField.setText(vlrMoedaFormatado);
            textField.positionCaret(vlrMoedaFormatado.length());
        });
    }

    public void maskField(JFXTextField textField, int len, String tipMascara, int decimal) {
        if (tipMascara.length() > 0)
            setMascara(len, tipMascara, decimal);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String strValue = newValue, value = newValue, maskDigit = "";
            StringBuilder resultado = new StringBuilder();
            if (strValue.length() > 0) {
                int digitado = 0;
                Pattern p = Pattern.compile(REGEX_PONTUACAO);
                Matcher m = p.matcher(getMascara());
                if (m.find())
                    value = strValue.replaceAll("\\W", "");
                for (int i = 0; i < getMascara().length(); i++) {
                    if (digitado < value.length()) {
                        switch ((maskDigit = getMascara().substring(i, i + 1))) {
                            case "#":
                                if (Character.isDigit(value.charAt(digitado))) {
                                    resultado.append(value.substring(digitado, digitado + 1));
                                    digitado++;
                                }
                                break;
                            case "U":
                            case "A":
                            case "L":
                                if ((Character.isLetterOrDigit(value.charAt(digitado))
                                        || Character.isSpaceChar(value.charAt(digitado))
                                        || Character.isDefined(value.charAt(digitado)))) {
                                    if (maskDigit.equals("L"))
                                        resultado.append(value.substring(digitado, digitado + 1).toLowerCase());
                                    else
                                        resultado.append(value.substring(digitado, digitado + 1).toUpperCase());
                                    digitado++;
                                }
                                break;
                            case "?":
                            case "*":
                                resultado.append(value.substring(digitado, digitado + 1));
                                digitado++;
                                break;
                            default:
                                resultado.append(getMascara().substring(i, i + 1));
                                break;
                        }
                    }
                }
            }
//            Platform.runLater(() -> {
            textField.setText(resultado.toString());
            textField.positionCaret(resultado.length());
//            });
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
