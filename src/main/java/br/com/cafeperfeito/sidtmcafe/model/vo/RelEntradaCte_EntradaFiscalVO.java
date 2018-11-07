package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RelEntradaCte_EntradaFiscalVO extends RecursiveTreeObject<RelEntradaCte_EntradaFiscalVO> {

    IntegerProperty tabEntradaCte_id, tabEntradaFiscal_id;

    public RelEntradaCte_EntradaFiscalVO() {
    }

    public int getTabEntradaCte_id() {
        return tabEntradaCte_idProperty().get();
    }

    public IntegerProperty tabEntradaCte_idProperty() {
        if (tabEntradaFiscal_id == null) tabEntradaFiscal_id = new SimpleIntegerProperty(0);
        return tabEntradaCte_id;
    }

    public void setTabEntradaCte_id(int tabEntradaCte_id) {
        tabEntradaCte_idProperty().set(tabEntradaCte_id);
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
