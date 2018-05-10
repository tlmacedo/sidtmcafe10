package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SisSituacaoSistemaVO extends RecursiveTreeObject<SisSituacaoSistemaVO> {

    IntegerProperty id, classificacao;
    StringProperty descricao;

    public SisSituacaoSistemaVO() {
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

    public int getClassificacao() {
        return classificacaoProperty().get();
    }

    public IntegerProperty classificacaoProperty() {
        if (classificacao == null) classificacao = new SimpleIntegerProperty(0);
        return classificacao;
    }

    public void setClassificacao(int classificacao) {
        classificacaoProperty().set(classificacao);
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
        return descricaoProperty().get();
    }
    
}
