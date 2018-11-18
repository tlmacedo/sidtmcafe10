package br.com.tlmacedo.cafeperfeito.model.vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Telefone")
@Table(name = "telefone")
public class Telefone implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 11)
    private String descricao;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "telefoneOperadora_id", foreignKey = @ForeignKey(name = "fk_telefone_telefoneOperadora_id"))
    private TelefoneOperadora telefoneOperadora;

    public Telefone() {
    }

    public Telefone(String descricao, TelefoneOperadora telefoneOperadora) {
        this.descricao = descricao;
        this.telefoneOperadora = telefoneOperadora;
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

    public TelefoneOperadora getTelefoneOperadora() {
        return telefoneOperadora;
    }

    public void setTelefoneOperadora(TelefoneOperadora telefoneOperadora) {
        this.telefoneOperadora = telefoneOperadora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Telefone)) return false;

        Telefone telefone = (Telefone) o;

        return getId().equals(telefone.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }


    @Override
    public String toString() {
        return "TelefoneDAO{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", telefoneOperadora=" + telefoneOperadora +
                '}';
    }
}
