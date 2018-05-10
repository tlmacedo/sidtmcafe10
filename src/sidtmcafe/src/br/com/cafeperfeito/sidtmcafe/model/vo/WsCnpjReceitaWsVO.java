package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Pair;

import java.util.List;

public class WsCnpjReceitaWsVO extends RecursiveTreeObject<WsCnpjReceitaWsVO> {

    List<Pair<String, String>> atividadePrincipal, atividadesSecundarias, qsa;

    StringProperty status, message, cnpj, tipo, abertura, nome, fantasia, naturezaJuridica,
            logradouro, numero, complemento, cep, bairro, municipio, uf, email, telefone, efr,
            situacao, dataSituacao, motivoSituacao, situacaoEspecial, dataSituacaoEspecial,
            capitalSocial, extra;

    public WsCnpjReceitaWsVO() {
    }

    public List<Pair<String, String>> getAtividadePrincipal() {
        return atividadePrincipal;
    }

    public void setAtividadePrincipal(List<Pair<String, String>> atividadePrincipal) {
        this.atividadePrincipal = atividadePrincipal;
    }

    public List<Pair<String, String>> getAtividadesSecundarias() {
        return atividadesSecundarias;
    }

    public void setAtividadesSecundarias(List<Pair<String, String>> atividadesSecundarias) {
        this.atividadesSecundarias = atividadesSecundarias;
    }

    public List<Pair<String, String>> getQsa() {
        return qsa;
    }

    public void setQsa(List<Pair<String, String>> qsa) {
        this.qsa = qsa;
    }

    public String getStatus() {
        return statusProperty().get();
    }

    public StringProperty statusProperty() {
        if (status == null) status = new SimpleStringProperty("");
        return status;
    }

    public void setStatus(String status) {
        statusProperty().set(status);
    }

    public String getMessage() {
        return messageProperty().get();
    }

    public StringProperty messageProperty() {
        if (message == null) message = new SimpleStringProperty("");
        return message;
    }

