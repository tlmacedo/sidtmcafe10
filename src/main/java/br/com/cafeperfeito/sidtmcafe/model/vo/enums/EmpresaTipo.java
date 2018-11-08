package br.com.cafeperfeito.sidtmcafe.model.vo.enums;

public enum EmpresaTipo {

    PESSOAFISICA(1, "Pessoa Física"),
    PESSOAJURIDICA(2, "Pessoa Jurídica");

    private int cod;
    private String descricao;

    private EmpresaTipo(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static EmpresaTipo toEnum(Integer cod) {
        if (cod == null) return null;
        for (EmpresaTipo tipo : EmpresaTipo.values())
            if (cod.equals(tipo.getCod()))
                return tipo;
        throw new IllegalArgumentException("Id inválido");
    }
}
