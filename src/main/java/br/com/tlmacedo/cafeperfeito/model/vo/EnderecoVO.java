package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.vo.enums.EnderecoTipo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "endereco")
public class EnderecoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer tipo;
    @Column(nullable = false, length = 8)
    private String cep;
    @Column(nullable = false, length = 80)
    private String logradouro;
    @Column(nullable = false, length = 10)
    private String numero;
    @Column(nullable = false, length = 80)
    private String complemento;
    @Column(nullable = false, length = 50)
    private String bairro;
    @Column(nullable = false, length = 80)
    private String prox;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "municipio_id", foreignKey = @ForeignKey(name = "fk_endereco_municipio_id"))
    private MunicipioVO municipio;

    public EnderecoVO() {
    }

    public EnderecoVO(EnderecoTipo tipo, String cep, String logradouro, String numero, String complemento, String bairro, String prox, MunicipioVO municipio) {
        this.tipo = tipo.getCod();
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.prox = prox;
        this.municipio = municipio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnderecoTipo getTipo() {
        return EnderecoTipo.toEnum(tipo);
    }

    public void setTipo(EnderecoTipo tipo) {
        this.tipo = tipo.getCod();
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getProx() {
        return prox;
    }

    public void setProx(String prox) {
        this.prox = prox;
    }

    public MunicipioVO getMunicipio() {
        return municipio;
    }

    public void setMunicipio(MunicipioVO municipio) {
        this.municipio = municipio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnderecoVO)) return false;

        EnderecoVO that = (EnderecoVO) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "EnderecoVO{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", cep='" + cep + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", numero='" + numero + '\'' +
                ", complemento='" + complemento + '\'' +
                ", bairro='" + bairro + '\'' +
                ", prox='" + prox + '\'' +
                ", municipio=" + municipio +
                '}';
    }
}
