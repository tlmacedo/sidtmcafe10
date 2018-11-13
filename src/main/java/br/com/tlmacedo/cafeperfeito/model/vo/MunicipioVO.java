package br.com.tlmacedo.cafeperfeito.model.vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "municipio")
public class MunicipioVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 80)
    private String descricao;
    @Column(nullable = false)
    private Boolean isCapital;
    @Column(nullable = false, length = 7)
    private String ibge_codigo;
    @Column(nullable = false, length = 2)
    private Integer ddd;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "uf_id", foreignKey = @ForeignKey(name = "fk_municipio_uf_id"))
    private UfVO ufVO;

    public MunicipioVO() {
    }

    public MunicipioVO(String descricao, Boolean isCapital, String ibge_codigo, Integer ddd, UfVO ufVO) {
        this.descricao = descricao;
        this.isCapital = isCapital;
        this.ibge_codigo = ibge_codigo;
        this.ddd = ddd;
        this.ufVO = ufVO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getCapital() {
        return isCapital;
    }

    public void setCapital(Boolean capital) {
        isCapital = capital;
    }

    public String getIbge_codigo() {
        return ibge_codigo;
    }

    public void setIbge_codigo(String ibge_codigo) {
        this.ibge_codigo = ibge_codigo;
    }

    public Integer getDdd() {
        return ddd;
    }

    public void setDdd(Integer ddd) {
        this.ddd = ddd;
    }

    public UfVO getUfVO() {
        return ufVO;
    }

    public void setUfVO(UfVO ufVO) {
        this.ufVO = ufVO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MunicipioVO)) return false;

        MunicipioVO that = (MunicipioVO) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "MunicipioVO{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", isCapital=" + isCapital +
                ", ibge_codigo='" + ibge_codigo + '\'' +
                ", ddd=" + ddd +
                ", ufVO=" + ufVO +
                '}';
    }
}
