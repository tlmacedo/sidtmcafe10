package br.com.tlmacedo.cafeperfeito.model.vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cargo")
public class CargoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30, nullable = false)
    private String descricao;


    public CargoVO() {
    }

    public CargoVO(String descricao) {
        this.descricao = descricao;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CargoVO)) return false;

        CargoVO cargo_vo = (CargoVO) o;

        return getId().equals(cargo_vo.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
