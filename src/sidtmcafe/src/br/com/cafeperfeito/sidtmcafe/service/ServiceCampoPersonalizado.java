package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import com.jfoenix.controls.*;
import javafx.concurrent.Service;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

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

    public static void maxLenField(AnchorPane anchorPane) {
        int tamanho;
        for (Node node : anchorPane.getChildren())
            if (node instanceof JFXTextField) {
                if (node.getAccessibleText() != null)
                    if ((tamanho = Integer.parseInt(node.getAccessibleText().substring(0, 3))) > 0)
                        ServiceFormatarDado.maxField((JFXTextField) node, tamanho);
            } else if (node instanceof TitledPane) {
                maxLenField((AnchorPane) node);
            } else if (node instanceof TabPane) {
                for (Tab tab : ((TabPane) node).getTabs())
                    maxLenField((AnchorPane) node);
            } else if (node instanceof JFXTabPane) {
                for (Tab tab : ((JFXTabPane) node).getTabs())
                    maxLenField((AnchorPane) node);
            } else if (node instanceof AnchorPane) {
                maxLenField((AnchorPane) node);
            }
    }

    public static void clearField(AnchorPane anchorPane) {
        for (Node node : anchorPane.getChildren())

            if (node instanceof JFXTextField) {

            } else if (node instanceof TitledPane) {
                clearField((AnchorPane) node);
            } else if (node instanceof TabPane) {
                for (Tab tab : ((TabPane) node).getTabs())
                    clearField((AnchorPane) node);
            } else if (node instanceof JFXTabPane) {
                for (Tab tab : ((JFXTabPane) node).getTabs())
                    clearField((AnchorPane) node);
            } else if (node instanceof AnchorPane) {
                clearField((AnchorPane) node);
            }
    }

    public static void disableField(AnchorPane anchorPane, boolean setDisable) {
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
                disableField((AnchorPane) node, setDisable);
            } else if (node instanceof TabPane) {
                for (Tab tab : ((TabPane) node).getTabs())
                    disableField((AnchorPane) node, setDisable);
            } else if (node instanceof JFXTabPane) {
                for (Tab tab : ((JFXTabPane) node).getTabs())
                    disableField((AnchorPane) node, setDisable);
            } else if (node instanceof AnchorPane) {
                disableField((AnchorPane) node, setDisable);
            }

        }
    }

}
