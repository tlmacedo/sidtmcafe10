package br.com.tlmacedo.cafeperfeito.model.vo;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "uf")
public class UfVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    @Column(nullable = false, length = 50)
    private String descricao;
    @Column(nullable = false, length = 2)
    private String sigla;

    public UfVO() {
    }

    public UfVO(Long id,String descricao, String sigla) {
        this.id = id;
        this.descricao = descricao;
        this.sigla = sigla;
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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UfVO)) return false;

        UfVO ufVO = (UfVO) o;

        return getId().equals(ufVO.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "UfVO{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", sigla='" + sigla + '\'' +
                '}';
    }
}
