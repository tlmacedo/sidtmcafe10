package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FiscalFreteSituacaoTributariaVO extends RecursiveTreeObject<FiscalFreteSituacaoTributariaVO> {

    IntegerProperty id;
    StringProperty descricao;

    public FiscalFreteSituacaoTributariaVO() {
    }

    public int getId() {
        return idProperty().get();
    }

    public IntegerProperty idProperty() {
        if (id==null)id = new SimpleIntegerProperty(0);
        return id;
    }

    public void setId(int id) {
        idProperty().set(id);
    }

    public String getDescricao() {
        return descricaoProperty().get();
    }

    public StringProperty descricaoProperty() {
        if (descricao == null) descricao = new SimpleStringProperty("");
        return descricao;
    }

    public void setDescricao(String descricao) {
        descricaoProperty().set(descricao);
    }

    @Override
    public String toString() {
        return String.format("%s - %s", String.format("%02d", idProperty().get()), descricaoProperty().get().toUpperCase());
    }
}
