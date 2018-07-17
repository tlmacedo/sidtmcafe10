package br.com.cafeperfeito.sidtmcafe.model.vo;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SisTelefoneOperadoraVO extends RecursiveTreeObject<SisTelefoneOperadoraVO> implements Constants {

    IntegerProperty id, tipo, codigoDDD;
    StringProperty descricao, codigoWs;

    public SisTelefoneOperadoraVO() {
    }

    public SisTelefoneOperadoraVO(String wsDetalhes, String codigoWs) {
        String[] wsDetalhe = wsDetalhes.replace(" ", "").split("-");
        this.descricao = new SimpleStringProperty(wsDetalhe[0]);
        this.tipo = new SimpleIntegerProperty(wsDetalhe[1].toLowerCase().contains("fixo") ? 0 : 1);
        this.codigoDDD = new SimpleIntegerProperty(DDD_SISTEMA);
        this.codigoWs = new SimpleStringProperty(codigoWs);
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

    public int getTipo() {
        return tipoProperty().get();
    }

    public IntegerProperty tipoProperty() {
        if (tipo == null) tipo = new SimpleIntegerProperty(0);
        return tipo;
    }

    public void setTipo(int tipo) {
        tipoProperty().set(tipo);
    }

    public int getCodigoDDD() {
        return codigoDDDProperty().get();
    }

    public IntegerProperty codigoDDDProperty() {
        if (codigoDDD == null) codigoDDD = new SimpleIntegerProperty(0);
        return codigoDDD;
    }

    public void setCodigoDDD(int codigoDDD) {
        codigoDDDProperty().set(codigoDDD);
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

    public String getCodigoWs() {
        return codigoWsProperty().get();
    }

    public StringProperty codigoWsProperty() {
        if (codigoWs == null) codigoWs = new SimpleStringProperty("");
        return codigoWs;
    }

    public void setCodigoWs(String codigoWs) {
        codigoWsProperty().set(codigoWs);
    }

    @Override
    public String toString() {
        return descricaoProperty().get();
    }
}
