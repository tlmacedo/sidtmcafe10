package br.com.cafeperfeito.sidtmcafe.model.vo.enums;

public enum TelefoneTipo {

    FIXO(1, "Fixo"),
    CELULAR(2, "Celular"),
    FIXO_CELULAR(3, "Fixo/Celular");

    private int cod;
    private String descricao;

    private TelefoneTipo(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TelefoneTipo toEnum(Integer cod) {
        if (cod == null) return null;
        for (TelefoneTipo tipo : TelefoneTipo.values())
            if (cod.equals(tipo.getCod()))
                return tipo;
        throw new IllegalArgumentException("Id inválido");
    }


}
