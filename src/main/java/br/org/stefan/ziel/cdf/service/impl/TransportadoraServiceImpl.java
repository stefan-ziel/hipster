package br.org.stefan.ziel.cdf.service.impl;

import br.org.stefan.ziel.cdf.service.TransportadoraService;
import br.org.stefan.ziel.cdf.domain.Transportadora;
import br.org.stefan.ziel.cdf.repository.TransportadoraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Transportadora.
 */
@Service
@Transactional
public class TransportadoraServiceImpl implements TransportadoraService {

    private final Logger log = LoggerFactory.getLogger(TransportadoraServiceImpl.class);

    private final TransportadoraRepository transportadoraRepository;

    public TransportadoraServiceImpl(TransportadoraRepository transportadoraRepository) {
        this.transportadoraRepository = transportadoraRepository;
    }

    /**
     * Save a transportadora.
     *
     * @param transportadora the entity to save
     * @return the persisted entity
     */
    @Override
    public Transportadora save(Transportadora transportadora) {
        log.debug("Request to save Transportadora : {}", transportadora);
        return transportadoraRepository.save(transportadora);
    }

    /**
     * Get all the transportadoras.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Transportadora> findAll() {
        log.debug("Request to get all Transportadoras");
        return transportadoraRepository.findAll();
    }


    /**
     * Get one transportadora by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Transportadora> findOne(Long id) {
        log.debug("Request to get Transportadora : {}", id);
        return transportadoraRepository.findById(id);
    }

    /**
     * Delete the transportadora by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Transportadora : {}", id);
        transportadoraRepository.deleteById(id);
    }
}
