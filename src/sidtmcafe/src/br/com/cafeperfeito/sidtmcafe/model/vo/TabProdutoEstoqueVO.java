package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TabProdutoEstoqueVO extends RecursiveTreeObject<TabProdutoEstoqueVO> {

    TabColaboradorVO usuarioCadastroVO;

    IntegerProperty id, tabProduto_id, qtd, usuarioCadastro_id;
    StringProperty lote, docOrigem, chaveNfeEntrada;

    Timestamp validade, dataCadastro;

    BigDecimal vlrBruto, vlrImposto, vlrCteBruto, vlrCteImposto, vlrCteLiquido, vlrLiquido;

    public TabProdutoEstoqueVO(int tabProduto_id) {
        this.id = new SimpleIntegerProperty(0);
        this.tabProduto_id = new SimpleIntegerProperty(tabProduto_id);
        this.qtd = new SimpleIntegerProperty(0);
    }

    public TabColaboradorVO getUsuarioCadastroVO() {
        return usuarioCadastroVO;
    }

    public void setUsuarioCadastroVO(TabColaboradorVO usuarioCadastroVO) {
        this.usuarioCadastroVO = usuarioCadastroVO;
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

    public int getTabProduto_id() {
        return tabProduto_idProperty().get();
    }

    public IntegerProperty tabProduto_idProperty() {
        if (tabProduto_id == null) tabProduto_id = new SimpleIntegerProperty(0);
        return tabProduto_id;
    }

    public void setTabProduto_id(int tabProduto_id) {
        tabProduto_idProperty().set(tabProduto_id);
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

    public BigDecimal getVlrBruto() {
        return vlrBruto;
    }

    public void setVlrBruto(BigDecimal vlrBruto) {
        this.vlrBruto = vlrBruto;
    }

    public BigDecimal getVlrImposto() {
        return vlrImposto;
    }

    public void setVlrImposto(BigDecimal vlrImposto) {
        this.vlrImposto = vlrImposto;
    }

    public BigDecimal getVlrCteBruto() {
        return vlrCteBruto;
    }

    public void setVlrCteBruto(BigDecimal vlrCteBruto) {
        this.vlrCteBruto = vlrCteBruto;
    }

    public BigDecimal getVlrCteImposto() {
        return vlrCteImposto;
    }

    public void setVlrCteImposto(BigDecimal vlrCteImposto) {
        this.vlrCteImposto = vlrCteImposto;
    }

    public BigDecimal getVlrCteLiquido() {
        return vlrCteLiquido;
    }

    public void setVlrCteLiquido(BigDecimal vlrCteLiquido) {
        this.vlrCteLiquido = vlrCteLiquido;
    }

    public BigDecimal getVlrLiquido() {
        return vlrLiquido;
    }

    public void setVlrLiquido(BigDecimal vlrLiquido) {
        this.vlrLiquido = vlrLiquido;
    }
}
