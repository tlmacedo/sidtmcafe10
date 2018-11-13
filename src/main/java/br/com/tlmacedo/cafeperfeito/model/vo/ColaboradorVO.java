package br.com.tlmacedo.cafeperfeito.model.vo;


import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "colaborador")
@Inheritance(strategy = InheritanceType.JOINED)
public class ColaboradorVO implements Serializable {
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
    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime dataAdmisao;
    @Column(length = 20, scale = 2, nullable = false)
    private BigDecimal salario;
    @Column(nullable = false)
    private Boolean ativo;
    @ManyToOne
    @JoinColumn(name = "cargo_id", foreignKey = @ForeignKey(name = "fk_colaborador_cargo_id"))
    private CargoVO cargoVO;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "empresa_id", foreignKey = @ForeignKey(name = "fk_colaborador_empresa_id"))
    private EmpresaVO trabalha;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TelefoneVO> telefoneVOS = new ArrayList<>();

    public ColaboradorVO() {
    }

    public ColaboradorVO(String nome, String apelido, String ctps, LocalDateTime dataAdmisao, BigDecimal salario, Boolean ativo, CargoVO cargoVO, EmpresaVO trabalha) {
        this.nome = nome;
        this.apelido = apelido;
        this.ctps = ctps;
        this.dataAdmisao = dataAdmisao;
        this.salario = salario;
        this.ativo = ativo;
        this.cargoVO = cargoVO;
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

    public CargoVO getCargoVO() {
        return cargoVO;
    }

    public void setCargoVO(CargoVO cargoVO) {
        this.cargoVO = cargoVO;
    }

    public EmpresaVO getTrabalha() {
        return trabalha;
    }

    public void setTrabalha(EmpresaVO trabalha) {
        this.trabalha = trabalha;
    }

    public List<TelefoneVO> getTelefoneVOS() {
        return telefoneVOS;
    }

    public void setTelefoneVOS(List<TelefoneVO> telefoneVOS) {
        this.telefoneVOS = telefoneVOS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColaboradorVO)) return false;

        ColaboradorVO that = (ColaboradorVO) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "ColaboradorVO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", apelido='" + apelido + '\'' +
                ", ctps='" + ctps + '\'' +
                ", dataAdmisao=" + dataAdmisao +
                ", salario=" + salario +
                ", ativo=" + ativo +
                ", cargoVO=" + cargoVO +
                ", trabalha=" + trabalha +
                ", telefoneVOS=" + telefoneVOS +
                '}';
    }
}
