package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RelEmpresaTelefoneVO extends RecursiveTreeObject<RelEmpresaTelefoneVO> {

    IntegerProperty tabEmpresa_id, tabTelefone_id;

    public RelEmpresaTelefoneVO() {
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

    public int getTabTelefone_id() {
        return tabTelefone_idProperty().get();
    }

    public IntegerProperty tabTelefone_idProperty() {
        if (tabTelefone_id == null) tabTelefone_id = new SimpleIntegerProperty(0);
        return tabTelefone_id;
    }

    public void setTabTelefone_id(int tabTelefone_id) {
        tabTelefone_idProperty().set(tabTelefone_id);
    }
}
