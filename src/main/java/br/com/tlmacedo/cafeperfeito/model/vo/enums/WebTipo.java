package br.com.tlmacedo.cafeperfeito.model.vo.enums;

public enum WebTipo {

    EMAIL(1, "email"),
    HOMEPAGE(2, "home page");

    private int cod;
    private String descricao;

    private WebTipo(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static WebTipo toEnum(Integer cod) {
        if (cod == null) return null;
        for (WebTipo tipo : WebTipo.values())
            if (cod.equals(tipo.getCod()))
                return tipo;
        throw new IllegalArgumentException("Id inválido");
    }


}
