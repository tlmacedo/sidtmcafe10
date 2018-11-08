package br.com.cafeperfeito.sidtmcafe.model.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "usuario")
public class Usuario extends Colaborador implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(length = 56)
    private String senha;
    @ManyToOne
    @JoinColumn(name = "situacaoNoSistema_id", foreignKey = @ForeignKey(name = "fk_usuario_situacaoNoSistema_id"))
    private SituacaoNoSistema situacaoNoSistema;

    public Usuario() {
    }

    public Usuario(String nome, String apelido, String ctps, Date dataAdmisao, BigDecimal salario, Boolean ativo, Cargo cargo, String senha, SituacaoNoSistema situacaoNoSistema) {
        super(nome, apelido, ctps, dataAdmisao, salario, ativo, cargo);
        this.senha = senha;
        this.situacaoNoSistema = situacaoNoSistema;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public SituacaoNoSistema getSituacaoNoSistema() {
        return situacaoNoSistema;
    }

    public void setSituacaoNoSistema(SituacaoNoSistema situacaoNoSistema) {
        this.situacaoNoSistema = situacaoNoSistema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        if (!super.equals(o)) return false;

        Usuario usuario = (Usuario) o;

        return getSenha().equals(usuario.getSenha());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getSenha().hashCode();
        return result;
    }
}
