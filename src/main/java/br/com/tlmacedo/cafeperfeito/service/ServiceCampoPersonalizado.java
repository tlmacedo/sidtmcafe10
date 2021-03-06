package br.com.tlmacedo.cafeperfeito.service;

import br.com.tlmacedo.cafeperfeito.interfaces.Constants;
import com.jfoenix.controls.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.time.LocalDate;
import java.util.HashMap;

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
            String vlrInicial = "";
            if (node.getAccessibleText() != null) {
                HashMap<String, String> hashMap = ServiceFormatarDado.getFieldFormatMap(node.getAccessibleText());
                if (hashMap.containsKey("value")) {
                    if ((vlrInicial = hashMap.get("value")) == null)
                        vlrInicial = "";
                    if (node instanceof Label)
                        ((Label) node).setText(vlrInicial);
                }
            }
            if (node instanceof JFXTextField)
                ((JFXTextField) node).setText(vlrInicial);
            else if (node instanceof JFXCheckBox)
                ((JFXCheckBox) node).setSelected(vlrInicial.equals("true")
                        || vlrInicial.equals("verdadeiro"));
            else if (node instanceof JFXComboBox)
                ((JFXComboBox) node).getSelectionModel().select(vlrInicial.equals("") ? -1 : Integer.parseInt(vlrInicial));
            else if (node instanceof ImageView)
                ((ImageView) node).setImage(null);
            else if (node instanceof DatePicker)
                if (vlrInicial.equals("")) {
                    ((DatePicker) node).setValue(LocalDate.now());
                } else {
                    ((DatePicker) node).setValue(LocalDate.parse(vlrInicial));
                }
            else if (node instanceof Circle)
                ((Circle) node).setFill(FUNDO_RADIAL_GRADIENT);
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
            boolean setEditable = true;
            if (node.getAccessibleText() != null) {
                HashMap<String, String> hashMap = ServiceFormatarDado.getFieldFormatMap(node.getAccessibleText());
                if (hashMap.containsKey("seteditable"))
                    setEditable = (hashMap.get("seteditable").equals("true") || hashMap.get("seteditable").equals(""));
            }
            if (node instanceof DatePicker) {
                ((DatePicker) node).setDisable(setDisable);
                if (setDisable == false)
                    ((DatePicker) node).setEditable(setEditable);
            } else if (node instanceof JFXTextField) {
                ((JFXTextField) node).setDisable(setDisable);
                if (setDisable == false)
                    ((JFXTextField) node).setEditable(setEditable);
            } else if (node instanceof JFXTextArea) {
                ((JFXTextArea) node).setDisable(setDisable);
                if (setDisable == false)
                    ((JFXTextArea) node).setEditable(setEditable);
            } else if (node instanceof TreeTableView) {
                ((TreeTableView) node).setDisable(setDisable);
                if (setDisable == false)
                    ((TreeTableView) node).setEditable(setEditable);
            } else if (node instanceof JFXComboBox) {
                ((JFXComboBox) node).setDisable(setDisable);
            } else if (node instanceof ComboBox) {
                ((ComboBox) node).setDisable(setDisable);
            } else if (node instanceof ImageView) {
                ((ImageView) node).setDisable(setDisable);
            } else if (node instanceof Circle) {
                ((Circle) node).setDisable(setDisable);
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
            node.getId();
            if (node instanceof JFXTextField && node.getAccessibleText() != null) {
                int len = 0, decimal = 0;
                String type = "";
                HashMap<String, String> hashMap = ServiceFormatarDado.getFieldFormatMap(node.getAccessibleText());
                if (hashMap.containsKey("len"))
                    len = hashMap.get("len").equals("") ? 0 : Integer.parseInt(hashMap.get("len"));
                if (hashMap.containsKey("type")) {
                    if ((type = hashMap.get("type")).equals(""))
                        type = "TEXTO";
                    if (type.contains("numero") || type.contains("moeda") || type.contains("valor") || type.contains("peso")) {
                        if (hashMap.containsKey("decimal"))
                            decimal = hashMap.get("decimal").equals("") ? 0 : Integer.parseInt(hashMap.get("decimal"));
                        if (type.contains("numero") && decimal == 0) {
                            new ServiceFormatarDado().maskField((JFXTextField) node, len, type, decimal);
                        } else {
                            new ServiceFormatarDado().maskMoedaField((JFXTextField) node, len, type, decimal);
                        }
                    } else {
                        new ServiceFormatarDado().maskField((JFXTextField) node, len, type, decimal);
                    }
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

