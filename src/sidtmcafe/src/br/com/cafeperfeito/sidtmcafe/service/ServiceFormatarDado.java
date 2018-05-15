package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import com.jfoenix.controls.IFXTextInputControl;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
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
import java.awt.*;
import java.io.BufferedWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static br.com.cafeperfeito.sidtmcafe.interfaces.Constants.PATTERN;

public class ServiceFormatarDado {
    String mascara;
    Matcher matcher;
    BufferedWriter bufferedWriter;

    public static String getValueMoeda(String valor, int casaDecimal) {
        String sinal = "";
        String value = String.valueOf(Long.parseLong(valor.replaceAll("[\\D]", "")));
        for (int i = value.length(); i < (casaDecimal + 1); i++)
            value = "0" + value;

        value = value.replaceAll("(\\d{1})(\\d{" + (casaDecimal + 18) + "})$", "$1.$2");
        value = value.replaceAll("(\\d{1})(\\d{" + (casaDecimal + 15) + "})$", "$1.$2");
        value = value.replaceAll("(\\d{1})(\\d{" + (casaDecimal + 12) + "})$", "$1.$2");
        value = value.replaceAll("(\\d{1})(\\d{" + (casaDecimal + 9) + "})$", "$1.$2");
        value = value.replaceAll("(\\d{1})(\\d{" + (casaDecimal + 6) + "})$", "$1.$2");
        value = value.replaceAll("(\\d{1})(\\d{" + (casaDecimal + 3) + "})$", "$1.$2");
        if (casaDecimal > 0) {
            value = value.replaceAll("(\\d{1})(\\d{" + casaDecimal + "})$", "$1,$2");
        }
        if (valor.substring(0, 1).equals("-")) sinal = "-";
        return sinal + value;
    }

