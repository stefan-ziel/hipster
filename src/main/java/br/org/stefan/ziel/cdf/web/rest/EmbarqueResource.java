package br.org.stefan.ziel.cdf.web.rest;
import br.org.stefan.ziel.cdf.domain.Embarque;
import br.org.stefan.ziel.cdf.service.EmbarqueService;
import br.org.stefan.ziel.cdf.web.rest.errors.BadRequestAlertException;
import br.org.stefan.ziel.cdf.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Embarque.
 */
@RestController
@RequestMapping("/api")
public class EmbarqueResource {

    private final Logger log = LoggerFactory.getLogger(EmbarqueResource.class);

    private static final String ENTITY_NAME = "embarque";

    private final EmbarqueService embarqueService;

    public EmbarqueResource(EmbarqueService embarqueService) {
        this.embarqueService = embarqueService;
    }

    /**
     * POST  /embarques : Create a new embarque.
     *
     * @param embarque the embarque to create
     * @return the ResponseEntity with status 201 (Created) and with body the new embarque, or with status 400 (Bad Request) if the embarque has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/embarques")
    public ResponseEntity<Embarque> createEmbarque(@Valid @RequestBody Embarque embarque) throws URISyntaxException {
        log.debug("REST request to save Embarque : {}", embarque);
        if (embarque.getId() != null) {
            throw new BadRequestAlertException("A new embarque cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Embarque result = embarqueService.save(embarque);
        return ResponseEntity.created(new URI("/api/embarques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /embarques : Updates an existing embarque.
     *
     * @param embarque the embarque to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated embarque,
     * or with status 400 (Bad Request) if the embarque is not valid,
     * or with status 500 (Internal Server Error) if the embarque couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/embarques")
    public ResponseEntity<Embarque> updateEmbarque(@Valid @RequestBody Embarque embarque) throws URISyntaxException {
        log.debug("REST request to update Embarque : {}", embarque);
        if (embarque.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Embarque result = embarqueService.save(embarque);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, embarque.getId().toString()))
            .body(result);
    }

    /**
     * GET  /embarques : get all the embarques.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of embarques in body
     */
    @GetMapping("/embarques")
    public List<Embarque> getAllEmbarques() {
        log.debug("REST request to get all Embarques");
        return embarqueService.findAll();
    }

    /**
     * GET  /embarques/:id : get the "id" embarque.
     *
     * @param id the id of the embarque to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the embarque, or with status 404 (Not Found)
     */
    @GetMapping("/embarques/{id}")
    public ResponseEntity<Embarque> getEmbarque(@PathVariable Long id) {
        log.debug("REST request to get Embarque : {}", id);
        Optional<Embarque> embarque = embarqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(embarque);
    }

    /**
     * DELETE  /embarques/:id : delete the "id" embarque.
     *
     * @param id the id of the embarque to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/embarques/{id}")
    public ResponseEntity<Void> deleteEmbarque(@PathVariable Long id) {
        log.debug("REST request to delete Embarque : {}", id);
        embarqueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
