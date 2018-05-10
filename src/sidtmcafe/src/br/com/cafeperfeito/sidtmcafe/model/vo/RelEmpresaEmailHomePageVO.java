package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RelEmpresaEmailHomePageVO extends RecursiveTreeObject<RelEmpresaEmailHomePageVO> {

    IntegerProperty tabEmpresa_id, tabEmailHomePage_id;

    public RelEmpresaEmailHomePageVO() {
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

    public int getTabEmailHomePage_id() {
        return tabEmailHomePage_idProperty().get();
    }

    public IntegerProperty tabEmailHomePage_idProperty() {
        if (tabEmailHomePage_id == null) tabEmailHomePage_id = new SimpleIntegerProperty(0);
        return tabEmailHomePage_id;
    }

    public void setTabEmailHomePage_id(int tabEmailHomePage_id) {
        tabEmailHomePage_idProperty().set(tabEmailHomePage_id);
    }
}