    public static String getValorFormatado(String value, String tipOrMascara) {
        String strValue = value.replaceAll("[\\W]", "");
        String strMasc = gerarMascara(tipOrMascara, value.length(), "#");
        if (strValue.length() > 0)
            try {
                if (tipOrMascara.replaceAll("\\d", "").toLowerCase().equals("moeda")) {
                    int qtdDigitos = 0;
                    if (!(tipOrMascara.replaceAll("\\D", "").equals("")))
                        qtdDigitos = Integer.parseInt(tipOrMascara.replaceAll("\\D", ""));
                    return new DecimalFormat("#,##" + strMasc + ";-#,##" + strMasc).format(Double.parseDouble(strValue.replaceAll("(\\d{1})(\\d{" + qtdDigitos + "})$", "$1.$2")));
                } else {
                    if (tipOrMascara.replaceAll("\\d", "").toLowerCase().equals("nfenumero"))
                        return String.format("%09d", Integer.parseInt(strValue)).replaceAll("(\\d{3})", ".$1").substring(1);
                    MaskFormatter formatter = new MaskFormatter(strMasc);
                    formatter.setValueContainsLiteralCharacters(false);
                    return formatter.valueToString(strValue);
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
//        return strValue;
        return null;
    }

    public static String gerarMascara(String tipOrMascara, int qtdDigitos, String caracter) {
        if (tipOrMascara.equals(""))
            return String.format("%0" + qtdDigitos + "d", 0).replace("0", caracter);
        if (tipOrMascara.toLowerCase().replaceAll("\\d", "").equals("cnpj"))
            return String.format("%014d", 0).replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})$", "$1.$2.$3/$4-$5").replace("0", caracter);
        if (tipOrMascara.toLowerCase().replaceAll("\\d", "").equals("cpf"))
            return String.format("%011d", 0).replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})$", "$1.$2.$3-$4").replace("0", caracter);
        if (tipOrMascara.toLowerCase().replaceAll("\\d", "").equals("barcode") || tipOrMascara.toLowerCase().replaceAll("\\d", "").equals("ean") || tipOrMascara.toLowerCase().replaceAll("\\d", "").equals("codbarras"))
            return String.format("%013d", 0).replace("0", caracter);
        if (tipOrMascara.toLowerCase().replaceAll("\\d", "").equals("moeda") || tipOrMascara.toLowerCase().replaceAll("\\d", "").equals("numero") || tipOrMascara.toLowerCase().replaceAll("\\d", "").equals("peso")) {
            if (tipOrMascara.replaceAll("\\D", "").equals(""))
//                return "#,###,###,##0;-#,###,###,##0";
                return "#,###,###,##0;-#,###,###,##0";
            else
                qtdDigitos = Integer.parseInt(tipOrMascara.replaceAll("\\D", ""));
//            String zeros = String.format("%0" + (qtdDigitos + 1) + "d", 0).replaceAll("(\\d{1})(\\d{" + qtdDigitos + "})$", "$1.$2");
//            return "#,###,###,##" + zeros + ";-#,###,###,##" + zeros;
            return String.format("%0" + (qtdDigitos + 1) + "d", 0).replaceAll("(\\d{1})(\\d{" + qtdDigitos + "})$", "$1.$2");
        }
        if (tipOrMascara.toLowerCase().replaceAll("\\d", "").equals("cep"))
            return String.format("%08d", 0).replaceAll("(\\d{2})(\\d{3})(\\d{3})$", "$1.$2-$3").replace("0", caracter);
        if (tipOrMascara.toLowerCase().replaceAll("\\d", "").equals("ncm"))
            return String.format("%08d", 0).replaceAll("(\\d{4})(\\d{2})(\\d{2})$", "$1.$2.$3").replace("0", caracter);
        if (tipOrMascara.toLowerCase().replaceAll("\\d", "").equals("cest"))
            return String.format("%07d", 0).replaceAll("(\\d{2})(\\d{3})(\\d{2})$", "$1.$2.$3").replace("0", caracter);
        if (tipOrMascara.toLowerCase().replaceAll("\\d", "").equals("nfechave"))
            return String.format("%044d", 0).replaceAll("(\\d{4})", " $1").replace("0", caracter).trim();
        if (tipOrMascara.toLowerCase().replaceAll("\\d", "").equals("nfenumero"))
            return String.format("%09d", 0).replaceAll("(\\d{3})", ".$1").replace("0", caracter).substring(1);
        if (tipOrMascara.toLowerCase().replaceAll("\\d", "").equals("nfedocorigem"))
            return String.format("%012d", 0).replaceAll("(\\d{11})(\\d{1})$", "$1-$2").replace("0", caracter);
        if (tipOrMascara.toLowerCase().replaceAll("\\d", "").equals("telefone"))
            if (qtdDigitos < 9)
                return String.format("%08d", 0).replaceAll("(\\d{4})(\\d{4})$", "$1-$2").replace("0", caracter);
            else
                return String.format("%09d", 0).replaceAll("(\\d{1})(\\d{4})(\\d{4})", "$1 $2-$3").replace("0", caracter);
        if (tipOrMascara.toLowerCase().replaceAll("\\d", "").substring(0, 2).equals("ie"))
            if (tipOrMascara.length() < 4)
                return String.format("%012d", 0).replace("0", caracter);
            else
                return getMascaraIE(tipOrMascara.substring(2).toUpperCase(), caracter);
        return String.format("%1$0" + qtdDigitos + "d", 0).replace("0", caracter);
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

    static String getMascaraIE(String uf, String caracter) {
        if (uf.equals("AC"))
            return String.format("%013d", 0).replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{3})(\\d{2})$", "$1.$2.$3/$4-$5").replace("0", caracter);
        if (uf.equals("AL"))
            return String.format("%09d", 0).replace("0", caracter);
        if (uf.equals("AM"))
            return String.format("%09d", 0).replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{1})$", "$1.$2.$3-$4").replace("0", caracter);
        if (uf.equals("AP"))
            return String.format("%09d", 0).replace("0", caracter);
        if (uf.equals("BA"))
            return String.format("%09d", 0).replaceAll("(\\d{3})(\\d{3})(\\d{2})(\\d{1})$", "$1.$2.$3-$4").replace("0", caracter);
        if (uf.equals("CE"))
            return String.format("%09d", 0).replaceAll("(\\d{8})(\\d{1})$", "$1-$2").replace("0", caracter);
        if (uf.equals("DF"))
            return String.format("%013d", 0).replaceAll("(\\d{11})(\\d{2})$", "$1-$2").replace("0", caracter);
        if (uf.equals("ES"))
            return String.format("%09d", 0).replaceAll("(\\d{3})(\\d{3})(\\d{2})(\\d{1})$", "$1.$2.$3-$4").replace("0", caracter);
        if (uf.equals("GO"))
            return String.format("%09d", 0).replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{1})$", "$1.$2.$3-$4").replace("0", caracter);
        if (uf.equals("MA"))
            return String.format("%09d", 0).replace("0", caracter);
        if (uf.equals("MG"))
            return String.format("%013d", 0).replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{4})$", "$1.$2.$3/$4").replace("0", caracter);
        if (uf.equals("MS"))
            return String.format("%09d", 0).replace("0", caracter);
        if (uf.equals("MT"))
            return String.format("%09d", 0).replace("0", caracter);
        if (uf.equals("PA"))
            return String.format("%09d", 0).replaceAll("(\\d{2})(\\d{6})(\\d{1})$", "$1-$2-$3").replace("0", caracter);
        if (uf.equals("PB"))
            return String.format("%09d", 0).replaceAll("(\\d{8})(\\d{1})$", "$1-$2").replace("0", caracter);
        if (uf.equals("PE"))
            return String.format("%014d", 0).replaceAll("(\\d{2})(\\d{1})(\\d{3})(\\d{7})(\\d{1})$", "$1.$2.$3.$4-$5").replace("0", caracter);
        if (uf.equals("PI"))
            return String.format("%09d", 0).replace("0", caracter);
        if (uf.equals("PR"))
            return String.format("%010d", 0).replaceAll("(\\d{8})(\\d{2})$", "$1-$2").replace("0", caracter);
        if (uf.equals("RJ"))
            return String.format("%08d", 0).replaceAll("(\\d{2})(\\d{3})(\\d{2})(\\d{1})$", "$1.$2.$3-$4").replace("0", caracter);
        if (uf.equals("RN"))
            return String.format("%09d", 0).replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{1})$", "$1.$2.$3-$4").replace("0", caracter);
        if (uf.equals("RO"))
            return String.format("%09d", 0).replaceAll("(\\d{3})(\\d{5})(\\d{1})$", "$1.$2-$3").replace("0", caracter);
        if (uf.equals("RR"))
            return String.format("%09d", 0).replaceAll("(\\d{8})(\\d{1})$", "$1-$2").replace("0", caracter);
        if (uf.equals("RS"))
            return String.format("%010d", 0).replaceAll("(\\d{3})(\\d{7})$", "$1-$2").replace("0", caracter);
        if (uf.equals("SC"))
            return String.format("%09d", 0).replaceAll("(\\d{3})(\\d{3})(\\d{3})$", "$1.$2.$3").replace("0", caracter);
        if (uf.equals("SE"))
            return String.format("%010d", 0).replaceAll("(\\d{9})(\\d{1})$", "$1-$2").replace("0", caracter);
        if (uf.equals("SP"))
            return String.format("%012d", 0).replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{3})$", "$1.$2.$3.$4").replace("0", caracter);
        if (uf.equals("TO"))
            return String.format("%011d", 0).replace("0", caracter);
        return null;
    }

    public void maskField(JFXTextField textField, String strMascara) {
        if (strMascara.length() > 0)
            setMascara(strMascara);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String value = textField.getText().replaceAll("[\\W]", "");
            StringBuilder resultado = new StringBuilder();
            if (newValue.length() > oldValue.length()) {
                if (newValue.length() <= getMascara().length()) {
                    int digitado = 0;
                    for (int i = 0; i < getMascara().length(); i++) {
                        if (digitado < value.length()) {
                            switch (getMascara().substring(i, i + 1)) {
                                case "@":
                                    resultado.append(value.substring(i, i + 1).toUpperCase());
                                    digitado++;
                                    break;
                                case "?":
                                    resultado.append(value.substring(i, i + 1).toLowerCase());
                                    digitado++;
                                    break;
                                case "#":
//                                    if (Character.isLetterOrDigit(value.charAt(digitado)))
//                                        resultado.append(value.substring(digitado, digitado + 1));
                                    resultado.append(value.substring(i, i + 1));
                                    digitado++;
                                    break;
                                case "0":
                                    if (Character.isDigit(value.charAt(digitado)))
                                        resultado.append(value.substring(digitado, digitado + 1));
                                    digitado++;
                                    break;
                                default:
                                    resultado.append(mascara.substring(i, i + 1));
                            }
                        }
                    }
                    textField.setText(resultado.toString());
                } else {
                    textField.setText(textField.getText(0, getMascara().length()));
                }
            }
        });
    }

    public void maskFieldMoeda(JFXTextField textField, int casaDecimal) {
        textField.setAlignment(Pos.CENTER_RIGHT);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                textField.setText(getValueMoeda(newValue, casaDecimal));
                textField.positionCaret(newValue.length());
            });
        });

    }

    public static void maxField(JFXTextField textField, int tamMax) {
//        textField.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
//            if (newValue.intValue() > tamMax)
//                textField.setText(textField.getText().substring(0, tamMax));
//
//        });
        textField.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue()>tamMax)
                    textField.setText(textField.getText(0,tamMax));
            }
        });
//        textField.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                if (newValue.length()>tamMax)
//                    textField.setText(oldValue);
//            }
//        });
    }

    public void fatorarColunaCheckBox(JFXTreeTableColumn colunaGenerica) {
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
                if (key == null)
                    key = detalhe.trim();
                else value = detalhe.trim();
                if (key.equals(keyFormat.toLowerCase()) && value != null) {
                    return new Pair<String, String>(key, value);
                }
            }
        }
        return null;
    }
}
