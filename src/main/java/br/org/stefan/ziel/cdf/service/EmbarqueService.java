package br.org.stefan.ziel.cdf.service;

import br.org.stefan.ziel.cdf.domain.Embarque;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Embarque.
 */
public interface EmbarqueService {

    /**
     * Save a embarque.
     *
     * @param embarque the entity to save
     * @return the persisted entity
     */
    Embarque save(Embarque embarque);

    /**
     * Get all the embarques.
     *
     * @return the list of entities
     */
    List<Embarque> findAll();


    /**
     * Get the "id" embarque.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Embarque> findOne(Long id);

    /**
     * Delete the "id" embarque.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
