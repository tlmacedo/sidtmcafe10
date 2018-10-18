package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class TabEntradaNfeItemVO extends RecursiveTreeObject<TabEntradaNfeItemVO> {

    IntegerProperty id, tabEntradaNfe_id, tabProduto_id, qtd, estoque, varejo;
    StringProperty codProduto, descricao;

    BigDecimal peso, vlrFabrica, vlrTotalBruto, vlrImposto, vlrDesconto, vlrLiquido;

    public TabEntradaNfeItemVO() {
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

    public int getTabEntradaNfe_id() {
        return tabEntradaNfe_idProperty().get();
    }

    public IntegerProperty tabEntradaNfe_idProperty() {
        if (tabEntradaNfe_id == null) tabEntradaNfe_id = new SimpleIntegerProperty(0);
        return tabEntradaNfe_id;
    }

    public void setTabEntradaNfe_id(int tabEntradaNfe_id) {
        tabEntradaNfe_idProperty().set(tabEntradaNfe_id);
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

    public int getEstoque() {
        return estoqueProperty().get();
    }

    public IntegerProperty estoqueProperty() {
        if (estoque == null) estoque = new SimpleIntegerProperty(0);
        return estoque;
    }

    public void setEstoque(int estoque) {
        estoqueProperty().set(estoque);
    }

    public int getVarejo() {
        return varejoProperty().get();
    }

    public IntegerProperty varejoProperty() {
        if (varejo == null) varejo = new SimpleIntegerProperty(0);
        return varejo;
    }

    public void setVarejo(int varejo) {
        varejoProperty().set(varejo);
    }

    public String getCodProduto() {
        return codProdutoProperty().get();
    }

    public StringProperty codProdutoProperty() {
        if (codProduto == null) codProduto = new SimpleStringProperty("");
        return codProduto;
    }

    public void setCodProduto(String codProduto) {
        codProdutoProperty().set(codProduto);
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

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public BigDecimal getVlrFabrica() {
        return vlrFabrica;
    }

    public void setVlrFabrica(BigDecimal vlrFabrica) {
        this.vlrFabrica = vlrFabrica;
    }

    public BigDecimal getVlrTotalBruto() {
        return vlrTotalBruto;
    }

    public void setVlrTotalBruto(BigDecimal vlrTotalBruto) {
        this.vlrTotalBruto = vlrTotalBruto;
    }

    public BigDecimal getVlrImposto() {
        return vlrImposto;
    }

    public void setVlrImposto(BigDecimal vlrImposto) {
        this.vlrImposto = vlrImposto;
    }

    public BigDecimal getVlrDesconto() {
        return vlrDesconto;
    }

    public void setVlrDesconto(BigDecimal vlrDesconto) {
        this.vlrDesconto = vlrDesconto;
    }

    public BigDecimal getVlrLiquido() {
        return vlrLiquido;
    }

    public void setVlrLiquido(BigDecimal vlrLiquido) {
        this.vlrLiquido = vlrLiquido;
    }
}
