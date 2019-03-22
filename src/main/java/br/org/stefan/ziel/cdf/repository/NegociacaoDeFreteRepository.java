package br.org.stefan.ziel.cdf.repository;

import br.org.stefan.ziel.cdf.domain.Embarcadora;
import br.org.stefan.ziel.cdf.domain.NegociacaoDeFrete;
import br.org.stefan.ziel.cdf.domain.enumeration.CategoriaDeVeiculo;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NegociacaoDeFrete entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NegociacaoDeFreteRepository extends JpaRepository<NegociacaoDeFrete, Long> {
	
	List<NegociacaoDeFrete> findByEmbarcadoraAndCategoriaDeVeiculo(Embarcadora embarcadora,CategoriaDeVeiculo categoriaDeVeiculo);
	
}
