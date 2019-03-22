package br.org.stefan.ziel.cdf.service.impl;

import br.org.stefan.ziel.cdf.service.EmbarcadoraService;
import br.org.stefan.ziel.cdf.domain.Embarcadora;
import br.org.stefan.ziel.cdf.repository.EmbarcadoraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Embarcadora.
 */
@Service
@Transactional
public class EmbarcadoraServiceImpl implements EmbarcadoraService {

    private final Logger log = LoggerFactory.getLogger(EmbarcadoraServiceImpl.class);

    private final EmbarcadoraRepository embarcadoraRepository;

    public EmbarcadoraServiceImpl(EmbarcadoraRepository embarcadoraRepository) {
        this.embarcadoraRepository = embarcadoraRepository;
    }

    /**
     * Save a embarcadora.
     *
     * @param embarcadora the entity to save
     * @return the persisted entity
     */
    @Override
    public Embarcadora save(Embarcadora embarcadora) {
        log.debug("Request to save Embarcadora : {}", embarcadora);
        return embarcadoraRepository.save(embarcadora);
    }

    /**
     * Get all the embarcadoras.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Embarcadora> findAll() {
        log.debug("Request to get all Embarcadoras");
        return embarcadoraRepository.findAll();
    }


    /**
     * Get one embarcadora by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Embarcadora> findOne(Long id) {
        log.debug("Request to get Embarcadora : {}", id);
        return embarcadoraRepository.findById(id);
    }

    /**
     * Delete the embarcadora by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Embarcadora : {}", id);
        embarcadoraRepository.deleteById(id);
    }
}
