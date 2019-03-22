package br.org.stefan.ziel.cdf.repository;

import br.org.stefan.ziel.cdf.domain.Embarque;
import br.org.stefan.ziel.cdf.domain.Resultado;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Resultado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {
	
	Optional<Resultado> findByEmbarque(Embarque embarque);
	
}
