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

    public static void fieldLenMax(AnchorPane anchorPane) {
        int tamanho;
        for (Node node : anchorPane.getChildren())
            if (node instanceof JFXTextField) {
                if (node.getAccessibleText() != null)
                    if ((tamanho = Integer.parseInt(node.getAccessibleText().substring(0, 3))) > 0)
                        ServiceFormatarDado.maxField((JFXTextField) node, tamanho);
            } else if (node instanceof TitledPane) {
                fieldLenMax((AnchorPane) node);
            } else if (node instanceof TabPane) {
                for (Tab tab : ((TabPane) node).getTabs())
                    fieldLenMax((AnchorPane) node);
            } else if (node instanceof JFXTabPane) {
                for (Tab tab : ((JFXTabPane) node).getTabs())
                    fieldLenMax((AnchorPane) node);
            } else if (node instanceof AnchorPane) {
                fieldLenMax((AnchorPane) node);
            }
    }

    public static void fieldClear(AnchorPane anchorPane) {
        for (Node node : anchorPane.getChildren()) {
            String newValue = null;
            List<Pair<String, String>> fieldFormatList = new ArrayList<>();
            System.out.println("node[" + node.toString() + "]");
            if (node.getAccessibleText() != null) {
                System.out.println("node[" + node.toString() + "] :[" + node.getAccessibleText() + "]");
                if ((newValue = ServiceFormatarDado.getFieldFormat(node.getAccessibleText(), "value").getValue()) == null)
                    newValue = "";
                System.out.println("newValue: [" + newValue + "]");
            }
            if (node instanceof JFXTextField) {
                ((JFXTextField) node).setText(newValue);
            } else if (node instanceof TitledPane) {
                fieldClear((AnchorPane) ((TitledPane) node).getContent());
            } else if (node instanceof TabPane) {
                for (Tab tab : ((TabPane) node).getTabs())
                    fieldClear((AnchorPane) node);
            } else if (node instanceof JFXTabPane) {
                for (Tab tab : ((JFXTabPane) node).getTabs())
                    fieldClear((AnchorPane) node);
            } else if (node instanceof AnchorPane) {
                fieldClear((AnchorPane) node);
            }
        }
    }

    public static void fieldDisable(AnchorPane anchorPane, boolean setDisable) {
        int tipMascara;
        boolean campoEditavel = true;
        boolean campoDeshabilitado = setDisable;
        for (Node node : anchorPane.getChildren()) {
            if (node.getAccessibleText() != null && node.getAccessibleText().length() > 5 && (tipMascara = Integer.parseInt(node.getAccessibleText().substring(5, 6))) >= 0) {
                if (tipMascara == 1) campoDeshabilitado = true;
                if (tipMascara == 2) campoEditavel = false;
            }

            if (node instanceof DatePicker) {
                if (!campoEditavel)
                    ((DatePicker) node).setEditable(campoEditavel);
                ((DatePicker) node).setDisable(campoDeshabilitado);
            } else if (node instanceof JFXTextField) {
                if (!campoEditavel)
                    ((JFXTextField) node).setEditable(campoEditavel);
                ((JFXTextField) node).setDisable(campoDeshabilitado);
            } else if (node instanceof JFXTextArea) {
                if (!campoEditavel)
                    ((JFXTextArea) node).setEditable(campoEditavel);
                ((JFXTextArea) node).setDisable(campoDeshabilitado);
            } else if (node instanceof JFXComboBox) {
                if (!campoEditavel)
                    ((JFXComboBox) node).setEditable(campoEditavel);
                ((JFXComboBox) node).setDisable(campoDeshabilitado);
            } else if (node instanceof JFXCheckBox) {
                ((JFXCheckBox) node).setDisable(campoDeshabilitado);
            } else if (node instanceof JFXTreeTableView) {
                if (!campoEditavel)
                    ((JFXTreeTableView) node).setEditable(campoEditavel);
                ((JFXTreeTableView) node).setDisable(campoDeshabilitado);
            } else if (node instanceof TitledPane) {
                fieldDisable((AnchorPane) ((TitledPane) node).getContent(), setDisable);
            } else if (node instanceof TabPane) {
                for (Tab tab : ((TabPane) node).getTabs())
                    fieldDisable((AnchorPane) node, setDisable);
            } else if (node instanceof JFXTabPane) {
                for (Tab tab : ((JFXTabPane) node).getTabs())
                    fieldDisable((AnchorPane) node, setDisable);
            } else if (node instanceof AnchorPane) {
                fieldDisable((AnchorPane) node, setDisable);
            }

        }
    }

}
