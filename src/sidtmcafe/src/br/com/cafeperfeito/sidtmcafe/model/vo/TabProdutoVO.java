package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;

import java.sql.Timestamp;
import java.util.List;

public class TabProdutoVO extends RecursiveTreeObject<TabProdutoVO> {

    SisUnidadeComercialVO sisUnidadeComercialVO;
    SisSituacaoSistemaVO sisSituacaoSistemaVO;
    FiscalCestNcmVO fiscalCestNcmVO;
    FiscalCSTOrigemVO fiscalCSTOrigemVO;
    FiscalICMSVO fiscalICMSVO;
    FiscalPISCOFINSVO fiscalPISVO;
    FiscalPISCOFINSVO fiscalCOFINSVO;
    TabColaboradorVO usuarioCadastroVO;
    TabColaboradorVO usuarioAtualizacaoVO;
    List<TabProdutoEanVO> tabProdutoEanVOList;

    Timestamp dataCadastro, dataAtualizacao;

    IntegerProperty id, sisUnidadeComercial_id, sisSituacaoSistema_id, varejo, fiscalCestNcm_id, fiscalCSTOrigem_id,
            fiscalICMS_id, fiscalPIS_id, fiscalCOFINS_id, usuarioCadastro_id, usuarioAtualizacao_id;

    StringProperty codigo, descricao, fiscalNcm, nfeGenero;

    DoubleProperty peso, precoFabrica, precoVenda, precoUltimoFrete, comissao;

    public TabProdutoVO() {
    }

    public SisUnidadeComercialVO getSisUnidadeComercialVO() {
        return sisUnidadeComercialVO;
    }

    public void setSisUnidadeComercialVO(SisUnidadeComercialVO sisUnidadeComercialVO) {
        this.sisUnidadeComercialVO = sisUnidadeComercialVO;
    }

    public SisSituacaoSistemaVO getSisSituacaoSistemaVO() {
        return sisSituacaoSistemaVO;
    }

    public void setSisSituacaoSistemaVO(SisSituacaoSistemaVO sisSituacaoSistemaVO) {
        this.sisSituacaoSistemaVO = sisSituacaoSistemaVO;
    }

    public FiscalCestNcmVO getFiscalCestNcmVO() {
        return fiscalCestNcmVO;
    }

    public void setFiscalCestNcmVO(FiscalCestNcmVO fiscalCestNcmVO) {
        this.fiscalCestNcmVO = fiscalCestNcmVO;
    }

    public FiscalCSTOrigemVO getFiscalCSTOrigemVO() {
        return fiscalCSTOrigemVO;
    }

    public void setFiscalCSTOrigemVO(FiscalCSTOrigemVO fiscalCSTOrigemVO) {
        this.fiscalCSTOrigemVO = fiscalCSTOrigemVO;
    }

    public FiscalICMSVO getFiscalICMSVO() {
        return fiscalICMSVO;
    }

    public void setFiscalICMSVO(FiscalICMSVO fiscalICMSVO) {
        this.fiscalICMSVO = fiscalICMSVO;
    }

    public FiscalPISCOFINSVO getFiscalPISVO() {
        return fiscalPISVO;
    }

    public void setFiscalPISVO(FiscalPISCOFINSVO fiscalPISVO) {
        this.fiscalPISVO = fiscalPISVO;
    }

    public FiscalPISCOFINSVO getFiscalCOFINSVO() {
        return fiscalCOFINSVO;
    }

    public void setFiscalCOFINSVO(FiscalPISCOFINSVO fiscalCOFINSVO) {
        this.fiscalCOFINSVO = fiscalCOFINSVO;
    }

    public TabColaboradorVO getUsuarioCadastroVO() {
        return usuarioCadastroVO;
    }

    public void setUsuarioCadastroVO(TabColaboradorVO usuarioCadastroVO) {
        this.usuarioCadastroVO = usuarioCadastroVO;
    }

    public TabColaboradorVO getUsuarioAtualizacaoVO() {
        return usuarioAtualizacaoVO;
    }

    public void setUsuarioAtualizacaoVO(TabColaboradorVO usuarioAtualizacaoVO) {
        this.usuarioAtualizacaoVO = usuarioAtualizacaoVO;
    }

    public List<TabProdutoEanVO> getTabProdutoEanVOList() {
        return tabProdutoEanVOList;
    }

    public void setTabProdutoEanVOList(List<TabProdutoEanVO> tabProdutoEanVOList) {
        this.tabProdutoEanVOList = tabProdutoEanVOList;
    }

    public Timestamp getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Timestamp dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Timestamp getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Timestamp dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
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

    public int getSisUnidadeComercial_id() {
        return sisUnidadeComercial_idProperty().get();
    }

    public IntegerProperty sisUnidadeComercial_idProperty() {
        if (sisUnidadeComercial_id == null) sisUnidadeComercial_id = new SimpleIntegerProperty(0);
        return sisUnidadeComercial_id;
    }

    public void setSisUnidadeComercial_id(int sisUnidadeComercial_id) {
        sisUnidadeComercial_idProperty().set(sisUnidadeComercial_id);
    }

    public int getSisSituacaoSistema_id() {
        return sisSituacaoSistema_idProperty().get();
    }

    public IntegerProperty sisSituacaoSistema_idProperty() {
        if (sisSituacaoSistema_id == null) sisSituacaoSistema_id = new SimpleIntegerProperty(0);
        return sisSituacaoSistema_id;
    }

