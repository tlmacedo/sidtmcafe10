package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FiscalFreteSituacaoTributariaVO extends RecursiveTreeObject<FiscalFreteSituacaoTributariaVO> {

    StringProperty id, descricao;

    public FiscalFreteSituacaoTributariaVO() {
    }

    public String getId() {
        return idProperty().get();
    }

    public StringProperty idProperty() {
        if (id == null) id = new SimpleStringProperty("");
        return id;
    }

    public void setId(String id) {
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
        return String.format("%s - %s", idProperty().get(), descricaoProperty().get().toUpperCase());
    }
}
