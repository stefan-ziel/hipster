package br.org.stefan.ziel.cdf.web.rest;
import br.org.stefan.ziel.cdf.domain.Transportadora;
import br.org.stefan.ziel.cdf.service.TransportadoraService;
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
 * REST controller for managing Transportadora.
 */
@RestController
@RequestMapping("/api")
public class TransportadoraResource {

    private final Logger log = LoggerFactory.getLogger(TransportadoraResource.class);

    private static final String ENTITY_NAME = "transportadora";

    private final TransportadoraService transportadoraService;

    public TransportadoraResource(TransportadoraService transportadoraService) {
        this.transportadoraService = transportadoraService;
    }

    /**
     * POST  /transportadoras : Create a new transportadora.
     *
     * @param transportadora the transportadora to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transportadora, or with status 400 (Bad Request) if the transportadora has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transportadoras")
    public ResponseEntity<Transportadora> createTransportadora(@Valid @RequestBody Transportadora transportadora) throws URISyntaxException {
        log.debug("REST request to save Transportadora : {}", transportadora);
        if (transportadora.getId() != null) {
            throw new BadRequestAlertException("A new transportadora cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Transportadora result = transportadoraService.save(transportadora);
        return ResponseEntity.created(new URI("/api/transportadoras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transportadoras : Updates an existing transportadora.
     *
     * @param transportadora the transportadora to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transportadora,
     * or with status 400 (Bad Request) if the transportadora is not valid,
     * or with status 500 (Internal Server Error) if the transportadora couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transportadoras")
    public ResponseEntity<Transportadora> updateTransportadora(@Valid @RequestBody Transportadora transportadora) throws URISyntaxException {
        log.debug("REST request to update Transportadora : {}", transportadora);
        if (transportadora.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Transportadora result = transportadoraService.save(transportadora);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transportadora.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transportadoras : get all the transportadoras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of transportadoras in body
     */
    @GetMapping("/transportadoras")
    public List<Transportadora> getAllTransportadoras() {
        log.debug("REST request to get all Transportadoras");
        return transportadoraService.findAll();
    }

    /**
     * GET  /transportadoras/:id : get the "id" transportadora.
     *
     * @param id the id of the transportadora to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transportadora, or with status 404 (Not Found)
     */
    @GetMapping("/transportadoras/{id}")
    public ResponseEntity<Transportadora> getTransportadora(@PathVariable Long id) {
        log.debug("REST request to get Transportadora : {}", id);
        Optional<Transportadora> transportadora = transportadoraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transportadora);
    }

    /**
     * DELETE  /transportadoras/:id : delete the "id" transportadora.
     *
     * @param id the id of the transportadora to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transportadoras/{id}")
    public ResponseEntity<Void> deleteTransportadora(@PathVariable Long id) {
        log.debug("REST request to delete Transportadora : {}", id);
        transportadoraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
