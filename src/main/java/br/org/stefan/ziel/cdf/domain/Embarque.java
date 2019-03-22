package br.org.stefan.ziel.cdf.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import br.org.stefan.ziel.cdf.domain.enumeration.CategoriaDeVeiculo;

/**
 * A Embarque.
 */
@Entity
@Table(name = "embarque")
public class Embarque implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_de_veiculo")
    private CategoriaDeVeiculo categoriaDeVeiculo;

    @Column(name = "origem")
    private String origem;

    @Column(name = "destino")
    private String destino;

    @NotNull
    @Column(name = "qilometragem", precision = 10, scale = 2, nullable = false)
    private BigDecimal qilometragem;

    @NotNull
    @Column(name = "peso", precision = 10, scale = 2, nullable = false)
    private BigDecimal peso;

    @NotNull
    @Column(name = "data_de_coleta", nullable = false)
    private Instant dataDeColeta;

    @ManyToOne
    @JsonIgnoreProperties("embarques")
    private Embarcadora embarcadora;

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

    public Embarque categoriaDeVeiculo(CategoriaDeVeiculo categoriaDeVeiculo) {
        this.categoriaDeVeiculo = categoriaDeVeiculo;
        return this;
    }

    public void setCategoriaDeVeiculo(CategoriaDeVeiculo categoriaDeVeiculo) {
        this.categoriaDeVeiculo = categoriaDeVeiculo;
    }

    public String getOrigem() {
        return origem;
    }

    public Embarque origem(String origem) {
        this.origem = origem;
        return this;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public Embarque destino(String destino) {
        this.destino = destino;
        return this;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public BigDecimal getQilometragem() {
        return qilometragem;
    }

    public Embarque qilometragem(BigDecimal qilometragem) {
        this.qilometragem = qilometragem;
        return this;
    }

    public void setQilometragem(BigDecimal qilometragem) {
        this.qilometragem = qilometragem;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public Embarque peso(BigDecimal peso) {
        this.peso = peso;
        return this;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public Instant getDataDeColeta() {
        return dataDeColeta;
    }

    public Embarque dataDeColeta(Instant dataDeColeta) {
        this.dataDeColeta = dataDeColeta;
        return this;
    }

    public void setDataDeColeta(Instant dataDeColeta) {
        this.dataDeColeta = dataDeColeta;
    }

    public Embarcadora getEmbarcadora() {
        return embarcadora;
    }

    public Embarque embarcadora(Embarcadora embarcadora) {
        this.embarcadora = embarcadora;
        return this;
    }

    public void setEmbarcadora(Embarcadora embarcadora) {
        this.embarcadora = embarcadora;
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
        Embarque embarque = (Embarque) o;
        if (embarque.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), embarque.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Embarque{" +
            "id=" + getId() +
            ", categoriaDeVeiculo='" + getCategoriaDeVeiculo() + "'" +
            ", origem='" + getOrigem() + "'" +
            ", destino='" + getDestino() + "'" +
            ", qilometragem=" + getQilometragem() +
            ", peso=" + getPeso() +
            ", dataDeColeta='" + getDataDeColeta() + "'" +
            "}";
    }
}
