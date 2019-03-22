package br.org.stefan.ziel.cdf.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A Resultado.
 */
@Entity
@Table(name = "resultado")
public class Resultado implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "valor_calculado", precision = 10, scale = 2, nullable = false)
    private BigDecimal valorCalculado;

    @NotNull
    @Column(name = "previsao_de_entrega", nullable = false)
    private Instant previsaoDeEntrega;

    @OneToOne
    @JoinColumn(unique = true)
    private Embarque embarque;

    @ManyToOne
    @JsonIgnoreProperties("resultados")
    private NegociacaoDeFrete melhorNegociacaoDeFrete;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorCalculado() {
        return valorCalculado;
    }

    public Resultado valorCalculado(BigDecimal valorCalculado) {
        this.valorCalculado = valorCalculado;
        return this;
    }

    public void setValorCalculado(BigDecimal valorCalculado) {
        this.valorCalculado = valorCalculado;
    }

    public Instant getPrevisaoDeEntrega() {
        return previsaoDeEntrega;
    }

    public Resultado previsaoDeEntrega(Instant previsaoDeEntrega) {
        this.previsaoDeEntrega = previsaoDeEntrega;
        return this;
    }

    public void setPrevisaoDeEntrega(Instant previsaoDeEntrega) {
        this.previsaoDeEntrega = previsaoDeEntrega;
    }

    public Embarque getEmbarque() {
        return embarque;
    }

    public Resultado embarque(Embarque embarque) {
        this.embarque = embarque;
        return this;
    }

    public void setEmbarque(Embarque embarque) {
        this.embarque = embarque;
    }

    public NegociacaoDeFrete getMelhorNegociacaoDeFrete() {
        return melhorNegociacaoDeFrete;
    }

    public Resultado melhorNegociacaoDeFrete(NegociacaoDeFrete negociacaoDeFrete) {
        this.melhorNegociacaoDeFrete = negociacaoDeFrete;
        return this;
    }

    public void setMelhorNegociacaoDeFrete(NegociacaoDeFrete negociacaoDeFrete) {
        this.melhorNegociacaoDeFrete = negociacaoDeFrete;
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
        Resultado resultado = (Resultado) o;
        if (resultado.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resultado.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Resultado{" +
            "id=" + getId() +
            ", valorCalculado=" + getValorCalculado() +
            ", previsaoDeEntrega='" + getPrevisaoDeEntrega() + "'" +
            "}";
    }
}
