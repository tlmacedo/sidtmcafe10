package br.com.tlmacedo.cafeperfeito.model.dto;

import br.com.tlmacedo.cafeperfeito.model.vo.CargoVO;
import br.com.tlmacedo.cafeperfeito.model.vo.EmpresaVO;
import br.com.tlmacedo.cafeperfeito.model.vo.TelefoneVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.tlmacedo.cafeperfeito.interfaces.Constants.DTF_DATA;

public class ColaboradorDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String apelido;
    private String ctps;
    private String dataAdmisao;
    private BigDecimal salario;
    private Boolean ativo;
    private String cargo;
    private String trabalha;
    private List<String> telefones = new ArrayList<>();

    public ColaboradorDTO() {
    }

    public ColaboradorDTO(Long id, String nome, String apelido, String ctps, String dataAdmisao, BigDecimal salario, Boolean ativo, String cargo, String trabalha) {
        this.id = id;
        this.nome = nome;
        this.apelido = apelido;
        this.ctps = ctps;
        this.dataAdmisao = dataAdmisao;
        this.salario = salario;
        this.ativo = ativo;
        this.cargo = cargo;
        this.trabalha = trabalha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

    public String getDataAdmisao() {
        return dataAdmisao;
    }

    public void setDataAdmisao(LocalDateTime dataAdmisao) {
        this.dataAdmisao = DTF_DATA.format(dataAdmisao);
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(CargoVO cargoVO) {
        this.cargo = cargoVO.getDescricao();
    }

    public String getTrabalha() {
        return trabalha;
    }

    public void setTrabalha(EmpresaVO trabalha) {
        this.trabalha = String.format("%s (%s)", trabalha.getRazao(), trabalha.getFantasia());
    }

    public List<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<TelefoneVO> telefoneVOS) {
        for (TelefoneVO tel : telefoneVOS)
            this.telefones.add(String.format("(%d)%s [%s-%s]",
                    tel.getTelefoneOperadoraVO().getDdd(),
                    tel.getDescricao(),
                    tel.getTelefoneOperadoraVO().getDescricao(),
                    tel.getTelefoneOperadoraVO().getTipo().getDescricao()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColaboradorDTO)) return false;

        ColaboradorDTO that = (ColaboradorDTO) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }


    @Override
    public String toString() {
        return "ColaboradorDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", apelido='" + apelido + '\'' +
                ", ctps='" + ctps + '\'' +
                ", dataAdmisao='" + dataAdmisao + '\'' +
                ", salario=" + salario +
                ", ativo=" + ativo +
                ", cargo='" + cargo + '\'' +
                ", trabalha='" + trabalha + '\'' +
                ", telefones=" + telefones +
                '}';
    }
}
