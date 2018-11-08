package br.com.cafeperfeito.sidtmcafe.model.vo.enums;

public enum EnderecoTipo {

    PRINCIPAL(1, "Principal"),
    ENTREGA(2, "Entrega"),
    COBRANCA(3, "Cobrança"),
    CORRESPONDENCIA(4, "Correspondência"),
    RESIDENCIAL(5, "Residencial"),
    RECADO(6, "Recado");

    private int cod;
    private String descricao;

    private EnderecoTipo(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static EnderecoTipo toEnum(Integer cod) {
        if (cod == null) return null;
        for (EnderecoTipo tipo : EnderecoTipo.values())
            if (cod.equals(tipo.getCod()))
                return tipo;
        throw new IllegalArgumentException("Id inválido");
    }


}
