package br.org.stefan.ziel.cdf.service;

import br.org.stefan.ziel.cdf.domain.Embarcadora;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Embarcadora.
 */
public interface EmbarcadoraService {

    /**
     * Save a embarcadora.
     *
     * @param embarcadora the entity to save
     * @return the persisted entity
     */
    Embarcadora save(Embarcadora embarcadora);

    /**
     * Get all the embarcadoras.
     *
     * @return the list of entities
     */
    List<Embarcadora> findAll();


    /**
     * Get the "id" embarcadora.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Embarcadora> findOne(Long id);

    /**
     * Delete the "id" embarcadora.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
