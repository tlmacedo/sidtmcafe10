package br.com.cafeperfeito.sidtmcafe.model.vo;

import br.com.cafeperfeito.sidtmcafe.model.dao.TabProdutoDAO;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TabProdutoVO extends RecursiveTreeObject<TabProdutoVO> {

    SisUnidadeComercialVO sisUnidadeComercialVO;
    SisSituacaoSistemaVO sisSituacaoSistemaVO;
    FiscalCestNcmVO fiscalCestNcmVO;
    FiscalCstOrigemVO fiscalCstOrigemVO;
    FiscalIcmsVO fiscalIcmsVO;
    FiscalPisCofinsVO fiscalPisVO;
    FiscalPisCofinsVO fiscalCofinsVO;
    TabColaboradorVO usuarioCadastroVO;
    TabColaboradorVO usuarioAtualizacaoVO;
    List<TabProduto_CodBarraVO> codBarraVOList;

    Timestamp dataCadastro, dataAtualizacao;

    IntegerProperty id, sisUnidadeComercial_id, sisSituacaoSistema_id, varejo, fiscalCestNcm_id, fiscalCSTOrigem_id,
            fiscalICMS_id, fiscalPIS_id, fiscalCOFINS_id, usuarioCadastro_id, usuarioAtualizacao_id;

    StringProperty codigo, descricao, nfeGenero, ncm, cest;

    BigDecimal peso, precoFabrica, precoVenda, precoUltimoImpostoSefaz, precoUltimoFrete, comissao;

    Image imgProduto, imgProdutoBack;

    public TabProdutoVO() {

    }

    public TabProdutoVO(int id) {
        this.id = new SimpleIntegerProperty(id);
        this.varejo = new SimpleIntegerProperty(1);
        this.sisUnidadeComercial_id = new SimpleIntegerProperty(-1);
        this.sisSituacaoSistema_id = new SimpleIntegerProperty(-1);
        this.fiscalCestNcm_id = new SimpleIntegerProperty(-1);
        this.fiscalCSTOrigem_id = new SimpleIntegerProperty(-1);
        this.fiscalICMS_id = new SimpleIntegerProperty(-1);
        this.fiscalPIS_id = new SimpleIntegerProperty(-1);
        this.fiscalCOFINS_id = new SimpleIntegerProperty(-1);
        this.codBarraVOList = new ArrayList<>();
    }

    @Override
    public TabProdutoVO clone() throws CloneNotSupportedException {
        TabProdutoVO produtoVO = new TabProdutoVO();
        produtoVO = new TabProdutoDAO().getTabProdutoVO(getId());
        return produtoVO;
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

    public FiscalCstOrigemVO getFiscalCstOrigemVO() {
        return fiscalCstOrigemVO;
    }

    public void setFiscalCstOrigemVO(FiscalCstOrigemVO fiscalCstOrigemVO) {
        this.fiscalCstOrigemVO = fiscalCstOrigemVO;
    }

    public FiscalIcmsVO getFiscalIcmsVO() {
        return fiscalIcmsVO;
    }

    public void setFiscalIcmsVO(FiscalIcmsVO fiscalIcmsVO) {
        this.fiscalIcmsVO = fiscalIcmsVO;
    }

    public FiscalPisCofinsVO getFiscalPisVO() {
        return fiscalPisVO;
    }

    public void setFiscalPisVO(FiscalPisCofinsVO fiscalPisVO) {
        this.fiscalPisVO = fiscalPisVO;
    }

    public FiscalPisCofinsVO getFiscalCofinsVO() {
        return fiscalCofinsVO;
    }

    public void setFiscalCofinsVO(FiscalPisCofinsVO fiscalCofinsVO) {
        this.fiscalCofinsVO = fiscalCofinsVO;
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

    public List<TabProduto_CodBarraVO> getCodBarraVOList() {
        return codBarraVOList;
    }

    public void setCodBarraVOList(List<TabProduto_CodBarraVO> codBarraVOList) {
        this.codBarraVOList = codBarraVOList;
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

    public BigDecimal getPeso() {
        if (peso == null) peso = new BigDecimal(0);
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public BigDecimal getPrecoFabrica() {
        if (precoFabrica == null) precoFabrica = new BigDecimal(0);
        return precoFabrica;
    }

    public void setPrecoFabrica(BigDecimal precoFabrica) {
        this.precoFabrica = precoFabrica;
    }

    public BigDecimal getPrecoVenda() {
        if (precoVenda == null) precoVenda = new BigDecimal(0);
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public BigDecimal getPrecoUltimoImpostoSefaz() {
        if (precoUltimoImpostoSefaz == null) precoUltimoImpostoSefaz = new BigDecimal(0);
        return precoUltimoImpostoSefaz;
    }

    public void setPrecoUltimoImpostoSefaz(BigDecimal precoUltimoImpostoSefaz) {
        this.precoUltimoImpostoSefaz = precoUltimoImpostoSefaz;
    }

    public BigDecimal getPrecoUltimoFrete() {
        if (precoUltimoFrete == null) precoUltimoFrete = new BigDecimal(0);
        return precoUltimoFrete;
    }

    public void setPrecoUltimoFrete(BigDecimal precoUltimoFrete) {
        this.precoUltimoFrete = precoUltimoFrete;
    }

    public BigDecimal getComissao() {
        if (comissao == null) comissao = new BigDecimal(0);
        return comissao;
    }

    public void setComissao(BigDecimal comissao) {
        this.comissao = comissao;
    }

    public Image getImgProduto() {
        return imgProduto;
    }

    public void setImgProduto(Image imgProduto) {
        this.imgProduto = imgProduto;
    }

    public Image getImgProdutoBack() {
        return imgProdutoBack;
    }

    public void setImgProdutoBack(Image imgProdutoBack) {
        this.imgProdutoBack = imgProdutoBack;
    }
}
