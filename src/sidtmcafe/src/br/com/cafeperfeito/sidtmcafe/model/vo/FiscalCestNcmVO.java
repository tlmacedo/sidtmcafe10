package br.com.cafeperfeito.sidtmcafe.model.vo;

import br.com.cafeperfeito.sidtmcafe.service.ServiceFormatarDado;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FiscalCestNcmVO extends RecursiveTreeObject<FiscalCestNcmVO> {

    IntegerProperty id;
    StringProperty segmento, descricao, cest, ncm;

    public FiscalCestNcmVO() {
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

    public String getSegmento() {
        return segmentoProperty().get();
    }

    public StringProperty segmentoProperty() {
        if (segmento == null) segmento = new SimpleStringProperty("");
        return segmento;
    }

    public void setSegmento(String segmento) {
        segmentoProperty().set(segmento);
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

    public String getCest() {
        return cestProperty().get();
    }

    public StringProperty cestProperty() {
        if (cest == null) cest = new SimpleStringProperty("");
        return cest;
    }

    public void setCest(String cest) {
        cestProperty().set(cest);
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

    public String getDetalheCestNcm() {
        if (descricaoProperty().get() != "")
            return "[Segmento]: " + segmentoProperty().get() +
                    ";[descrição]: " + descricaoProperty().get() +
                    ";[Cest]: " + ServiceFormatarDado.getValorFormatado(cestProperty().get(), "cest") +
                    "  [Ncm]:" + ServiceFormatarDado.getValorFormatado(ncmProperty().get(), "ncm");
        return "";
    }

    @Override
    public String toString() {
        return descricaoProperty().get();
    }
}
