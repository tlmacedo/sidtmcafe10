package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RelEntradaNfe_EntradaFiscalVO extends RecursiveTreeObject<RelEntradaNfe_EntradaFiscalVO> {

    IntegerProperty tabEntradaNfe_id, tabEntradaFiscal_id;

    public RelEntradaNfe_EntradaFiscalVO() {
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

    public int getTabEntradaFiscal_id() {
        return tabEntradaFiscal_idProperty().get();
    }

    public IntegerProperty tabEntradaFiscal_idProperty() {
        if (tabEntradaFiscal_id == null) tabEntradaFiscal_id = new SimpleIntegerProperty(0);
        return tabEntradaFiscal_id;
    }

    public void setTabEntradaFiscal_id(int tabEntradaFiscal_id) {
        tabEntradaFiscal_idProperty().set(tabEntradaFiscal_id);
    }
}
