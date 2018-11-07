package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TabEntradaNfeVO extends RecursiveTreeObject<TabEntradaNfeVO> {

    TabEmpresaVO lojaDestinoVO, fornecedorVO;
    FiscalModeloNfeCteVO modeloNfeVO;
    SisStatusNfeVO statusNfeVO;

    Timestamp dataEmissaoNfe, dataEntradaNfe;

    IntegerProperty id, lojaDestino_id, numeroNfe, serieNfe, modeloNfe_id, fornecedor_id, statusNfe_id;
    StringProperty chaveNfe;


    public TabEntradaNfeVO() {
        this.dataEmissaoNfe = Timestamp.valueOf(LocalDateTime.now());
        this.dataEntradaNfe = Timestamp.valueOf(LocalDateTime.now());
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

    public FiscalModeloNfeCteVO getModeloNfeVO() {
        return modeloNfeVO;
    }

    public void setModeloNfeVO(FiscalModeloNfeCteVO modeloNfeVO) {
        this.modeloNfeVO = modeloNfeVO;
    }

    public SisStatusNfeVO getStatusNfeVO() {
        return statusNfeVO;
    }

    public void setStatusNfeVO(SisStatusNfeVO statusNfeVO) {
        this.statusNfeVO = statusNfeVO;
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

    public int getModeloNfe_id() {
        return modeloNfe_idProperty().get();
    }

    public IntegerProperty modeloNfe_idProperty() {
        if (modeloNfe_id == null) modeloNfe_id = new SimpleIntegerProperty(0);
        return modeloNfe_id;
    }

    public void setModeloNfe_id(int modeloNfe_id) {
        modeloNfe_idProperty().set(modeloNfe_id);
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

    public String getChaveNfe() {
        return chaveNfeProperty().get();
    }

    public StringProperty chaveNfeProperty() {
        if (chaveNfe == null) chaveNfe = new SimpleStringProperty("");
        return chaveNfe;
    }

    public void setChaveNfe(String chaveNfe) {
        chaveNfeProperty().set(chaveNfe.replaceAll("\\W", ""));
    }
}
