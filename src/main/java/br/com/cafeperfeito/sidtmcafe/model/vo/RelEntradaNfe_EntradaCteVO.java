package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RelEntradaNfe_EntradaCteVO extends RecursiveTreeObject<RelEntradaNfe_EntradaCteVO> {

    IntegerProperty tabEntradaNfe_id, tabEntradaCte_id;

    public RelEntradaNfe_EntradaCteVO() {
    }

    public int getTabEntradaNfe_id() {
        return tabEntradaNfe_idProperty().get();
    }

    public IntegerProperty tabEntradaNfe_idProperty() {
        if (tabEntradaNfe_id == null) tabEntradaNfe_id = new SimpleIntegerProperty(0);
        return tabEntradaNfe_id;
    }

    public void setTabEntradaNfe_id(int tabEntradaNfe_id) {
        tabEntradaNfe_idProperty().set(tabEntradaNfe_id);
    }

    public int getTabEntradaCte_id() {
        return tabEntradaCte_idProperty().get();
    }

    public IntegerProperty tabEntradaCte_idProperty() {
        if (tabEntradaCte_id == null) tabEntradaCte_id = new SimpleIntegerProperty(0);
        return tabEntradaCte_id;
    }

    public void setTabEntradaCte_id(int tabEntradaCte_id) {
        tabEntradaCte_idProperty().set(tabEntradaCte_id);
    }
}
