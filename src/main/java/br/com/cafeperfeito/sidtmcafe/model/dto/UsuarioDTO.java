package br.com.cafeperfeito.sidtmcafe.model.dto;

import br.com.cafeperfeito.sidtmcafe.model.vo.Cargo;
import br.com.cafeperfeito.sidtmcafe.model.vo.Empresa;
import br.com.cafeperfeito.sidtmcafe.model.vo.enums.SituacaoNoSistema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UsuarioDTO extends ColaboradorDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String senha;
    private Integer situacao;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String nome, String apelido, String ctps, String  dataAdmisao, BigDecimal salario, Boolean ativo, String  cargo, String  trabalha_id, String senha, SituacaoNoSistema situacao) {
        super(id, nome, apelido, ctps, dataAdmisao, salario, ativo, cargo, trabalha_id);
        this.senha = senha;
        this.situacao = situacao.getCod();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public SituacaoNoSistema getSituacao() {
        return SituacaoNoSistema.toEnum(situacao);
    }

    public void setSituacao(SituacaoNoSistema situacao) {
        this.situacao = situacao.getCod();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioDTO)) return false;

        UsuarioDTO that = (UsuarioDTO) o;

        return getSenha().equals(that.getSenha());
    }

    @Override
    public int hashCode() {
        return getSenha().hashCode();
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "senha='" + senha + '\'' +
                ", situacao=" + situacao +
                "} " + super.toString();
    }
}
