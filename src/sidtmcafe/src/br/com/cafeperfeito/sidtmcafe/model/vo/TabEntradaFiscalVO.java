package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class TabEntradaFiscalVO extends RecursiveTreeObject<TabEntradaFiscalVO> {

    FiscalTributoSefazAmVO tributoSefazAmVO;

    IntegerProperty id, tributoSefazAM_id;
    StringProperty numControle, docOrigem;

    BigDecimal vlrNfe, vlrTributo, vlrMulta, vlrJuros, vlrTaxa;

    public TabEntradaFiscalVO() {
    }

    public FiscalTributoSefazAmVO getTributoSefazAmVO() {
        return tributoSefazAmVO;
    }

    public void setTributoSefazAmVO(FiscalTributoSefazAmVO tributoSefazAmVO) {
        this.tributoSefazAmVO = tributoSefazAmVO;
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

    public int getTributoSefazAM_id() {
        return tributoSefazAM_idProperty().get();
    }

    public IntegerProperty tributoSefazAM_idProperty() {
        if (tributoSefazAM_id == null) tributoSefazAM_id = new SimpleIntegerProperty(0);
        return tributoSefazAM_id;
    }

    public void setTributoSefazAM_id(int tributoSefazAM_id) {
        tributoSefazAM_idProperty().set(tributoSefazAM_id);
    }

    public String getNumControle() {
        return numControleProperty().get();
    }

    public StringProperty numControleProperty() {
        if (numControle == null) numControle = new SimpleStringProperty("");
        return numControle;
    }

    public void setNumControle(String numControle) {
        numControleProperty().set(numControle);
    }

    public String getDocOrigem() {
        return docOrigemProperty().get();
    }

    public StringProperty docOrigemProperty() {
        if (docOrigem == null) docOrigem = new SimpleStringProperty("");
        return docOrigem;
    }

    public void setDocOrigem(String docOrigem) {
        docOrigemProperty().set(docOrigem);
    }

    public BigDecimal getVlrNfe() {
        return vlrNfe;
    }

    public void setVlrNfe(BigDecimal vlrNfe) {
        this.vlrNfe = vlrNfe;
    }

    public BigDecimal getVlrTributo() {
        return vlrTributo;
    }

    public void setVlrTributo(BigDecimal vlrTributo) {
        this.vlrTributo = vlrTributo;
    }

    public BigDecimal getVlrMulta() {
        return vlrMulta;
    }

    public void setVlrMulta(BigDecimal vlrMulta) {
        this.vlrMulta = vlrMulta;
    }

    public BigDecimal getVlrJuros() {
        return vlrJuros;
    }

    public void setVlrJuros(BigDecimal vlrJuros) {
        this.vlrJuros = vlrJuros;
    }

    public BigDecimal getVlrTaxa() {
        return vlrTaxa;
    }

    public void setVlrTaxa(BigDecimal vlrTaxa) {
        this.vlrTaxa = vlrTaxa;
    }
}
