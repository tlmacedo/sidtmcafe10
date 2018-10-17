package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TabEntradaCteVO extends RecursiveTreeObject<TabEntradaCteVO> {

    FiscalFreteTomadorServicoVO tomadorServicoVO;
    FiscalModeloNfeCteVO modeloCteVO;
    FiscalFreteSituacaoTributariaVO situacaoTributariaVO;
    TabEmpresaVO transportadoraVO;

    Timestamp dataEmissaoCte;

    IntegerProperty id, tomadorServico_id, numeroCte, serieCte, modeloCte_id, situacaoTributaria_id, transportadora_id, qtdVolume;
    StringProperty chaveCte;
    BigDecimal vlrCte, pesoBruto, vlrFreteBruto, vlrImpostoFrete;

    public TabEntradaCteVO() {
    }

    public FiscalFreteTomadorServicoVO getTomadorServicoVO() {
        return tomadorServicoVO;
    }

    public void setTomadorServicoVO(FiscalFreteTomadorServicoVO tomadorServicoVO) {
        this.tomadorServicoVO = tomadorServicoVO;
    }

    public FiscalModeloNfeCteVO getModeloCteVO() {
        return modeloCteVO;
    }

    public void setModeloCteVO(FiscalModeloNfeCteVO modeloCteVO) {
        this.modeloCteVO = modeloCteVO;
    }

    public FiscalFreteSituacaoTributariaVO getSituacaoTributariaVO() {
        return situacaoTributariaVO;
    }

    public void setSituacaoTributariaVO(FiscalFreteSituacaoTributariaVO situacaoTributariaVO) {
        this.situacaoTributariaVO = situacaoTributariaVO;
    }

    public TabEmpresaVO getTransportadoraVO() {
        return transportadoraVO;
    }

    public void setTransportadoraVO(TabEmpresaVO transportadoraVO) {
        this.transportadoraVO = transportadoraVO;
    }

    public Timestamp getDataEmissaoCte() {
        return dataEmissaoCte;
    }

    public void setDataEmissaoCte(Timestamp dataEmissaoCte) {
        this.dataEmissaoCte = dataEmissaoCte;
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

    public int getTomadorServico_id() {
        return tomadorServico_idProperty().get();
    }

    public IntegerProperty tomadorServico_idProperty() {
        if (id == null) id = new SimpleIntegerProperty(0);
        return tomadorServico_id;
    }

    public void setTomadorServico_id(int tomadorServico_id) {
        tomadorServico_idProperty().set(tomadorServico_id);
    }

    public int getNumeroCte() {
        return numeroCteProperty().get();
    }

    public IntegerProperty numeroCteProperty() {
        if (id == null) id = new SimpleIntegerProperty(0);
        return numeroCte;
    }

    public void setNumeroCte(int numeroCte) {
        numeroCteProperty().set(numeroCte);
    }

    public int getSerieCte() {
        return serieCteProperty().get();
    }

    public IntegerProperty serieCteProperty() {
        if (serieCte == null) serieCte = new SimpleIntegerProperty(0);
        return serieCte;
    }

    public void setSerieCte(int serieCte) {
        serieCteProperty().set(serieCte);
    }

    public int getModeloCte_id() {
        return modeloCte_idProperty().get();
    }

    public IntegerProperty modeloCte_idProperty() {
        if (modeloCte_id == null) modeloCte_id = new SimpleIntegerProperty(0);
        return modeloCte_id;
    }

    public void setModeloCte_id(int modeloCte_id) {
        modeloCte_idProperty().set(modeloCte_id);
    }

    public int getSituacaoTributaria_id() {
        return situacaoTributaria_idProperty().get();
    }

    public IntegerProperty situacaoTributaria_idProperty() {
        if (situacaoTributaria_id == null) situacaoTributaria_id = new SimpleIntegerProperty(0);
        return situacaoTributaria_id;
    }

    public void setSituacaoTributaria_id(int situacaoTributaria_id) {
        situacaoTributaria_idProperty().set(situacaoTributaria_id);
    }

    public int getTransportadora_id() {
        return transportadora_idProperty().get();
    }

    public IntegerProperty transportadora_idProperty() {
        if (transportadora_id == null) transportadora_id = new SimpleIntegerProperty(0);
        return transportadora_id;
    }

    public void setTransportadora_id(int transportadora_id) {
        transportadora_idProperty().set(transportadora_id);
    }

    public int getQtdVolume() {
        return qtdVolumeProperty().get();
    }

    public IntegerProperty qtdVolumeProperty() {
        if (qtdVolume == null) qtdVolume = new SimpleIntegerProperty(0);
        return qtdVolume;
    }

    public void setQtdVolume(int qtdVolume) {
        qtdVolumeProperty().set(qtdVolume);
    }

    public String getChaveCte() {
        return chaveCteProperty().get();
    }

    public StringProperty chaveCteProperty() {
        if (chaveCte == null) chaveCte = new SimpleStringProperty("");
        return chaveCte;
    }

    public void setChaveCte(String chaveCte) {
        chaveCteProperty().set(chaveCte);
    }

    public BigDecimal getVlrCte() {
        return vlrCte;
    }

    public void setVlrCte(BigDecimal vlrCte) {
        vlrCte = vlrCte;
    }

    public BigDecimal getPesoBruto() {
        return pesoBruto;
    }

    public void setPesoBruto(BigDecimal pesoBruto) {
        pesoBruto = pesoBruto;
    }

    public BigDecimal getVlrFreteBruto() {
        return vlrFreteBruto;
    }

    public void setVlrFreteBruto(BigDecimal vlrFreteBruto) {
        vlrFreteBruto = vlrFreteBruto;
    }

    public BigDecimal getVlrImpostoFrete() {
        return vlrImpostoFrete;
    }

    public void setVlrImpostoFrete(BigDecimal vlrImpostoFrete) {
        vlrImpostoFrete = vlrImpostoFrete;
    }
}
