package br.com.cafeperfeito.sidtmcafe.model.vo;

import br.com.cafeperfeito.sidtmcafe.model.dao.SisSituacaoSistemaDAO;
import br.com.cafeperfeito.sidtmcafe.model.dao.TabEmpresaDAO;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TabEmpresaVO extends RecursiveTreeObject<TabEmpresaVO> {

    SisSituacaoSistemaVO sisSituacaoSistemaVO;
    TabColaboradorVO usuarioCadastroVO;
    TabColaboradorVO usuarioAtualizacaoVO;
    List<TabEnderecoVO> tabEnderecoVOList;
    List<TabEmailHomePageVO> tabEmailHomePageVOList;
    List<TabTelefoneVO> tabTelefoneVOList;
    List<TabContatoVO> tabContatoVOList;
    List<TabEmpresaReceitaFederalVO> tabEmpresaReceitaFederalVOList;

    Date dataAbertura;
    Timestamp dataCadastro, dataAtualizacao;
    IntegerProperty id, sisSituacaoSistema_id, usuarioCadastro_id, usuarioAtualizacao_id;
    BooleanProperty isEmpresa, ieIsento, isLoja, isCliente, isFornecedor, isTransportadora;
    StringProperty cnpj, ie, razao, fantasia, naturezaJuridica;

    public TabEmpresaVO() {
        this.sisSituacaoSistemaVO = new SisSituacaoSistemaVO();
        this.usuarioCadastroVO = new TabColaboradorVO();
        this.usuarioAtualizacaoVO = new TabColaboradorVO();
        this.tabEnderecoVOList = new ArrayList<>();
        this.tabEmailHomePageVOList = new ArrayList<>();
        this.tabTelefoneVOList = new ArrayList<>();
        this.tabContatoVOList = new ArrayList<>();
        this.tabEmpresaReceitaFederalVOList = new ArrayList<>();
    }

    @Override
    public TabEmpresaVO clone() throws CloneNotSupportedException {
        TabEmpresaVO empresaVO = new TabEmpresaVO();
        empresaVO = new TabEmpresaDAO().getTabEmpresaVO(getId());
        return empresaVO;
    }

    public TabEmpresaVO(int sisSituacaoSistema_id) {
        this.id = new SimpleIntegerProperty(0);
        this.isEmpresa = new SimpleBooleanProperty(true);
        this.isLoja = new SimpleBooleanProperty(false);
        this.isCliente = new SimpleBooleanProperty(true);
        this.isFornecedor = new SimpleBooleanProperty(false);
        this.isTransportadora = new SimpleBooleanProperty(false);
        this.sisSituacaoSistema_id = new SimpleIntegerProperty(sisSituacaoSistema_id);
        this.sisSituacaoSistemaVO = new SisSituacaoSistemaDAO().getSisSituacaoSistemaVO(sisSituacaoSistema_id);
        this.tabEnderecoVOList = new ArrayList<>();
        this.tabEnderecoVOList.add(new TabEnderecoVO(1, 112));
        this.tabEmailHomePageVOList = new ArrayList<>();
        this.tabTelefoneVOList = new ArrayList<>();
        this.tabContatoVOList = new ArrayList<>();
        this.tabEmpresaReceitaFederalVOList = new ArrayList<>();
    }

    public SisSituacaoSistemaVO getSisSituacaoSistemaVO() {
        return sisSituacaoSistemaVO;
    }

    public void setSisSituacaoSistemaVO(SisSituacaoSistemaVO sisSituacaoSistemaVO) {
        this.sisSituacaoSistemaVO = sisSituacaoSistemaVO;
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

    public List<TabEnderecoVO> getTabEnderecoVOList() {
        return tabEnderecoVOList;
    }

    public void setTabEnderecoVOList(List<TabEnderecoVO> tabEnderecoVOList) {
        this.tabEnderecoVOList = tabEnderecoVOList;
    }

    public List<TabEmailHomePageVO> getTabEmailHomePageVOList() {
        return tabEmailHomePageVOList;
    }

    public void setTabEmailHomePageVOList(List<TabEmailHomePageVO> tabEmailHomePageVOList) {
        this.tabEmailHomePageVOList = tabEmailHomePageVOList;
    }

    public List<TabTelefoneVO> getTabTelefoneVOList() {
        return tabTelefoneVOList;
    }

    public void setTabTelefoneVOList(List<TabTelefoneVO> tabTelefoneVOList) {
        this.tabTelefoneVOList = tabTelefoneVOList;
    }

    public List<TabContatoVO> getTabContatoVOList() {
        return tabContatoVOList;
    }

    public void setTabContatoVOList(List<TabContatoVO> tabContatoVOList) {
        this.tabContatoVOList = tabContatoVOList;
    }

    public List<TabEmpresaReceitaFederalVO> getTabEmpresaReceitaFederalVOList() {
        return tabEmpresaReceitaFederalVOList;
    }

    public void setTabEmpresaReceitaFederalVOList(List<TabEmpresaReceitaFederalVO> tabEmpresaReceitaFederalVOList) {
        this.tabEmpresaReceitaFederalVOList = tabEmpresaReceitaFederalVOList;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
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

    public boolean isIsEmpresa() {
        return isEmpresaProperty().get();
    }

    public BooleanProperty isEmpresaProperty() {
        if (isEmpresa == null) isEmpresa = new SimpleBooleanProperty(false);
        return isEmpresa;
    }

    public void setIsEmpresa(boolean isEmpresa) {
        isEmpresaProperty().set(isEmpresa);
    }

    public boolean isIeIsento() {
        return ieIsentoProperty().get();
    }

    public BooleanProperty ieIsentoProperty() {
        if (ieIsento == null) ieIsento = new SimpleBooleanProperty(false);
        return ieIsento;
    }

    public void setIeIsento(boolean ieIsento) {
        ieIsentoProperty().set(ieIsento);
    }

    public boolean isIsLoja() {
        return isLojaProperty().get();
    }

    public BooleanProperty isLojaProperty() {
        if (isLoja == null) isLoja = new SimpleBooleanProperty(false);
        return isLoja;
    }

    public void setIsLoja(boolean isLoja) {
        isLojaProperty().set(isLoja);
    }

    public boolean isIsCliente() {
        return isClienteProperty().get();
    }

    public BooleanProperty isClienteProperty() {
        if (isCliente == null) isCliente = new SimpleBooleanProperty(false);
        return isCliente;
    }

    public void setIsCliente(boolean isCliente) {
        isClienteProperty().set(isCliente);
    }

    public boolean isIsFornecedor() {
        return isFornecedorProperty().get();
    }

    public BooleanProperty isFornecedorProperty() {
        if (isFornecedor == null) isFornecedor = new SimpleBooleanProperty(false);
        return isFornecedor;
    }

    public void setIsFornecedor(boolean isFornecedor) {
        isFornecedorProperty().set(isFornecedor);
    }

    public boolean isIsTransportadora() {
        return isTransportadoraProperty().get();
    }

    public BooleanProperty isTransportadoraProperty() {
        if (isTransportadora == null) isTransportadora = new SimpleBooleanProperty(false);
        return isTransportadora;
    }

    public void setIsTransportadora(boolean isTransportadora) {
        isTransportadoraProperty().set(isTransportadora);
    }

    public String getCnpj() {
        return cnpjProperty().get();
    }

    public StringProperty cnpjProperty() {
        if (cnpj == null) cnpj = new SimpleStringProperty("");
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        cnpjProperty().set(cnpj);
    }

    public String getIe() {
        return ieProperty().get();
    }

    public StringProperty ieProperty() {
        if (ie == null) ie = new SimpleStringProperty("");
        return ie;
    }

    public void setIe(String ie) {
        ieProperty().set(ie);
    }

    public String getRazao() {
        return razaoProperty().get();
    }

    public StringProperty razaoProperty() {
        if (razao == null) razao = new SimpleStringProperty("");
        return razao;
    }

    public void setRazao(String razao) {
        razaoProperty().set(razao);
    }

    public String getFantasia() {
        return fantasiaProperty().get();
    }

    public StringProperty fantasiaProperty() {
        if (fantasia == null) fantasia = new SimpleStringProperty("");
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        fantasiaProperty().set(fantasia);
    }

    public String getNaturezaJuridica() {
        return naturezaJuridicaProperty().get();
    }

    public StringProperty naturezaJuridicaProperty() {
        if (naturezaJuridica == null) naturezaJuridica = new SimpleStringProperty("");
        return naturezaJuridica;
    }

    public void setNaturezaJuridica(String naturezaJuridica) {
        naturezaJuridicaProperty().set(naturezaJuridica);
    }

//    public String getDetalheTransportadora() {
//        if ((razaoProperty().get() != "") & enderecoVOList.size() > 0)
//            return "[Transp]: " + razaoProperty().get() + " (" + fantasiaProperty().get() + ") "
//                    + ";[End.]: " + enderecoVOList.get(0).getLogradouro() + ", "
//                    + enderecoVOList.get(0).getNumero() + " - " + enderecoVOList.get(0).getBairro();
//        return "";
//    }
//
//    public String getDetalheFornecedor() {
//        if ((razaoProperty().get() != "") & enderecoVOList.size() > 0)
//            return "[Fornec]: " + razaoProperty().get() + " (" + fantasiaProperty().get() + ") "
//                    + ";[End.]: " + enderecoVOList.get(0).getLogradouro() + ", "
//                    + enderecoVOList.get(0).getNumero() + " - " + enderecoVOList.get(0).getBairro();
//        return "";
//    }
//
//    public String getDetalheCliente() {
//        if ((razaoProperty().get() != "") & enderecoVOList.size() > 0)
//            return "[Cliente]: " + razaoProperty().get() + " (" + fantasiaProperty().get() + ") "
//                    + ";[End.]: " + enderecoVOList.get(0).getLogradouro() + ", "
//                    + enderecoVOList.get(0).getNumero() + " - " + enderecoVOList.get(0).getBairro();
//        return "";
//    }

}
