package br.org.stefan.ziel.cdf.repository;

import br.org.stefan.ziel.cdf.domain.Embarque;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Embarque entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmbarqueRepository extends JpaRepository<Embarque, Long> {
	
	List<Embarque> findByDataDeColetaBetweenOrderByEmbarcadora(Instant dataDe, Instant dataAte );
	
}
