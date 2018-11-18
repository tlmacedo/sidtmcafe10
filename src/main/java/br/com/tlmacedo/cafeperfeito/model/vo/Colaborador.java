package br.com.tlmacedo.cafeperfeito.model.vo;


import br.com.tlmacedo.cafeperfeito.model.vo.enums.SituacaoNoSistema;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "Colaborador")
@Table(name = "colaborador")
@Inheritance(strategy = InheritanceType.JOINED)
public class Colaborador implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 80)
    private String nome;
    @Column(nullable = false, length = 30)
    private String apelido;
    @Column(nullable = false, length = 30)
    private String ctps;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dataAdmisao;
    @Column(length = 20, scale = 2, nullable = false)
    private BigDecimal salario;
    @Column(nullable = false)
    private Integer ativo;
    @ManyToOne
    @JoinColumn(name = "cargo_id", foreignKey = @ForeignKey(name = "fk_colaborador_cargo_id"))
    private Cargo cargo;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", foreignKey = @ForeignKey(name = "fk_colaborador_empresa_id"))
    private Empresa trabalha;

    public Colaborador() {
    }

    public Colaborador(String nome, String apelido, String ctps, LocalDateTime dataAdmisao, BigDecimal salario, SituacaoNoSistema ativo, Cargo cargo, Empresa trabalha) {
        this.nome = nome;
        this.apelido = apelido;
        this.ctps = ctps;
        this.dataAdmisao = dataAdmisao;
        this.salario = salario;
        this.ativo = ativo.getCod();
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

    public LocalDateTime getDataAdmisao() {
        return dataAdmisao;
    }

    public void setDataAdmisao(LocalDateTime dataAdmisao) {
        this.dataAdmisao = dataAdmisao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public SituacaoNoSistema getAtivo() {
        return SituacaoNoSistema.toEnum(ativo);
    }

    public void setAtivo(SituacaoNoSistema ativo) {
        this.ativo = ativo.getCod();
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Empresa getTrabalha() {
        return trabalha;
    }

    public void setTrabalha(Empresa trabalha) {
        this.trabalha = trabalha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Colaborador)) return false;

        Colaborador that = (Colaborador) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "ColaboradorDAO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", apelido='" + apelido + '\'' +
                ", ctps='" + ctps + '\'' +
                ", dataAdmisao=" + dataAdmisao +
                ", salario=" + salario +
                ", ativo=" + ativo +
                ", cargo=" + cargo +
                ", trabalha=" + trabalha +
                '}';
    }
}
