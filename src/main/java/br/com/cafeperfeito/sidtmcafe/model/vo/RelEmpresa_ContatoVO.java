package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RelEmpresa_ContatoVO extends RecursiveTreeObject<RelEmpresa_ContatoVO> {

    IntegerProperty tabEmpresa_id, tabContato_id;

    public RelEmpresa_ContatoVO() {
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

    public int getTabContato_id() {
        return tabContato_idProperty().get();
    }

    public IntegerProperty tabContato_idProperty() {
        if (tabContato_id == null) tabContato_id = new SimpleIntegerProperty(0);
        return tabContato_id;
    }

    public void setTabContato_id(int tabContato_id) {
        tabContato_idProperty().set(tabContato_id);
    }
}
