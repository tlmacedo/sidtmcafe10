package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;

import java.sql.Timestamp;

public class TabEstoqueVO extends RecursiveTreeObject<TabEstoqueVO> {

    Timestamp validade, dataCadastro;
    IntegerProperty id, produto_id, qtd, usuarioCadastro_id;
    StringProperty lote, docOrigem, chaveNfeEntrada;
    DoubleProperty vlrBruto, vlrImposto, vlrFrete, vlrFreteImposto, vlrFreteLiquido, vlrLiquido;

    public TabEstoqueVO() {
    }

    public Timestamp getValidade() {
        return validade;
    }

    public void setValidade(Timestamp validade) {
        this.validade = validade;
    }

    public Timestamp getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Timestamp dataCadastro) {
        this.dataCadastro = dataCadastro;
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

    public int getProduto_id() {
        return produto_idProperty().get();
    }

    public IntegerProperty produto_idProperty() {
        if (produto_id == null) produto_id = new SimpleIntegerProperty(0);
        return produto_id;
    }

    public void setProduto_id(int produto_id) {
        produto_idProperty().set(produto_id);
    }

    public int getQtd() {
        return qtdProperty().get();
    }

    public IntegerProperty qtdProperty() {
        if (qtd == null) qtd = new SimpleIntegerProperty(0);
        return qtd;
    }

    public void setQtd(int qtd) {
        qtdProperty().set(qtd);
    }

    public int getUsuarioCadastro_id() {
        return usuarioCadastro_idProperty().get();
    }

    public IntegerProperty usuarioCadastro_idProperty() {
        if (usuarioCadastro_id == null) usuarioCadastro_id = new SimpleIntegerProperty(0);
        return usuarioCadastro_id;
    }

    public void setUsuarioCadastro_id(int usuarioCadastro_id) {
        usuarioCadastro_idProperty().set(usuarioCadastro_id);
    }

    public String getLote() {
        return loteProperty().get();
    }

    public StringProperty loteProperty() {
        if (lote == null) lote = new SimpleStringProperty("");
        return lote;
    }

    public void setLote(String lote) {
        loteProperty().set(lote);
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

    public String getChaveNfeEntrada() {
        return chaveNfeEntradaProperty().get();
    }

    public StringProperty chaveNfeEntradaProperty() {
        if (chaveNfeEntrada == null) chaveNfeEntrada = new SimpleStringProperty("");
        return chaveNfeEntrada;
    }

    public void setChaveNfeEntrada(String chaveNfeEntrada) {
        chaveNfeEntradaProperty().set(chaveNfeEntrada);
    }

    public double getVlrBruto() {
        return vlrBrutoProperty().get();
    }

    public DoubleProperty vlrBrutoProperty() {
        if (vlrBruto == null) vlrBruto = new SimpleDoubleProperty(0);
        return vlrBruto;
    }

    public void setVlrBruto(double vlrBruto) {
        vlrBrutoProperty().set(vlrBruto);
    }

    public double getVlrImposto() {
        return vlrImpostoProperty().get();
    }

    public DoubleProperty vlrImpostoProperty() {
        if (vlrImposto == null) vlrImposto = new SimpleDoubleProperty(0);
        return vlrImposto;
    }

    public void setVlrImposto(double vlrImposto) {
        vlrImpostoProperty().set(vlrImposto);
    }

    public double getVlrFrete() {
        return vlrFreteProperty().get();
    }

    public DoubleProperty vlrFreteProperty() {
        if (vlrFrete == null) vlrFrete = new SimpleDoubleProperty(0);
        return vlrFrete;
    }

    public void setVlrFrete(double vlrFrete) {
        vlrFreteProperty().set(vlrFrete);
    }

    public double getVlrFreteImposto() {
        return vlrFreteImpostoProperty().get();
    }

    public DoubleProperty vlrFreteImpostoProperty() {
        if (vlrFreteImposto == null) vlrFreteImposto = new SimpleDoubleProperty(0);
        return vlrFreteImposto;
    }

    public void setVlrFreteImposto(double vlrFreteImposto) {
        vlrFreteImpostoProperty().set(vlrFreteImposto);
    }

    public double getVlrFreteLiquido() {
        return vlrFreteLiquidoProperty().get();
    }

    public DoubleProperty vlrFreteLiquidoProperty() {
        if (vlrFreteLiquido == null) vlrFreteLiquido = new SimpleDoubleProperty(0);
        return vlrFreteLiquido;
    }

    public void setVlrFreteLiquido(double vlrFreteLiquido) {
        vlrFreteLiquidoProperty().set(vlrFreteLiquido);
    }

    public double getVlrLiquido() {
        return vlrLiquidoProperty().get();
    }

    public DoubleProperty vlrLiquidoProperty() {
        if (vlrLiquido == null) vlrLiquido = new SimpleDoubleProperty(0);
        return vlrLiquido;
    }

    public void setVlrLiquido(double vlrLiquido) {
        vlrLiquidoProperty().set(vlrLiquido);
    }
}