    public void setSisSituacaoSistema_id(int sisSituacaoSistema_id) {
        sisSituacaoSistema_idProperty().set(sisSituacaoSistema_id);
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

    public int getFiscalCestNcm_id() {
        return fiscalCestNcm_idProperty().get();
    }

    public IntegerProperty fiscalCestNcm_idProperty() {
        if (fiscalCestNcm_id == null) fiscalCestNcm_id = new SimpleIntegerProperty(0);
        return fiscalCestNcm_id;
    }

    public void setFiscalCestNcm_id(int fiscalCestNcm_id) {
        fiscalCestNcm_idProperty().set(fiscalCestNcm_id);
    }

    public int getFiscalCSTOrigem_id() {
        return fiscalCSTOrigem_idProperty().get();
    }

    public IntegerProperty fiscalCSTOrigem_idProperty() {
        if (fiscalCSTOrigem_id == null) fiscalCSTOrigem_id = new SimpleIntegerProperty(0);
        return fiscalCSTOrigem_id;
    }

    public void setFiscalCSTOrigem_id(int fiscalCSTOrigem_id) {
        fiscalCSTOrigem_idProperty().set(fiscalCSTOrigem_id);
    }

    public int getFiscalICMS_id() {
        return fiscalICMS_idProperty().get();
    }

    public IntegerProperty fiscalICMS_idProperty() {
        if (fiscalICMS_id == null) fiscalICMS_id = new SimpleIntegerProperty(0);
        return fiscalICMS_id;
    }

    public void setFiscalICMS_id(int fiscalICMS_id) {
        fiscalICMS_idProperty().set(fiscalICMS_id);
    }

    public int getFiscalPIS_id() {
        return fiscalPIS_idProperty().get();
    }

    public IntegerProperty fiscalPIS_idProperty() {
        if (fiscalPIS_id == null) fiscalPIS_id = new SimpleIntegerProperty(0);
        return fiscalPIS_id;
    }

    public void setFiscalPIS_id(int fiscalPIS_id) {
        fiscalPIS_idProperty().set(fiscalPIS_id);
    }

    public int getFiscalCOFINS_id() {
        return fiscalCOFINS_idProperty().get();
    }

    public IntegerProperty fiscalCOFINS_idProperty() {
        if (fiscalCOFINS_id == null) fiscalCOFINS_id = new SimpleIntegerProperty(0);
        return fiscalCOFINS_id;
    }

    public void setFiscalCOFINS_id(int fiscalCOFINS_id) {
        fiscalCOFINS_idProperty().set(fiscalCOFINS_id);
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

    public int getUsuarioAtualizacao_id() {
        return usuarioAtualizacao_idProperty().get();
    }

    public IntegerProperty usuarioAtualizacao_idProperty() {
        if (usuarioAtualizacao_id == null) usuarioAtualizacao_id = new SimpleIntegerProperty(0);
        return usuarioAtualizacao_id;
    }

    public void setUsuarioAtualizacao_id(int usuarioAtualizacao_id) {
        usuarioAtualizacao_idProperty().set(usuarioAtualizacao_id);
    }

    public String getCodigo() {
        return codigoProperty().get();
    }

    public StringProperty codigoProperty() {
        if (codigo == null) codigo = new SimpleStringProperty("");
        return codigo;
    }

    public void setCodigo(String codigo) {
        codigoProperty().set(codigo);
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

    public String getFiscalNcm() {
        return fiscalNcmProperty().get();
    }

    public StringProperty fiscalNcmProperty() {
        if (fiscalNcm == null) fiscalNcm = new SimpleStringProperty("");
        return fiscalNcm;
    }

    public void setFiscalNcm(String fiscalNcm) {
        fiscalNcmProperty().set(fiscalNcm);
    }

    public String getNfeGenero() {
        return nfeGeneroProperty().get();
    }

    public StringProperty nfeGeneroProperty() {
        if (nfeGenero == null) nfeGenero = new SimpleStringProperty("");
        return nfeGenero;
    }

    public void setNfeGenero(String nfeGenero) {
        nfeGeneroProperty().set(nfeGenero);
    }

    public double getPeso() {
        return pesoProperty().get();
    }

    public DoubleProperty pesoProperty() {
        if (peso == null) peso = new SimpleDoubleProperty(0);
        return peso;
    }

    public void setPeso(double peso) {
        pesoProperty().set(peso);
    }

    public double getPrecoFabrica() {
        return precoFabricaProperty().get();
    }

    public DoubleProperty precoFabricaProperty() {
        if (precoFabrica == null) precoFabrica = new SimpleDoubleProperty(0);
        return precoFabrica;
    }

    public void setPrecoFabrica(double precoFabrica) {
        precoFabricaProperty().set(precoFabrica);
    }

    public double getPrecoVenda() {
        return precoVendaProperty().get();
    }

    public DoubleProperty precoVendaProperty() {
        if (precoVenda == null) precoVenda = new SimpleDoubleProperty(0);
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        precoVendaProperty().set(precoVenda);
    }

    public double getPrecoUltimoFrete() {
        return precoUltimoFreteProperty().get();
    }

    public DoubleProperty precoUltimoFreteProperty() {
        if (precoUltimoFrete == null) precoUltimoFrete = new SimpleDoubleProperty(0);
        return precoUltimoFrete;
    }

    public void setPrecoUltimoFrete(double precoUltimoFrete) {
        precoUltimoFreteProperty().set(precoUltimoFrete);
    }

    public double getComissao() {
        return comissaoProperty().get();
    }

    public DoubleProperty comissaoProperty() {
        if (comissao == null) comissao = new SimpleDoubleProperty(0);
        return comissao;
    }

    public void setComissao(double comissao) {
        comissaoProperty().set(comissao);
    }
}
