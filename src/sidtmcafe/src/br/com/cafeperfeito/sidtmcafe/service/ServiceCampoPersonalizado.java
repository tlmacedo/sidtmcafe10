package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import com.jfoenix.controls.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

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
//            } else if (node instanceof TabPane) {
//                for (Tab tab : ((TabPane) node).getTabs())
//                    fieldLenMax((AnchorPane) node);
//            } else if (node instanceof AnchorPane) {
//                fieldLenMax((AnchorPane) node);
//            }
//    }

    public static void fieldClear(AnchorPane anchorPane) {
        for (Node node : anchorPane.getChildren()) {
//            System.out.println("node: [" + node.toString() + "] node.getAccessibleText(): [" + node.getAccessibleText() + "]");
            String valorInicial = "";
            if (node.getAccessibleText() != null && node.getAccessibleText().contains(":")) {
                if ((valorInicial = ServiceFormatarDado.getFieldFormat(node.getAccessibleText(), "value").getValue()) == null)
                    valorInicial = "";
                if (node instanceof Label)
                    ((Label) node).setText(valorInicial);
            }
            if (node instanceof JFXTextField) {
                ((JFXTextField) node).setText(valorInicial);
                valorInicial = "";
            } else if (node instanceof JFXCheckBox) {
                ((JFXCheckBox) node).setSelected(valorInicial == "true");
            } else if (node instanceof JFXListView) {
                ((JFXListView) node).getItems().clear();
//            } else if (node instanceof TreeTableView) {
//                ((TreeTableView) node)
            } else if (node instanceof JFXComboBox) {
                if (valorInicial.equals("")) {
                    ((JFXComboBox) node).getSelectionModel().select(-1);
                } else {
                    ((JFXComboBox) node).getSelectionModel().select(Integer.parseInt(valorInicial));
                }
            } else if (node instanceof AnchorPane) {
                fieldClear((AnchorPane) node);
            } else if (node instanceof TitledPane) {
                fieldClear((AnchorPane) ((TitledPane) node).getContent());
            } else if (node instanceof TabPane) {
                for (Tab tab : ((TabPane) node).getTabs())
                    fieldClear((AnchorPane) tab.getContent());
//            } else if (node instanceof TabPane) {
//                for (Tab tab : ((TabPane) node).getTabs())
//                    fieldClear((AnchorPane) tab.getContent());
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
            } else if (node instanceof JFXListView) {
                ((JFXListView) node).setDisable(false);
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
            } else if (node instanceof TreeTableView) {
                if (!setDisable)
                    ((TreeTableView) node).setEditable(fielEditable);
                ((TreeTableView) node).setDisable(setDisable || fieldDisable);
            } else if (node instanceof AnchorPane) {
                fieldDisable((AnchorPane) node, setDisable);
            } else if (node instanceof TitledPane) {
                fieldDisable((AnchorPane) ((TitledPane) node).getContent(), setDisable);
            } else if (node instanceof TabPane) {
                for (Tab tab : ((TabPane) node).getTabs())
                    fieldDisable((AnchorPane) tab.getContent(), setDisable);
            } else if (node instanceof TabPane) {
                for (Tab tab : ((TabPane) node).getTabs())
                    fieldDisable((AnchorPane) tab.getContent(), setDisable);
            }

        }
    }

    public static void fieldMask(AnchorPane anchorPane) {
        for (Node node : anchorPane.getChildren()) {
            if (node instanceof JFXTextField) {
                if (node.getAccessibleText().toLowerCase().contains("type:")) {
                    int len = 0;
                    len = node.getAccessibleText().toLowerCase().contains("len:")
                            ? Integer.parseInt(ServiceFormatarDado.getFieldFormat(node.getAccessibleText(), "len").getValue())
                            : 0;
                    String type = "";
                    if ((type = ServiceFormatarDado.getFieldFormat(node.getAccessibleText(), "type").getValue()) == null)
                        type = "TEXTO";
                    System.out.printf("len:[%s]   type:[%s]   campo:[%s]\n", len, type, node.getId());
                    new ServiceFormatarDado().maskField((JFXTextField) node, type + len);
                }
            }
            if (node instanceof AnchorPane) {
                fieldMask((AnchorPane) node);
            } else if (node instanceof TitledPane) {
                fieldMask((AnchorPane) ((TitledPane) node).getContent());
            } else if (node instanceof TabPane) {
                for (Tab tab : ((TabPane) node).getTabs())
                    fieldMask((AnchorPane) tab.getContent());
            } else if (node instanceof TabPane) {
                for (Tab tab : ((TabPane) node).getTabs())
                    fieldMask((AnchorPane) tab.getContent());
            }
        }

    }
}

