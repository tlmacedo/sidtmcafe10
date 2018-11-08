package br.com.cafeperfeito.sidtmcafe.model.vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "situacaoNoSistema")
public class SituacaoNoSistema implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 30)
    private String descricao;

    public SituacaoNoSistema() {
    }

    public SituacaoNoSistema(String descricao) {
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SituacaoNoSistema)) return false;

        SituacaoNoSistema that = (SituacaoNoSistema) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
