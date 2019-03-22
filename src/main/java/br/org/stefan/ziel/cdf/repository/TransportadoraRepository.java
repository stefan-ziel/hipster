package br.org.stefan.ziel.cdf.repository;

import br.org.stefan.ziel.cdf.domain.Transportadora;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Transportadora entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransportadoraRepository extends JpaRepository<Transportadora, Long> {

}
