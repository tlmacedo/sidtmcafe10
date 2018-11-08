package br.com.cafeperfeito.sidtmcafe.model.vo.enums;

public enum SituacaoNoSistefma {

    ATIVO(1, "Ativo"),
    DESATIVADO(2, "Desativado"),
    NEGOCIACAO(3, "Negociação"),
    DESCONTINUADO(4, "Descontinuado"),
    INCONSISTENTE(5, "Inconsistente"),
    TERCEIROS(6, "Terceiros"),
    BAIXADA(7, "Baixada");

    private int cod;
    private String descricao;

    private SituacaoNoSistefma(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static SituacaoNoSistefma toEnum(Integer cod) {
        if (cod == null) return null;
        for (SituacaoNoSistefma tipo : SituacaoNoSistefma.values())
            if (cod.equals(tipo.getCod()))
                return tipo;
        throw new IllegalArgumentException("Id inválido");
    }

}
