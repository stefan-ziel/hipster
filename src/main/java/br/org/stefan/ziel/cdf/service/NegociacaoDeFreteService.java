package br.org.stefan.ziel.cdf.service;

import br.org.stefan.ziel.cdf.domain.NegociacaoDeFrete;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing NegociacaoDeFrete.
 */
public interface NegociacaoDeFreteService {

    /**
     * Save a negociacaoDeFrete.
     *
     * @param negociacaoDeFrete the entity to save
     * @return the persisted entity
     */
    NegociacaoDeFrete save(NegociacaoDeFrete negociacaoDeFrete);

    /**
     * Get all the negociacaoDeFretes.
     *
     * @return the list of entities
     */
    List<NegociacaoDeFrete> findAll();


    /**
     * Get the "id" negociacaoDeFrete.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<NegociacaoDeFrete> findOne(Long id);

    /**
     * Delete the "id" negociacaoDeFrete.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
