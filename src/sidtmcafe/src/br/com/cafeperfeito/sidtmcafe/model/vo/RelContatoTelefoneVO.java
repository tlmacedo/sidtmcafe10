package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RelContatoTelefoneVO extends RecursiveTreeObject<RelContatoTelefoneVO> {

    IntegerProperty tabContato_id, tabTelefone_id;

    public RelContatoTelefoneVO() {
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
