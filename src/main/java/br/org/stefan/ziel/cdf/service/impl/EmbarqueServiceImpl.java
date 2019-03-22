package br.org.stefan.ziel.cdf.service.impl;

import br.org.stefan.ziel.cdf.service.EmbarqueService;
import br.org.stefan.ziel.cdf.domain.Embarque;
import br.org.stefan.ziel.cdf.repository.EmbarqueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Embarque.
 */
@Service
@Transactional
public class EmbarqueServiceImpl implements EmbarqueService {

    private final Logger log = LoggerFactory.getLogger(EmbarqueServiceImpl.class);

    private final EmbarqueRepository embarqueRepository;

    public EmbarqueServiceImpl(EmbarqueRepository embarqueRepository) {
        this.embarqueRepository = embarqueRepository;
    }

    /**
     * Save a embarque.
     *
     * @param embarque the entity to save
     * @return the persisted entity
     */
    @Override
    public Embarque save(Embarque embarque) {
        log.debug("Request to save Embarque : {}", embarque);
        return embarqueRepository.save(embarque);
    }

    /**
     * Get all the embarques.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Embarque> findAll() {
        log.debug("Request to get all Embarques");
        return embarqueRepository.findAll();
    }


    /**
     * Get one embarque by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Embarque> findOne(Long id) {
        log.debug("Request to get Embarque : {}", id);
        return embarqueRepository.findById(id);
    }

    /**
     * Delete the embarque by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Embarque : {}", id);
        embarqueRepository.deleteById(id);
    }
}
