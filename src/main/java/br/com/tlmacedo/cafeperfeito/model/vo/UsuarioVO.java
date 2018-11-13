package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.vo.enums.SituacaoNoSistema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "usuario")
public class UsuarioVO extends ColaboradorVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(length = 60)
    private String senha;
    private Integer situacao;

    public UsuarioVO() {
    }

    public UsuarioVO(String nome, String apelido, String ctps, LocalDateTime dataAdmisao, BigDecimal salario, Boolean ativo, CargoVO cargoVO, EmpresaVO trabalha, String senha, Integer situacao) {
        super(nome, apelido, ctps, dataAdmisao, salario, ativo, cargoVO, trabalha);
        this.senha = senha;
        this.situacao = situacao;
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


    //    private CargoVO cargo;
//
//    private Collection<EmpresaVO> empresaCadastro;
//
//    private Collection<EmpresaVO> empresaAtualiza;


    public String getDetalhesUsuario() {
        StringBuilder usuarioDetalhe = new StringBuilder();
        Optional.of(getNome()).ifPresent(c ->
                usuarioDetalhe.append(String.format("Usuário: %s;", getNome()))
        );
        Optional.of(getApelido()).ifPresent(c ->
                usuarioDetalhe.append(String.format("Apelido: %s;", getApelido()))
        );
        Optional.of(getCargoVO()).ifPresent(c ->
                usuarioDetalhe.append(String.format("Cargo: %s;", c.getDescricao()))
        );
        Optional.of(getTrabalha()).ifPresent(c -> {
            usuarioDetalhe.append(String.format("Loja: %s (%s);", c.getRazao(), c.getFantasia()));
            Optional.of(getTrabalha().getEnderecoVOS().get(0)).ifPresent(end ->
                    usuarioDetalhe.append(String.format("Endereço: %s, %s - %s;",
                            end.getLogradouro(), end.getNumero(), end.getBairro())));
        });
        return usuarioDetalhe.toString();
    }

    @Override
    public String toString() {
        return getApelido();
    }
}