    public void setMessage(String message) {
        messageProperty().set(message);
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

    public String getTipo() {
        return tipoProperty().get();
    }

    public StringProperty tipoProperty() {
        if (tipo == null) tipo = new SimpleStringProperty("");
        return tipo;
    }

    public void setTipo(String tipo) {
        tipoProperty().set(tipo);
    }

    public String getAbertura() {
        return aberturaProperty().get();
    }

    public StringProperty aberturaProperty() {
        if (abertura == null) abertura = new SimpleStringProperty("");
        return abertura;
    }

    public void setAbertura(String abertura) {
        aberturaProperty().set(abertura);
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

    public String getLogradouro() {
        return logradouroProperty().get();
    }

    public StringProperty logradouroProperty() {
        if (logradouro == null) logradouro = new SimpleStringProperty("");
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        logradouroProperty().set(logradouro);
    }

    public String getNumero() {
        return numeroProperty().get();
    }

    public StringProperty numeroProperty() {
        if (numero == null) numero = new SimpleStringProperty("");
        return numero;
    }

    public void setNumero(String numero) {
        numeroProperty().set(numero);
    }

    public String getComplemento() {
        return complementoProperty().get();
    }

    public StringProperty complementoProperty() {
        if (complemento == null) complemento = new SimpleStringProperty("");
        return complemento;
    }

    public void setComplemento(String complemento) {
        complementoProperty().set(complemento);
    }

    public String getCep() {
        return cepProperty().get();
    }

    public StringProperty cepProperty() {
        if (cep == null) cep = new SimpleStringProperty("");
        return cep;
    }

    public void setCep(String cep) {
        cepProperty().set(cep);
    }

    public String getBairro() {
        return bairroProperty().get();
    }

    public StringProperty bairroProperty() {
        if (bairro == null) bairro = new SimpleStringProperty("");
        return bairro;
    }

    public void setBairro(String bairro) {
        bairroProperty().set(bairro);
    }

    public String getMunicipio() {
        return municipioProperty().get();
    }

    public StringProperty municipioProperty() {
        if (municipio == null) municipio = new SimpleStringProperty("");
        return municipio;
    }

    public void setMunicipio(String municipio) {
        municipioProperty().set(municipio);
    }

    public String getUf() {
        return ufProperty().get();
    }

    public StringProperty ufProperty() {
        if (uf == null) uf = new SimpleStringProperty("");
        return uf;
    }

    public void setUf(String uf) {
        ufProperty().set(uf);
    }

    public String getEmail() {
        return emailProperty().get();
    }

    public StringProperty emailProperty() {
        if (email == null) email = new SimpleStringProperty("");
        return email;
    }

    public void setEmail(String email) {
        emailProperty().set(email);
    }

    public String getTelefone() {
        return telefoneProperty().get();
    }

    public StringProperty telefoneProperty() {
        if (telefone == null) telefone = new SimpleStringProperty("");
        return telefone;
    }

    public void setTelefone(String telefone) {
        telefoneProperty().set(telefone);
    }

    public String getEfr() {
        return efrProperty().get();
    }

    public StringProperty efrProperty() {
        if (efr == null) efr = new SimpleStringProperty("");
        return efr;
    }

    public void setEfr(String efr) {
        efrProperty().set(efr);
    }

    public String getSituacao() {
        return situacaoProperty().get();
    }

    public StringProperty situacaoProperty() {
        if (situacao == null) situacao = new SimpleStringProperty("");
        return situacao;
    }

    public void setSituacao(String situacao) {
        situacaoProperty().set(situacao);
    }

    public String getDataSituacao() {
        return dataSituacaoProperty().get();
    }

    public StringProperty dataSituacaoProperty() {
        if (dataSituacao == null) dataSituacao = new SimpleStringProperty("");
        return dataSituacao;
    }

    public void setDataSituacao(String dataSituacao) {
        dataSituacaoProperty().set(dataSituacao);
    }

    public String getMotivoSituacao() {
        return motivoSituacaoProperty().get();
    }

    public StringProperty motivoSituacaoProperty() {
        if (motivoSituacao == null) motivoSituacao = new SimpleStringProperty("");
        return motivoSituacao;
    }

    public void setMotivoSituacao(String motivoSituacao) {
        motivoSituacaoProperty().set(motivoSituacao);
    }

    public String getSituacaoEspecial() {
        return situacaoEspecialProperty().get();
    }

    public StringProperty situacaoEspecialProperty() {
        if (situacaoEspecial == null) situacaoEspecial = new SimpleStringProperty("");
        return situacaoEspecial;
    }

    public void setSituacaoEspecial(String situacaoEspecial) {
        situacaoEspecialProperty().set(situacaoEspecial);
    }

    public String getDataSituacaoEspecial() {
        return dataSituacaoEspecialProperty().get();
    }

    public StringProperty dataSituacaoEspecialProperty() {
        if (dataSituacaoEspecial == null) dataSituacaoEspecial = new SimpleStringProperty("");
        return dataSituacaoEspecial;
    }

    public void setDataSituacaoEspecial(String dataSituacaoEspecial) {
        dataSituacaoEspecialProperty().set(dataSituacaoEspecial);
    }

    public String getCapitalSocial() {
        return capitalSocialProperty().get();
    }

    public StringProperty capitalSocialProperty() {
        if (capitalSocial == null) capitalSocial = new SimpleStringProperty("");
        return capitalSocial;
    }

    public void setCapitalSocial(String capitalSocial) {
        capitalSocialProperty().set(capitalSocial);
    }

    public String getExtra() {
        return extraProperty().get();
    }

    public StringProperty extraProperty() {
        if (extra == null) extra = new SimpleStringProperty("");
        return extra;
    }

    public void setExtra(String extra) {
        extraProperty().set(extra);
    }
}
