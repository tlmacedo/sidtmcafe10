package br.com.tlmacedo.cafeperfeito.model.vo;


import br.com.tlmacedo.cafeperfeito.model.vo.enums.SituacaoNoSistema;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empresa")
public class EmpresaVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Boolean isLojaSistema = false;
    @Column(nullable = false)
    private Integer tipo = 2;
    @Column(nullable = false, length = 14)
    private String cnpjCpf;
    @Column(nullable = false)
    private Boolean isIsento = false;
    @Column(nullable = false, length = 14)
    private String ieRg;
    @Column(nullable = false, length = 80)
    private String razao;
    @Column(nullable = false, length = 80)
    private String fantasia;
    @Column(nullable = false)
    private Boolean isCliente = true;
    @Column(nullable = false)
    private Boolean isFornecedor = false;
    @Column(nullable = false)
    private Boolean isTransportadora = false;
    @Column(nullable = false)
    private Integer situacao = 1;
    @ManyToOne()
    @JoinColumn(name = "usuarioCadastro_id", foreignKey = @ForeignKey(name = "fk_empresa_usuarioCadastro_id"))
    private UsuarioVO usuarioVOCadastro;
    //@Column(columnDefinition = "timestamp CURRENT_TIMESTAMP")
    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime dataCadastro;
    @ManyToOne
    @JoinColumn(name = "usuarioAtualizacao_id", foreignKey = @ForeignKey(name = "fk_empresa_usuarioAtualizacao_id"))
    private UsuarioVO usuarioVOAtualizacao;
    //@Column(columnDefinition = "timestamp ON UPDATE CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dataAtualizacao;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime dataAbetura;
    @Column(nullable = false, length = 150)
    private String naturezaJuridica = "";
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EnderecoVO> enderecoVOS = new ArrayList<>();

    //    private UsuarioVO usuarioVOCadastro;
//
//    private UsuarioVO usuarioVOAtualizacao;
//
//    private Collection<Endereco> enderecos;
//
//    private Collection<Endereco> assocEmpresaEndereco;
//
//    private Collection<TelefoneVO> assocEmpresaTelefone;
//
//    private Collection<ColaboradorVO> colaboradores;
//
//    private Collection<EmailHomePageVO> assocEmpresaEmailHomePage;
//
//    private Collection<ContatoVO> assocEmpresaContato;


    public EmpresaVO() {
    }

    public EmpresaVO(Boolean isLojaSistema, Integer tipo, String cnpjCpf, Boolean isIsento, String ieRg, String razao, String fantasia, Boolean isCliente, Boolean isFornecedor, Boolean isTransportadora, SituacaoNoSistema situacao, UsuarioVO usuarioVOCadastro, LocalDateTime dataCadastro, UsuarioVO usuarioVOAtualizacao, LocalDateTime dataAtualizacao, LocalDateTime dataAbetura, String naturezaJuridica) {
        this.isLojaSistema = isLojaSistema;
        this.tipo = tipo;
        this.cnpjCpf = cnpjCpf;
        this.isIsento = isIsento;
        this.ieRg = ieRg;
        this.razao = razao;
        this.fantasia = fantasia;
        this.isCliente = isCliente;
        this.isFornecedor = isFornecedor;
        this.isTransportadora = isTransportadora;
        this.situacao = situacao.getCod();
        this.usuarioVOCadastro = usuarioVOCadastro;
        this.dataCadastro = dataCadastro;
        this.usuarioVOAtualizacao = usuarioVOAtualizacao;
        this.dataAtualizacao = dataAtualizacao;
        this.dataAbetura = dataAbetura;
        this.naturezaJuridica = naturezaJuridica;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getLojaSistema() {
        return isLojaSistema;
    }

    public void setLojaSistema(Boolean lojaSistema) {
        isLojaSistema = lojaSistema;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
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

    public SituacaoNoSistema getSituacao() {
        return SituacaoNoSistema.toEnum(situacao);
    }

    public void setSituacao(SituacaoNoSistema situacao) {
        this.situacao = situacao.getCod();
    }

    public UsuarioVO getUsuarioVOCadastro() {
        return usuarioVOCadastro;
    }

    public void setUsuarioVOCadastro(UsuarioVO usuarioVOCadastro) {
        this.usuarioVOCadastro = usuarioVOCadastro;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public UsuarioVO getUsuarioVOAtualizacao() {
        return usuarioVOAtualizacao;
    }

    public void setUsuarioVOAtualizacao(UsuarioVO usuarioVOAtualizacao) {
        this.usuarioVOAtualizacao = usuarioVOAtualizacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public LocalDateTime getDataAbetura() {
        return dataAbetura;
    }

    public void setDataAbetura(LocalDateTime dataAbetura) {
        this.dataAbetura = dataAbetura;
    }

    public String getNaturezaJuridica() {
        return naturezaJuridica;
    }

    public void setNaturezaJuridica(String naturezaJuridica) {
        this.naturezaJuridica = naturezaJuridica;
    }

    public List<EnderecoVO> getEnderecoVOS() {
        return enderecoVOS;
    }

    public void setEnderecoVOS(List<EnderecoVO> enderecoVOS) {
        this.enderecoVOS = enderecoVOS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmpresaVO)) return false;

        EmpresaVO empresaVO = (EmpresaVO) o;

        return getId().equals(empresaVO.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "EmpresaVO{" +
                "id=" + id +
                ", isLojaSistema=" + isLojaSistema +
                ", tipo=" + tipo +
                ", cnpjCpf='" + cnpjCpf + '\'' +
                ", isIsento=" + isIsento +
                ", ieRg='" + ieRg + '\'' +
                ", razao='" + razao + '\'' +
                ", fantasia='" + fantasia + '\'' +
                ", isCliente=" + isCliente +
                ", isFornecedor=" + isFornecedor +
                ", isTransportadora=" + isTransportadora +
                ", situacao=" + situacao +
                ", usuarioVOCadastro=" + usuarioVOCadastro +
                ", dataCadastro=" + dataCadastro +
                ", usuarioVOAtualizacao=" + usuarioVOAtualizacao +
                ", dataAtualizacao=" + dataAtualizacao +
                ", dataAbetura=" + dataAbetura +
                ", naturezaJuridica='" + naturezaJuridica + '\'' +
                '}';
    }
}
