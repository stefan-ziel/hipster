package br.org.stefan.ziel.cdf.service.impl;

import br.org.stefan.ziel.cdf.service.NegociacaoDeFreteService;
import br.org.stefan.ziel.cdf.domain.NegociacaoDeFrete;
import br.org.stefan.ziel.cdf.repository.NegociacaoDeFreteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing NegociacaoDeFrete.
 */
@Service
@Transactional
public class NegociacaoDeFreteServiceImpl implements NegociacaoDeFreteService {

    private final Logger log = LoggerFactory.getLogger(NegociacaoDeFreteServiceImpl.class);

    private final NegociacaoDeFreteRepository negociacaoDeFreteRepository;

    public NegociacaoDeFreteServiceImpl(NegociacaoDeFreteRepository negociacaoDeFreteRepository) {
        this.negociacaoDeFreteRepository = negociacaoDeFreteRepository;
    }

    /**
     * Save a negociacaoDeFrete.
     *
     * @param negociacaoDeFrete the entity to save
     * @return the persisted entity
     */
    @Override
    public NegociacaoDeFrete save(NegociacaoDeFrete negociacaoDeFrete) {
        log.debug("Request to save NegociacaoDeFrete : {}", negociacaoDeFrete);
        return negociacaoDeFreteRepository.save(negociacaoDeFrete);
    }

    /**
     * Get all the negociacaoDeFretes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<NegociacaoDeFrete> findAll() {
        log.debug("Request to get all NegociacaoDeFretes");
        return negociacaoDeFreteRepository.findAll();
    }


    /**
     * Get one negociacaoDeFrete by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NegociacaoDeFrete> findOne(Long id) {
        log.debug("Request to get NegociacaoDeFrete : {}", id);
        return negociacaoDeFreteRepository.findById(id);
    }

    /**
     * Delete the negociacaoDeFrete by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NegociacaoDeFrete : {}", id);
        negociacaoDeFreteRepository.deleteById(id);
    }
}
