package br.com.tlmacedo.cafeperfeito.model.vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "telefone")
public class TelefoneVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 11)
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "telefoneOperadora_id", foreignKey = @ForeignKey(name = "fk_telefone_telefoneOperadora_id"))
    private TelefoneOperadoraVO telefoneOperadoraVO;

    public TelefoneVO() {
    }

    public TelefoneVO(String descricao, TelefoneOperadoraVO telefoneOperadoraVO) {
        this.descricao = descricao;
        this.telefoneOperadoraVO = telefoneOperadoraVO;
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

    public TelefoneOperadoraVO getTelefoneOperadoraVO() {
        return telefoneOperadoraVO;
    }

    public void setTelefoneOperadoraVO(TelefoneOperadoraVO telefoneOperadoraVO) {
        this.telefoneOperadoraVO = telefoneOperadoraVO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TelefoneVO)) return false;

        TelefoneVO telefoneVO = (TelefoneVO) o;

        return getId().equals(telefoneVO.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }


    @Override
    public String toString() {
        return "TelefoneVO{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", telefoneOperadoraVO=" + telefoneOperadoraVO +
                '}';
    }
}
