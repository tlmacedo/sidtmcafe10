package br.com.cafeperfeito.sidtmcafe.model.vo;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
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
    private Boolean ativo;
    @ManyToOne
    @JoinColumn(name = "cargo_id", foreignKey = @ForeignKey(name = "fk_colaborador_cargo_id"))
    private Cargo cargo;
    @ManyToOne
    @JoinColumn(name = "empresa_id", foreignKey = @ForeignKey(name = "fk_colaborador_empresa_id"))
    private Empresa trabalha;
    @OneToMany
    private List<Telefone> telefones = new ArrayList<>();

    public Colaborador() {
    }

    public Colaborador(String nome, String apelido, String ctps, LocalDateTime dataAdmisao, BigDecimal salario, Boolean ativo, Cargo cargo, Empresa trabalha) {
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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
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

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
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

    public String getDetalheColaborador() {
        StringBuilder detColaborador = new StringBuilder();
        Optional.of(getNome()).ifPresent(c ->
                detColaborador.append(String.format("usuario: %s;", getNome()))
        );
        Optional.of(getApelido()).ifPresent(c ->
                detColaborador.append(String.format("apelido: %s;", getApelido()))
        );
        Optional.of(getCargo()).ifPresent(c ->
                detColaborador.append(String.format("cargo: %s;", getCargo().getDescricao()))
        );
//        Optional.ofNullable(lojaVO).ifPresent(c -> {
//            detColaborador.append(String.format("loja: %s;", lojaVO.getFantasia()));
//            Optional.ofNullable(lojaVO.getTabEnderecoVOList().get(0)).ifPresent(c1 ->
//                    detColaborador.append(String.format("end: %s;", (String.format(" %s, %s - %s", lojaVO.getTabEnderecoVOList().get(0).getLogradouro(), lojaVO.getTabEnderecoVOList().get(0).getNumero(), lojaVO.getTabEnderecoVOList().get(0).getBairro()))))
//            );
//        });
        return detColaborador.toString();
    }

    @Override
    public String toString() {
        return "Colaborador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", apelido='" + apelido + '\'' +
                ", ctps='" + ctps + '\'' +
                ", dataAdmisao=" + dataAdmisao +
                ", salario=" + salario +
                ", ativo=" + ativo +
                ", cargo=" + cargo +
                ", trabalha=" + trabalha +
                ", telefones=" + telefones +
                '}';
    }
}
