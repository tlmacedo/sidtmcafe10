package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WsEanCosmosVO extends RecursiveTreeObject<WsEanCosmosVO> {

    StringProperty message, descricao, ncm;

    public WsEanCosmosVO() {
    }

    public String getMessage() {
        return messageProperty().get();
    }

    public StringProperty messageProperty() {
        if (message == null) message = new SimpleStringProperty("");
        return message;
    }

    public void setMessage(String message) {
        messageProperty().set(message);
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

    public String getNcm() {
        return ncmProperty().get();
    }

    public StringProperty ncmProperty() {
        if (ncm == null) ncm = new SimpleStringProperty("");
        return ncm;
    }

    public void setNcm(String ncm) {
        ncmProperty().set(ncm);
    }
}
