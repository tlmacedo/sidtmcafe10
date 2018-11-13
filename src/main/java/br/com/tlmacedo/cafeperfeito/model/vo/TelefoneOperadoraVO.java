package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.vo.enums.TelefoneTipo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "telefoneOperadora")
public class TelefoneOperadoraVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 30)
    private String descricao;
    @Column(nullable = false)
    private int tipo;
    @Column(nullable = false, length = 2)
    private int ddd;
    @Column(length = 30)
    private String codWsPortabiliadadeCeluar;

    public TelefoneOperadoraVO() {
    }

    public TelefoneOperadoraVO(String descricao, TelefoneTipo tipo, int ddd, String codWsPortabiliadadeCeluar) {
        this.descricao = descricao;
        this.tipo = tipo.getCod();
        this.ddd = ddd;
        this.codWsPortabiliadadeCeluar = codWsPortabiliadadeCeluar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TelefoneTipo getTipo() {
        return TelefoneTipo.toEnum(tipo);
    }

    public void setTipo(TelefoneTipo tipo) {
        this.tipo = tipo.getCod();
    }

    public int getDdd() {
        return ddd;
    }

    public void setDdd(int ddd) {
        this.ddd = ddd;
    }

    public String getCodWsPortabiliadadeCeluar() {
        return codWsPortabiliadadeCeluar;
    }

    public void setCodWsPortabiliadadeCeluar(String codWsPortabiliadadeCeluar) {
        this.codWsPortabiliadadeCeluar = codWsPortabiliadadeCeluar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TelefoneOperadoraVO)) return false;

        TelefoneOperadoraVO that = (TelefoneOperadoraVO) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "TelefoneOperadoraVO{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", tipo=" + tipo +
                ", ddd=" + ddd +
                ", codWsPortabiliadadeCeluar='" + codWsPortabiliadadeCeluar + '\'' +
                '}';
    }
}
