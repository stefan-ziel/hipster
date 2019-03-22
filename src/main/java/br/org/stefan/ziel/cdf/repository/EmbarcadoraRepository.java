package br.org.stefan.ziel.cdf.repository;

import br.org.stefan.ziel.cdf.domain.Embarcadora;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Embarcadora entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmbarcadoraRepository extends JpaRepository<Embarcadora, Long> {

}
