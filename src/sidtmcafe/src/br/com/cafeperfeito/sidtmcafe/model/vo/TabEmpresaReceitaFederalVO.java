package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TabEmpresaReceitaFederalVO extends RecursiveTreeObject<TabEmpresaReceitaFederalVO> {

    IntegerProperty id, tabEmpresa_id, isAtividadePrincipal;
    StringProperty str_Key, str_Value;

    public TabEmpresaReceitaFederalVO() {
    }

    public TabEmpresaReceitaFederalVO(int id, int tabEmpresa_id, int isAtividadePrincipal, String str_Key, String str_Value) {
        this.id = new SimpleIntegerProperty(id);
        this.tabEmpresa_id = new SimpleIntegerProperty(tabEmpresa_id);
        this.isAtividadePrincipal = new SimpleIntegerProperty(isAtividadePrincipal);
        this.str_Key = new SimpleStringProperty(str_Key);
        this.str_Value = new SimpleStringProperty(str_Value);
    }

    public int getId() {
        return idProperty().get();
    }

    public IntegerProperty idProperty() {
        if (id == null) id = new SimpleIntegerProperty(0);
        return id;
    }

    public void setId(int id) {
        idProperty().set(id);
    }

    public int getTabEmpresa_id() {
        return tabEmpresa_idProperty().get();
    }

    public IntegerProperty tabEmpresa_idProperty() {
        if (tabEmpresa_id == null) tabEmpresa_id = new SimpleIntegerProperty(0);
        return tabEmpresa_id;
    }

    public void setTabEmpresa_id(int tabEmpresa_id) {
        tabEmpresa_idProperty().set(tabEmpresa_id);
    }

    public int getIsAtividadePrincipal() {
        return isAtividadePrincipalProperty().get();
    }

    public IntegerProperty isAtividadePrincipalProperty() {
        if (isAtividadePrincipal == null) isAtividadePrincipal = new SimpleIntegerProperty(0);
        return isAtividadePrincipal;
    }

    public void setIsAtividadePrincipal(int isAtividadePrincipal) {
        isAtividadePrincipalProperty().set(isAtividadePrincipal);
    }

    public String getStr_Key() {
        return str_KeyProperty().get();
    }

    public StringProperty str_KeyProperty() {
        if (str_Key == null) str_Key = new SimpleStringProperty("");
        return str_Key;
    }

    public void setStr_Key(String str_Key) {
        str_KeyProperty().set(str_Key);
    }

    public String getStr_Value() {
        return str_ValueProperty().get();
    }

    public StringProperty str_ValueProperty() {
        if (str_Value == null) str_Value = new SimpleStringProperty("");
        return str_Value;
    }

    public void setStr_Value(String str_Value) {
        str_ValueProperty().set(str_Value);
    }

    public String getDetalheReceitaFederal() {
        if (str_KeyProperty().get() != "")
            return "[item]: " + str_KeyProperty().get() +
                    ";    [descrição]: " + str_ValueProperty().get();
        return "";
    }

    @Override
    public String toString() {
        return str_KeyProperty().get() + " - " + str_ValueProperty().get();
    }
}
