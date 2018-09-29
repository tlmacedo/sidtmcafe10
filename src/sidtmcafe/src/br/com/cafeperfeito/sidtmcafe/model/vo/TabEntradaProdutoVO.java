package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;

public class TabEntradaProdutoVO extends RecursiveTreeObject<TabEntradaProdutoVO> {

    TabEmpresaVO lojaDestinoVO, fornecedorVO;
    FiscalModeloNfeCteVO modeloNfeCteVO;
    SisStatusNfeVO statusNfeVO;
    TabEntradaProduto_Fiscal_NfeVO fiscal_nfeVO;

    Timestamp dataEmissaoNfe, dataEntradaNfe;

    IntegerProperty id, lojaDestino_id, numeroNfe, serieNfe, modeloNfeCte_id, fornecedor_id, statusNfe_id, fiscal_id, frete_id;
    StringProperty chaveNfe;


    public TabEntradaProdutoVO() {
    }

    public TabEmpresaVO getLojaDestinoVO() {
        return lojaDestinoVO;
    }

    public void setLojaDestinoVO(TabEmpresaVO lojaDestinoVO) {
        this.lojaDestinoVO = lojaDestinoVO;
    }

    public TabEmpresaVO getFornecedorVO() {
        return fornecedorVO;
    }

    public void setFornecedorVO(TabEmpresaVO fornecedorVO) {
        this.fornecedorVO = fornecedorVO;
    }

    public FiscalModeloNfeCteVO getModeloNfeCteVO() {
        return modeloNfeCteVO;
    }

    public void setModeloNfeCteVO(FiscalModeloNfeCteVO modeloNfeCteVO) {
        this.modeloNfeCteVO = modeloNfeCteVO;
    }

    public SisStatusNfeVO getStatusNfeVO() {
        return statusNfeVO;
    }

    public void setStatusNfeVO(SisStatusNfeVO statusNfeVO) {
        this.statusNfeVO = statusNfeVO;
    }

    public TabEntradaProduto_Fiscal_NfeVO getFiscal_nfeVO() {
        return fiscal_nfeVO;
    }

    public void setFiscal_nfeVO(TabEntradaProduto_Fiscal_NfeVO fiscal_nfeVO) {
        this.fiscal_nfeVO = fiscal_nfeVO;
    }

    public Timestamp getDataEmissaoNfe() {
        return dataEmissaoNfe;
    }

    public void setDataEmissaoNfe(Timestamp dataEmissaoNfe) {
        this.dataEmissaoNfe = dataEmissaoNfe;
    }

    public Timestamp getDataEntradaNfe() {
        return dataEntradaNfe;
    }

    public void setDataEntradaNfe(Timestamp dataEntradaNfe) {
        this.dataEntradaNfe = dataEntradaNfe;
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

    public int getLojaDestino_id() {
        return lojaDestino_idProperty().get();
    }

    public IntegerProperty lojaDestino_idProperty() {
        if (lojaDestino_id == null) lojaDestino_id = new SimpleIntegerProperty(0);
        return lojaDestino_id;
    }

    public void setLojaDestino_id(int lojaDestino_id) {
        lojaDestino_idProperty().set(lojaDestino_id);
    }

    public int getNumeroNfe() {
        return numeroNfeProperty().get();
    }

    public IntegerProperty numeroNfeProperty() {
        if (numeroNfe == null) numeroNfe = new SimpleIntegerProperty(0);
        return numeroNfe;
    }

    public void setNumeroNfe(int numeroNfe) {
        numeroNfeProperty().set(numeroNfe);
    }

    public int getSerieNfe() {
        return serieNfeProperty().get();
    }

    public IntegerProperty serieNfeProperty() {
        if (serieNfe == null) serieNfe = new SimpleIntegerProperty(0);
        return serieNfe;
    }

    public void setSerieNfe(int serieNfe) {
        serieNfeProperty().set(serieNfe);
    }

    public int getModeloNfeCte_id() {
        return modeloNfeCte_idProperty().get();
    }

    public IntegerProperty modeloNfeCte_idProperty() {
        if (modeloNfeCte_id == null) modeloNfeCte_id = new SimpleIntegerProperty(0);
        return modeloNfeCte_id;
    }

    public void setModeloNfeCte_id(int modeloNfeCte_id) {
        modeloNfeCte_idProperty().set(modeloNfeCte_id);
    }

    public int getFornecedor_id() {
        return fornecedor_idProperty().get();
    }

    public IntegerProperty fornecedor_idProperty() {
        if (fornecedor_id == null) fornecedor_id = new SimpleIntegerProperty(0);
        return fornecedor_id;
    }

    public void setFornecedor_id(int fornecedor_id) {
        fornecedor_idProperty().set(fornecedor_id);
    }

    public int getStatusNfe_id() {
        return statusNfe_idProperty().get();
    }

    public IntegerProperty statusNfe_idProperty() {
        if (statusNfe_id == null) statusNfe_id = new SimpleIntegerProperty(0);
        return statusNfe_id;
    }

    public void setStatusNfe_id(int statusNfe_id) {
        statusNfe_idProperty().set(statusNfe_id);
    }

    public int getFiscal_id() {
        return fiscal_idProperty().get();
    }

    public IntegerProperty fiscal_idProperty() {
        if (fiscal_id == null) fiscal_id = new SimpleIntegerProperty(0);
        return fiscal_id;
    }

    public void setFiscal_id(int fiscal_id) {
        fiscal_idProperty().set(fiscal_id);
    }

    public int getFrete_id() {
        return frete_idProperty().get();
    }

    public IntegerProperty frete_idProperty() {
        if (frete_id == null) frete_id = new SimpleIntegerProperty(0);
        return frete_id;
    }

    public void setFrete_id(int frete_id) {
        frete_idProperty().set(frete_id);
    }

    public String getChaveNfe() {
        return chaveNfeProperty().get();
    }

    public StringProperty chaveNfeProperty() {
        if (chaveNfe == null) chaveNfe = new SimpleStringProperty("");
        return chaveNfe;
    }

    public void setChaveNfe(String chaveNfe) {
        chaveNfeProperty().set(chaveNfe);
    }
}
