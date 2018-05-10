package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;

public class TabEmailHomePageVO extends RecursiveTreeObject<TabEmailHomePageVO> {

    IntegerProperty id;
    StringProperty descricao;
    BooleanProperty isEmail;

    public TabEmailHomePageVO() {
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

    public boolean isIsEmail() {
        return isEmailProperty().get();
    }

    public BooleanProperty isEmailProperty() {
        if (isEmail == null) isEmail = new SimpleBooleanProperty(false);
        return isEmail;
    }

    public void setIsEmail(boolean isEmail) {
        isEmailProperty().set(isEmail);
    }

    @Override
    public String toString() {
        return descricaoProperty().get();
    }

}
