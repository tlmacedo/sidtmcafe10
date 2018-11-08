package br.com.cafeperfeito.sidtmcafe.model.vo;

import br.com.cafeperfeito.sidtmcafe.model.vo.enums.SituacaoNoSistefma;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "usuario")
public class Usuario extends Colaborador implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(length = 56)
    private String senha;
    private Integer situacao;

    public Usuario() {
    }

    public Usuario(String nome, String apelido, String ctps, Date dataAdmisao, BigDecimal salario, Boolean ativo, Cargo cargo, Empresa trabalha, String senha, Integer situacao) {
        super(nome, apelido, ctps, dataAdmisao, salario, ativo, cargo, trabalha);
        this.senha = senha;
        this.situacao = situacao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public SituacaoNoSistefma getSituacao() {
        return SituacaoNoSistefma.toEnum(situacao);
    }

    public void setSituacao(SituacaoNoSistefma situacao) {
        this.situacao = situacao.getCod();
    }


    //    private Cargo cargo;
//
//    private Collection<Empresa> empresaCadastro;
//
//    private Collection<Empresa> empresaAtualiza;


    @Override
    public String toString() {
        return "Usuario{" +
                "senha='" + senha + '\'' +
                ", situacao=" + situacao +
                "} " + super.toString();
    }
}
