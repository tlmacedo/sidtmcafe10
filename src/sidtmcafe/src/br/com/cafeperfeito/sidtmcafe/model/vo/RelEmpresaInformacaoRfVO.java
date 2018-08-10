package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RelEmpresaInformacaoRfVO extends RecursiveTreeObject<RelEmpresaInformacaoRfVO> {

    IntegerProperty tabEmpresa_id, tabInformacaoReceitaFederal_id;

    public RelEmpresaInformacaoRfVO() {
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

    public int getTabInformacaoReceitaFederal_id() {
        return tabInformacaoReceitaFederal_idProperty().get();
    }

    public IntegerProperty tabInformacaoReceitaFederal_idProperty() {
        if (tabInformacaoReceitaFederal_id == null) tabInformacaoReceitaFederal_id = new SimpleIntegerProperty(0);
        return tabInformacaoReceitaFederal_id;
    }

    public void setTabInformacaoReceitaFederal_id(int tabInformacaoReceitaFederal_id) {
        tabInformacaoReceitaFederal_idProperty().set(tabInformacaoReceitaFederal_id);
    }
}
