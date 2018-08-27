package br.com.cafeperfeito.sidtmcafe.model.vo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Optional;

public class TabColaboradorVO {


    SisCargoVO sisCargoVO;
    SisSituacaoSistemaVO sisSituacaoSistemaVO;
    TabEmpresaVO lojaVO;

    IntegerProperty id, sisCargo_id, sisSituacaoSistema_id, tabLoja_id;
    StringProperty nome, apelido, senha, senhaSalt;

    public TabColaboradorVO() {
    }

    public SisCargoVO getSisCargoVO() {
        return sisCargoVO;
    }

    public void setSisCargoVO(SisCargoVO sisCargoVO) {
        this.sisCargoVO = sisCargoVO;
    }

    public SisSituacaoSistemaVO getSisSituacaoSistemaVO() {
        return sisSituacaoSistemaVO;
    }

    public void setSisSituacaoSistemaVO(SisSituacaoSistemaVO sisSituacaoSistemaVO) {
        this.sisSituacaoSistemaVO = sisSituacaoSistemaVO;
    }

    public TabEmpresaVO getLojaVO() {
        return lojaVO;
    }

    public void setLojaVO(TabEmpresaVO lojaVO) {
        this.lojaVO = lojaVO;
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

    public int getSisCargo_id() {
        return sisCargo_idProperty().get();
    }

    public IntegerProperty sisCargo_idProperty() {
        if (sisCargo_id == null) sisCargo_id = new SimpleIntegerProperty(0);
        return sisCargo_id;
    }

    public void setSisCargo_id(int sisCargo_id) {
        sisCargo_idProperty().set(sisCargo_id);
    }

    public int getTabLoja_id() {
        return tabLoja_idProperty().get();
    }

    public IntegerProperty tabLoja_idProperty() {
        if (tabLoja_id == null) tabLoja_id = new SimpleIntegerProperty(0);
        return tabLoja_id;
    }

    public void setTabLoja_id(int tabLoja_id) {
        tabLoja_idProperty().set(tabLoja_id);
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

    public String getNome() {
        return nomeProperty().get();
    }

    public StringProperty nomeProperty() {
        if (nome == null) nome = new SimpleStringProperty("");
        return nome;
    }

    public void setNome(String nome) {
        nomeProperty().set(nome);
    }

    public String getApelido() {
        return apelidoProperty().get();
    }

    public StringProperty apelidoProperty() {
        if (apelido == null) apelido = new SimpleStringProperty("");
        return apelido;
    }

    public void setApelido(String apelido) {
        apelidoProperty().set(apelido);
    }

    public String getSenha() {
        return senhaProperty().get();
    }

    public StringProperty senhaProperty() {
        if (senha == null) senha = new SimpleStringProperty("");
        return senha;
    }

    public void setSenha(String senha) {
        senhaProperty().set(senha);
    }

    public String getSenhaSalt() {
        return senhaSaltProperty().get();
    }

    public StringProperty senhaSaltProperty() {
        if (senhaSalt == null) senhaSalt = new SimpleStringProperty("");
        return senhaSalt;
    }

    public void setSenhaSalt(String senhaSalt) {
        senhaSaltProperty().set(senhaSalt);
    }

    public String getDetalheColaborador() {
        StringBuilder detColaborador = new StringBuilder();
        Optional.ofNullable(nomeProperty().get()).ifPresent(c ->
                detColaborador.append(String.format("usuario::%s;", nomeProperty().get()))
        );
        Optional.ofNullable(apelidoProperty().get()).ifPresent(c ->
                detColaborador.append(String.format("apelido::%s;", apelidoProperty().get()))
        );
        Optional.ofNullable(sisCargoVO).ifPresent(c ->
                detColaborador.append(String.format("cargo::%s;", sisCargoVO.getDescricao()))
        );
        Optional.ofNullable(lojaVO).ifPresent(c -> {
            detColaborador.append(String.format("loja::%s;", lojaVO.getFantasia()));
            Optional.ofNullable(lojaVO.getTabEnderecoVOList().get(0)).ifPresent(c1 ->
                    detColaborador.append(String.format("end::%s;", (String.format(" %s, %s - %s", lojaVO.getTabEnderecoVOList().get(0).getLogradouro(), lojaVO.getTabEnderecoVOList().get(0).getNumero(), lojaVO.getTabEnderecoVOList().get(0).getBairro()))))
            );
        });
        return detColaborador.toString();
    }

    @Override
    public String toString() {
        return apelidoProperty().get();
    }

}
