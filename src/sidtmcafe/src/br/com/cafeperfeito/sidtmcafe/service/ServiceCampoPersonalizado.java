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

    public static void fieldClear(AnchorPane anchorPane) {
        for (Node node : anchorPane.getChildren()) {
            String vlrInicial = "", txtAcessivel = node.getAccessibleText();
            if (txtAcessivel.toLowerCase().contains("value:")) {
                if ((vlrInicial = ServiceFormatarDado.getFieldFormatPair(txtAcessivel, "value").getValue()) == null)
                    vlrInicial = "";
                if (node instanceof Label)
                    ((Label) node).setText(vlrInicial);
            }
            if (node instanceof JFXTextField)
                ((JFXTextField) node).setText(vlrInicial);
            else if (node instanceof JFXCheckBox)
                ((JFXCheckBox) node).setSelected(vlrInicial.equals("true")
                        || vlrInicial.equals("verdadeiro")
                        || vlrInicial.equals("true"));
            else if (node instanceof JFXComboBox)
                ((JFXComboBox) node).getSelectionModel().select(vlrInicial.equals("") ? -1 : Integer.parseInt(vlrInicial));
            else if (node instanceof JFXListView)
                ((JFXListView) node).getItems().clear();
            else if (node instanceof AnchorPane)
                fieldClear((AnchorPane) node);
            else if (node instanceof TitledPane)
                fieldClear((AnchorPane) ((TitledPane) node).getContent());
            else if (node instanceof TabPane)
                for (Tab tab : ((TabPane) node).getTabs())
                    fieldClear((AnchorPane) tab.getContent());
        }
    }

    public static void fieldDisable(AnchorPane anchorPane, boolean setDisable) {
        for (Node node : anchorPane.getChildren()) {
            String txtAcessivel = node.getAccessibleText() != null ? node.getAccessibleText() : "", vlrEditable = "";
            if (txtAcessivel.toLowerCase().contains("seteditable:"))
                if ((vlrEditable = ServiceFormatarDado.getFieldFormatPair(txtAcessivel, "seteditable").getValue()) == null)
                    vlrEditable = "";
            boolean fieldEditable = (vlrEditable.equals("") || vlrEditable.equals("true"));
            if (node instanceof DatePicker) {
                ((DatePicker) node).setDisable(setDisable);
                if (!setDisable)
                    ((DatePicker) node).setEditable(fieldEditable);
            } else if (node instanceof JFXTextField) {
                ((JFXTextField) node).setDisable(setDisable);
                if (!setDisable)
                    ((JFXTextField) node).setEditable(fieldEditable);
            } else if (node instanceof JFXTextArea) {
                ((JFXTextArea) node).setDisable(setDisable);
                if (!setDisable)
                    ((JFXTextArea) node).setEditable(fieldEditable);
            } else if (node instanceof TreeTableView) {
                ((TreeTableView) node).setDisable(setDisable);
                if (!setDisable)
                    ((TreeTableView) node).setEditable(fieldEditable);
            } else if (node instanceof JFXComboBox) {
                ((JFXComboBox) node).setDisable(setDisable);
            } else if (node instanceof JFXCheckBox) {
                ((JFXCheckBox) node).setDisable(setDisable);
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
                            ? Integer.parseInt(ServiceFormatarDado.getFieldFormatPair(node.getAccessibleText(), "len").getValue())
                            : 0;
                    String type = "";
                    if ((type = ServiceFormatarDado.getFieldFormatPair(node.getAccessibleText(), "type").getValue()) == null)
                        type = "TEXTO";
                    new ServiceFormatarDado().maskField((JFXTextField) node, len + type);
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

