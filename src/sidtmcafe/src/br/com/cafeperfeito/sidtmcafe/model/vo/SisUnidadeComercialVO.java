package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SisUnidadeComercialVO extends RecursiveTreeObject<SisUnidadeComercialVO> {

    IntegerProperty id;
    StringProperty descricao, sigla;

    public SisUnidadeComercialVO() {
    }

    public SisUnidadeComercialVO(int id) {
        this.id = new SimpleIntegerProperty(-1);
        this.descricao = new SimpleStringProperty("");
        this.sigla = new SimpleStringProperty("");
    }

    public int getId() {
        return idProperty().get();
    }

    public IntegerProperty idProperty() {
        if (id == null) id = new SimpleIntegerProperty(0);
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

    public String getSigla() {
        return siglaProperty().get();
    }

    public StringProperty siglaProperty() {
        if (sigla == null) sigla = new SimpleStringProperty("");
        return sigla;
    }

    public void setSigla(String sigla) {
        siglaProperty().set(sigla);
    }

    @Override
    public String toString() {
        return siglaProperty().get();
    }

}
