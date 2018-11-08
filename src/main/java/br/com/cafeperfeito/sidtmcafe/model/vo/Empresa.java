package br.com.cafeperfeito.sidtmcafe.model.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean isLojaSistema;
    private Boolean isJuridica;
    @Column(length = 14)
    private String cnpjCpf;
    private Boolean isIsento;
    @Column(length = 14)
    private String ieRg;
    @Column(length = 80)
    private String razao;
    @Column(length = 80)
    private String fantasia;
    private Boolean isCliente;
    private Boolean isFornecedor;
    private Boolean isTransportadora;
    @ManyToOne
    @JoinColumn(name = "usuarioCadastro_id", foreignKey = @ForeignKey(name = "fk_empresa_usuarioCadastro_id"))
    private Usuario usuarioCadastro;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @ManyToOne
    @JoinColumn(name = "usuarioAtualizacao_id", foreignKey = @ForeignKey(name = "fk_empresa_usuarioAtualizacao_id"))
    private Usuario usuarioAtualizacao;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAbetura;
    @Column(length = 150)
    private String naturezaJuridica;
    @ManyToOne
    @JoinColumn(name = "situacaoNoSistema_id", foreignKey = @ForeignKey(name = "fk_empresa_situacaoNoSistema_id"))
    private SituacaoNoSistema situacaoNoSistema;

    public Empresa() {
    }

    public Empresa(Boolean isLojaSistema, Boolean isJuridica, String cnpjCpf, Boolean isIsento, String ieRg, String razao, String fantasia, Boolean isCliente, Boolean isFornecedor, Boolean isTransportadora, Usuario usuarioCadastro, Date dataCadastro, Usuario usuarioAtualizacao, Date dataAtualizacao, Date dataAbetura, String naturezaJuridica, SituacaoNoSistema situacaoNoSistema) {
        this.isLojaSistema = isLojaSistema;
        this.isJuridica = isJuridica;
        this.cnpjCpf = cnpjCpf;
        this.isIsento = isIsento;
        this.ieRg = ieRg;
        this.razao = razao;
        this.fantasia = fantasia;
        this.isCliente = isCliente;
        this.isFornecedor = isFornecedor;
        this.isTransportadora = isTransportadora;
        this.usuarioCadastro = usuarioCadastro;
        this.dataCadastro = dataCadastro;
        this.usuarioAtualizacao = usuarioAtualizacao;
        this.dataAtualizacao = dataAtualizacao;
        this.dataAbetura = dataAbetura;
        this.naturezaJuridica = naturezaJuridica;
        this.situacaoNoSistema = situacaoNoSistema;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getLojaSistema() {
        return isLojaSistema;
    }

    public void setLojaSistema(Boolean lojaSistema) {
        isLojaSistema = lojaSistema;
    }

    public Boolean getJuridica() {
        return isJuridica;
    }

    public void setJuridica(Boolean juridica) {
        isJuridica = juridica;
    }

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }

    public Boolean getIsento() {
        return isIsento;
    }

    public void setIsento(Boolean isento) {
        isIsento = isento;
    }

    public String getIeRg() {
        return ieRg;
    }

    public void setIeRg(String ieRg) {
        this.ieRg = ieRg;
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao = razao;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public Boolean getCliente() {
        return isCliente;
    }

    public void setCliente(Boolean cliente) {
        isCliente = cliente;
    }

    public Boolean getFornecedor() {
        return isFornecedor;
    }

    public void setFornecedor(Boolean fornecedor) {
        isFornecedor = fornecedor;
    }

    public Boolean getTransportadora() {
        return isTransportadora;
    }

    public void setTransportadora(Boolean transportadora) {
        isTransportadora = transportadora;
    }

    public Usuario getUsuarioCadastro() {
        return usuarioCadastro;
    }

    public void setUsuarioCadastro(Usuario usuarioCadastro) {
        this.usuarioCadastro = usuarioCadastro;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Usuario getUsuarioAtualizacao() {
        return usuarioAtualizacao;
    }

    public void setUsuarioAtualizacao(Usuario usuarioAtualizacao) {
        this.usuarioAtualizacao = usuarioAtualizacao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Date getDataAbetura() {
        return dataAbetura;
    }

    public void setDataAbetura(Date dataAbetura) {
        this.dataAbetura = dataAbetura;
    }

    public String getNaturezaJuridica() {
        return naturezaJuridica;
    }

    public void setNaturezaJuridica(String naturezaJuridica) {
        this.naturezaJuridica = naturezaJuridica;
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
        if (!(o instanceof Empresa)) return false;

        Empresa empresa = (Empresa) o;

        return getId().equals(empresa.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
