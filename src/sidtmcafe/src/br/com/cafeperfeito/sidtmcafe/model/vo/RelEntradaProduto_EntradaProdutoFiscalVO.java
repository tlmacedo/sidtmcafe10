package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RelEntradaProduto_EntradaProdutoFiscalVO extends RecursiveTreeObject<RelEntradaProduto_EntradaProdutoFiscalVO> {

    IntegerProperty tabEntradaProduto_id, tabEntradaProdutoFiscal_id;

    public RelEntradaProduto_EntradaProdutoFiscalVO() {
    }

    public int getTabEntradaProduto_id() {
        return tabEntradaProduto_idProperty().get();
    }

    public IntegerProperty tabEntradaProduto_idProperty() {
        if (tabEntradaProduto_id == null) tabEntradaProduto_id = new SimpleIntegerProperty(0);
        return tabEntradaProduto_id;
    }

    public void setTabEntradaProduto_id(int tabEntradaProduto_id) {
        tabEntradaProduto_idProperty().set(tabEntradaProduto_id);
    }

    public int getTabEntradaProdutoFiscal_id() {
        return tabEntradaProdutoFiscal_idProperty().get();
    }

    public IntegerProperty tabEntradaProdutoFiscal_idProperty() {
        if (tabEntradaProdutoFiscal_id == null) tabEntradaProdutoFiscal_id = new SimpleIntegerProperty(0);
        return tabEntradaProdutoFiscal_id;
    }

    public void setTabEntradaProdutoFiscal_id(int tabEntradaProdutoFiscal_id) {
        tabEntradaProdutoFiscal_idProperty().set(tabEntradaProdutoFiscal_id);
    }
}
