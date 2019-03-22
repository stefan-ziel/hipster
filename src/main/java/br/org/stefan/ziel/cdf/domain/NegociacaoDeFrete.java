package br.org.stefan.ziel.cdf.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import br.org.stefan.ziel.cdf.domain.enumeration.CategoriaDeVeiculo;

/**
 * A NegociacaoDeFrete.
 */
@Entity
@Table(name = "negociacao_de_frete")
public class NegociacaoDeFrete implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_de_veiculo")
    private CategoriaDeVeiculo categoriaDeVeiculo;

    @NotNull
    @Column(name = "peso_de", precision = 10, scale = 2, nullable = false)
    private BigDecimal pesoDe;

    @NotNull
    @Column(name = "peso_ate", precision = 10, scale = 2, nullable = false)
    private BigDecimal pesoAte;

    @NotNull
    @Column(name = "preco_por_quilometro", precision = 10, scale = 2, nullable = false)
    private BigDecimal precoPorQuilometro;

    @NotNull
    @Column(name = "prazo_de_entrega", nullable = false)
    private Integer prazoDeEntrega;

    @ManyToOne
    @JsonIgnoreProperties("negociacaoDeFretes")
    private Embarcadora embarcadora;

    @ManyToOne
    @JsonIgnoreProperties("negociacaoDeFretes")
    private Transportadora transportadora;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoriaDeVeiculo getCategoriaDeVeiculo() {
        return categoriaDeVeiculo;
    }

    public NegociacaoDeFrete categoriaDeVeiculo(CategoriaDeVeiculo categoriaDeVeiculo) {
        this.categoriaDeVeiculo = categoriaDeVeiculo;
        return this;
    }

    public void setCategoriaDeVeiculo(CategoriaDeVeiculo categoriaDeVeiculo) {
        this.categoriaDeVeiculo = categoriaDeVeiculo;
    }

    public BigDecimal getPesoDe() {
        return pesoDe;
    }

    public NegociacaoDeFrete pesoDe(BigDecimal pesoDe) {
        this.pesoDe = pesoDe;
        return this;
    }

    public void setPesoDe(BigDecimal pesoDe) {
        this.pesoDe = pesoDe;
    }

    public BigDecimal getPesoAte() {
        return pesoAte;
    }

    public NegociacaoDeFrete pesoAte(BigDecimal pesoAte) {
        this.pesoAte = pesoAte;
        return this;
    }

    public void setPesoAte(BigDecimal pesoAte) {
        this.pesoAte = pesoAte;
    }

    public BigDecimal getPrecoPorQuilometro() {
        return precoPorQuilometro;
    }

    public NegociacaoDeFrete precoPorQuilometro(BigDecimal precoPorQuilometro) {
        this.precoPorQuilometro = precoPorQuilometro;
        return this;
    }

    public void setPrecoPorQuilometro(BigDecimal precoPorQuilometro) {
        this.precoPorQuilometro = precoPorQuilometro;
    }

    public Integer getPrazoDeEntrega() {
        return prazoDeEntrega;
    }

    public NegociacaoDeFrete prazoDeEntrega(Integer prazoDeEntrega) {
        this.prazoDeEntrega = prazoDeEntrega;
        return this;
    }

    public void setPrazoDeEntrega(Integer prazoDeEntrega) {
        this.prazoDeEntrega = prazoDeEntrega;
    }

    public Embarcadora getEmbarcadora() {
        return embarcadora;
    }

    public NegociacaoDeFrete embarcadora(Embarcadora embarcadora) {
        this.embarcadora = embarcadora;
        return this;
    }

    public void setEmbarcadora(Embarcadora embarcadora) {
        this.embarcadora = embarcadora;
    }

    public Transportadora getTransportadora() {
        return transportadora;
    }

    public NegociacaoDeFrete transportadora(Transportadora transportadora) {
        this.transportadora = transportadora;
        return this;
    }

    public void setTransportadora(Transportadora transportadora) {
        this.transportadora = transportadora;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NegociacaoDeFrete negociacaoDeFrete = (NegociacaoDeFrete) o;
        if (negociacaoDeFrete.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), negociacaoDeFrete.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NegociacaoDeFrete{" +
            "id=" + getId() +
            ", categoriaDeVeiculo='" + getCategoriaDeVeiculo() + "'" +
            ", pesoDe=" + getPesoDe() +
            ", pesoAte=" + getPesoAte() +
            ", precoPorQuilometro=" + getPrecoPorQuilometro() +
            ", prazoDeEntrega=" + getPrazoDeEntrega() +
            "}";
    }
}
