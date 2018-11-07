package br.com.cafeperfeito.sidtmcafe.model.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "usuario")
public class Usuario extends Colaborador implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(length = 56)
    private String senha;

    public Usuario() {
    }

    public Usuario(String nome, String apelido, String ctps, Date dataAdmisao, BigDecimal salario, Boolean ativo, Cargo cargo, String senha) {
        super(nome, apelido, ctps, dataAdmisao, salario, ativo, cargo);
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return super.getApelido();
//        return "Usuario{" +
//                "senha='" + senha + '\'' +
//                "} " + super.toString();
    }
}
