package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import com.jfoenix.controls.*;
import javafx.concurrent.Service;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ServiceCampoPersonalizado implements Constants {

    //999@_CNPJ
    //(0, 3) qtdMax
    //(3, 4) Tipo [Numero] [AlfaNumerico]
    //(4, 5) Vlr campo Limpo
    //(5, 6) Deshabilitado
    //(6   ) Mascara
    // # -> numero
    // $ -> moeda
    // @ -> alfanum Maiusculo
    // ? -> alfanum Minusculo

//    public static void fieldLenMax(AnchorPane anchorPane) {
//        int tamanho;
//        for (Node node : anchorPane.getChildren())
//            if (node instanceof JFXTextField) {
//                if (node.getAccessibleText() != null)
//                    if ((tamanho = Integer.parseInt(node.getAccessibleText().substring(0, 3))) > 0)
//                        ServiceFormatarDado.maxField((JFXTextField) node, tamanho);
//            } else if (node instanceof TitledPane) {
//                fieldLenMax((AnchorPane) node);
//            } else if (node instanceof TabPane) {
//                for (Tab tab : ((TabPane) node).getTabs())
//                    fieldLenMax((AnchorPane) node);
//            } else if (node instanceof JFXTabPane) {
//                for (Tab tab : ((JFXTabPane) node).getTabs())
//                    fieldLenMax((AnchorPane) node);
//            } else if (node instanceof AnchorPane) {
//                fieldLenMax((AnchorPane) node);
//            }
//    }

    public static void fieldClear(AnchorPane anchorPane) {
        for (Node node : anchorPane.getChildren()) {
//            System.out.println("node: [" + node.toString() + "] node.getAccessibleText(): [" + node.getAccessibleText() + "]");
            String valorInicial = null;
            if (node.getAccessibleText() != null && node.getAccessibleText().contains(":")) {
                if ((valorInicial = ServiceFormatarDado.getFieldFormat(node.getAccessibleText(), "value").getValue()) == null)
                    valorInicial = "";
                if (node instanceof Label)
                    ((Label) node).setText(valorInicial);
            }
            if (node instanceof JFXTextField) {
                    ((JFXTextField) node).setText(valorInicial);
            } else if (node instanceof JFXComboBox) {
                ((JFXComboBox) node).getSelectionModel().select(0);
            } else if (node instanceof JFXCheckBox) {
                ((JFXCheckBox) node).setSelected(valorInicial == "true");
            } else if (node instanceof JFXListView) {
                ((JFXListView) node).getItems().clear();
//            } else if (node instanceof JFXTreeTableView) {
//                ((JFXTreeTableView) node)
            } else if (node instanceof AnchorPane) {
                fieldClear((AnchorPane) node);
            } else if (node instanceof TitledPane) {
                fieldClear((AnchorPane) ((TitledPane) node).getContent());
            } else if (node instanceof TabPane) {
                for (Tab tab : ((TabPane) node).getTabs())
                    fieldClear((AnchorPane) tab.getContent());
            } else if (node instanceof JFXTabPane) {
                for (Tab tab : ((JFXTabPane) node).getTabs())
                    fieldClear((AnchorPane) tab.getContent());
            }
        }
    }

    public static void fieldDisable(AnchorPane anchorPane, boolean setDisable) {
        boolean fielEditable, fieldDisable;
        for (Node node : anchorPane.getChildren()) {
            fielEditable = true;
            fieldDisable = setDisable;
            if (!setDisable)
                if (node.getAccessibleText() != null && node.getAccessibleText().contains(":")) {
                    Pair<String, String> pair;
                    if ((pair = ServiceFormatarDado.getFieldFormat(node.getAccessibleText(), "setEditable")) != null)
                        fielEditable = pair.getValue().equals("true");
                    if ((pair = ServiceFormatarDado.getFieldFormat(node.getAccessibleText(), "setDisable")) != null)
                        fieldDisable = pair.getValue().equals("true");
                }

            if (node instanceof DatePicker) {
                if (!setDisable)
                    ((DatePicker) node).setEditable(fielEditable);
                ((DatePicker) node).setDisable(setDisable || fieldDisable);
            } else if (node instanceof JFXTextField) {
                if (!setDisable)
                    ((JFXTextField) node).setEditable(fielEditable);
                ((JFXTextField) node).setDisable(setDisable || fieldDisable);
            } else if (node instanceof JFXTextArea) {
                if (!setDisable)
                    ((JFXTextArea) node).setEditable(fielEditable);
                ((JFXTextArea) node).setDisable(setDisable || fieldDisable);
            } else if (node instanceof JFXComboBox) {
                ((JFXComboBox) node).setDisable(setDisable || fieldDisable);
            } else if (node instanceof JFXCheckBox) {
                ((JFXCheckBox) node).setDisable(setDisable || fieldDisable);
            } else if (node instanceof JFXTreeTableView) {
                if (!setDisable)
                    ((JFXTreeTableView) node).setEditable(fielEditable);
                ((JFXTreeTableView) node).setDisable(setDisable || fieldDisable);
            } else if (node instanceof AnchorPane) {
                fieldDisable((AnchorPane) node, setDisable);
            } else if (node instanceof TitledPane) {
                fieldDisable((AnchorPane) ((TitledPane) node).getContent(), setDisable);
            } else if (node instanceof TabPane) {
                for (Tab tab : ((TabPane) node).getTabs())
                    fieldDisable((AnchorPane) tab.getContent(), setDisable);
            } else if (node instanceof JFXTabPane) {
                for (Tab tab : ((JFXTabPane) node).getTabs())
                    fieldDisable((AnchorPane) tab.getContent(), setDisable);
            }

        }
    }

    public static void fieldMask(AnchorPane anchorPane) {
        for (Node node : anchorPane.getChildren()) {
            if (node instanceof JFXTextField)
                if (node.getAccessibleText() != null && node.getAccessibleText().contains(":")) {
                    String tipoDado, mascara, caractere = null;
                    int len, decimal = 0;
                    if ((len = Integer.parseInt(ServiceFormatarDado.getFieldFormat(node.getAccessibleText(), "len").getValue())) < 0)
                        len = 0;
                    if ((mascara = ServiceFormatarDado.getFieldFormat(node.getAccessibleText(), "mask").getValue()) == null)
                        mascara = "";

                    if ((tipoDado = ServiceFormatarDado.getFieldFormat(node.getAccessibleText(), "type").getValue()) != null)
                        switch (tipoDado) {
                            case "normal":
                                caractere = "#";
                                break;
                            case "maiusculo":
                            case "maiuscula":
                                caractere = "@";
                                break;
                            case "minusculo":
                            case "minuscula":
                                caractere = "?";
                                break;
                            case "numero":
                            case "moeda":
                            case "peso":
                            case "numeral":
                                caractere = "0";
                                ServiceFormatarDado.maxField((JFXTextField) node, len);
                                if (mascara.contains("numero") || mascara.contains("peso") || mascara.contains("moeda"))
                                    caractere = "$";

                                if (mascara.replaceAll("[\\D]", "").equals(""))
                                    decimal = 0;
                                else
                                    decimal = Integer.parseInt(mascara.replaceAll("[\\D]", ""));
                                break;
                            default:
                                break;
                        }
                    if (caractere == "$") {
                        new ServiceFormatarDado().maskFieldMoeda((JFXTextField) node, decimal);
                    } else {
                        new ServiceFormatarDado().maskField((JFXTextField) node, ServiceFormatarDado.gerarMascara(mascara.replaceAll("[\\d]", ""), len, caractere));
                    }

                }
            if (node instanceof AnchorPane) {
                fieldMask((AnchorPane) node);
            } else if (node instanceof TitledPane) {
                fieldMask((AnchorPane) ((TitledPane) node).getContent());
            } else if (node instanceof TabPane) {
                for (Tab tab : ((TabPane) node).getTabs())
                    fieldMask((AnchorPane) tab.getContent());
            } else if (node instanceof JFXTabPane) {
                for (Tab tab : ((JFXTabPane) node).getTabs())
                    fieldMask((AnchorPane) tab.getContent());
            }
        }

    }
}

