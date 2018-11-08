package br.com.cafeperfeito.sidtmcafe.model.vo.enums;

public enum UnidadeComercial {

    UNIDADE(1, "UND"),
    PACOTE(2, "PCT"),
    PESO(3, "KG"),
    FARDO(4, "FD"),
    CAIXA(5, "CX"),
    VIDRO(6, "VD"),
    DUZIA(7, "DZ"),
    LATA(8, "LT");

    private int cod;
    private String descricao;

    private UnidadeComercial(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static UnidadeComercial toEnum(Integer cod) {
        if (cod == null) return null;
        for (UnidadeComercial tipo : UnidadeComercial.values())
            if (cod.equals(tipo.getCod()))
                return tipo;
        throw new IllegalArgumentException("Id inválido");
    }


}
