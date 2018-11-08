package br.com.cafeperfeito.sidtmcafe.model.vo;

import java.util.Collection;

public class Municipio {

    private Long id;

    private String descricao;

    private Boolean isCapital;

    private String ibge_codigo;

    private Integer ddd;

    private Uf uf;

    private Collection<Endereco> enderecos;

}
