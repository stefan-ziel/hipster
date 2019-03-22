package br.org.stefan.ziel.cdf.service;

import br.org.stefan.ziel.cdf.domain.Transportadora;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Transportadora.
 */
public interface TransportadoraService {

    /**
     * Save a transportadora.
     *
     * @param transportadora the entity to save
     * @return the persisted entity
     */
    Transportadora save(Transportadora transportadora);

    /**
     * Get all the transportadoras.
     *
     * @return the list of entities
     */
    List<Transportadora> findAll();


    /**
     * Get the "id" transportadora.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Transportadora> findOne(Long id);

    /**
     * Delete the "id" transportadora.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
