package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WsCepPostmonVO extends RecursiveTreeObject<WsCepPostmonVO> {

    StringProperty bairro, cidade, cep, logradouro, estado_area, estado_codigo_ibge, estado_nome, cidade_area,
            cidade_codigo_ibge, estado_sigla;

    public WsCepPostmonVO() {
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

    public String getCidade() {
        return cidadeProperty().get();
    }

    public StringProperty cidadeProperty() {
        if (cidade == null) cidade = new SimpleStringProperty("");
        return cidade;
    }

    public void setCidade(String cidade) {
        cidadeProperty().set(cidade);
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

    public String getEstado_area() {
        return estado_areaProperty().get();
    }

    public StringProperty estado_areaProperty() {
        if (estado_area == null) estado_area = new SimpleStringProperty("");
        return estado_area;
    }

    public void setEstado_area(String estado_area) {
        estado_areaProperty().set(estado_area);
    }

    public String getEstado_codigo_ibge() {
        return estado_codigo_ibgeProperty().get();
    }

    public StringProperty estado_codigo_ibgeProperty() {
        if (estado_codigo_ibge == null) estado_codigo_ibge = new SimpleStringProperty("");
        return estado_codigo_ibge;
    }

    public void setEstado_codigo_ibge(String estado_codigo_ibge) {
        estado_codigo_ibgeProperty().set(estado_codigo_ibge);
    }

    public String getEstado_nome() {
        return estado_nomeProperty().get();
    }

    public StringProperty estado_nomeProperty() {
        if (estado_nome == null) estado_nome = new SimpleStringProperty("");
        return estado_nome;
    }

    public void setEstado_nome(String estado_nome) {
        estado_nomeProperty().set(estado_nome);
    }

    public String getCidade_area() {
        return cidade_areaProperty().get();
    }

    public StringProperty cidade_areaProperty() {
        if (cidade_area == null) cidade_area = new SimpleStringProperty("");
        return cidade_area;
    }

    public void setCidade_area(String cidade_area) {
        cidade_areaProperty().set(cidade_area);
    }

    public String getCidade_codigo_ibge() {
        return cidade_codigo_ibgeProperty().get();
    }

    public StringProperty cidade_codigo_ibgeProperty() {
        if (cidade_codigo_ibge == null) cidade_codigo_ibge = new SimpleStringProperty("");
        return cidade_codigo_ibge;
    }

    public void setCidade_codigo_ibge(String cidade_codigo_ibge) {
        cidade_codigo_ibgeProperty().set(cidade_codigo_ibge);
    }

    public String getEstado_sigla() {
        return estado_siglaProperty().get();
    }

    public StringProperty estado_siglaProperty() {
        if (estado_sigla == null) estado_sigla = new SimpleStringProperty("");
        return estado_sigla;
    }

    public void setEstado_sigla(String estado_sigla) {
        estado_siglaProperty().set(estado_sigla);
    }
}
