package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RelProduto_CodBarraVO extends RecursiveTreeObject<RelProduto_CodBarraVO> {

    IntegerProperty tabProduto_id, tabProduto_CodBarra_id;

    public RelProduto_CodBarraVO() {
    }

    public int getTabProduto_id() {
        return tabProduto_idProperty().get();
    }

    public IntegerProperty tabProduto_idProperty() {
        if (tabProduto_id == null) tabProduto_id = new SimpleIntegerProperty(0);
        return tabProduto_id;
    }

    public void setTabProduto_id(int tabProduto_id) {
        tabProduto_idProperty().set(tabProduto_id);
    }

    public int getTabProduto_CodBarra_id() {
        return tabProduto_CodBarra_idProperty().get();
    }

    public IntegerProperty tabProduto_CodBarra_idProperty() {
        if (tabProduto_CodBarra_id == null) tabProduto_CodBarra_id = new SimpleIntegerProperty(0);
        return tabProduto_CodBarra_id;
    }

    public void setTabProduto_CodBarra_id(int tabProduto_CodBarra_id) {
        tabProduto_CodBarra_idProperty().set(tabProduto_CodBarra_id);
    }
}
