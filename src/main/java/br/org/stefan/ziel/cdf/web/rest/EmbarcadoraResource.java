package br.org.stefan.ziel.cdf.web.rest;
import br.org.stefan.ziel.cdf.domain.Embarcadora;
import br.org.stefan.ziel.cdf.service.EmbarcadoraService;
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
 * REST controller for managing Embarcadora.
 */
@RestController
@RequestMapping("/api")
public class EmbarcadoraResource {

    private final Logger log = LoggerFactory.getLogger(EmbarcadoraResource.class);

    private static final String ENTITY_NAME = "embarcadora";

    private final EmbarcadoraService embarcadoraService;

    public EmbarcadoraResource(EmbarcadoraService embarcadoraService) {
        this.embarcadoraService = embarcadoraService;
    }

    /**
     * POST  /embarcadoras : Create a new embarcadora.
     *
     * @param embarcadora the embarcadora to create
     * @return the ResponseEntity with status 201 (Created) and with body the new embarcadora, or with status 400 (Bad Request) if the embarcadora has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/embarcadoras")
    public ResponseEntity<Embarcadora> createEmbarcadora(@Valid @RequestBody Embarcadora embarcadora) throws URISyntaxException {
        log.debug("REST request to save Embarcadora : {}", embarcadora);
        if (embarcadora.getId() != null) {
            throw new BadRequestAlertException("A new embarcadora cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Embarcadora result = embarcadoraService.save(embarcadora);
        return ResponseEntity.created(new URI("/api/embarcadoras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /embarcadoras : Updates an existing embarcadora.
     *
     * @param embarcadora the embarcadora to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated embarcadora,
     * or with status 400 (Bad Request) if the embarcadora is not valid,
     * or with status 500 (Internal Server Error) if the embarcadora couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/embarcadoras")
    public ResponseEntity<Embarcadora> updateEmbarcadora(@Valid @RequestBody Embarcadora embarcadora) throws URISyntaxException {
        log.debug("REST request to update Embarcadora : {}", embarcadora);
        if (embarcadora.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Embarcadora result = embarcadoraService.save(embarcadora);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, embarcadora.getId().toString()))
            .body(result);
    }

    /**
     * GET  /embarcadoras : get all the embarcadoras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of embarcadoras in body
     */
    @GetMapping("/embarcadoras")
    public List<Embarcadora> getAllEmbarcadoras() {
        log.debug("REST request to get all Embarcadoras");
        return embarcadoraService.findAll();
    }

    /**
     * GET  /embarcadoras/:id : get the "id" embarcadora.
     *
     * @param id the id of the embarcadora to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the embarcadora, or with status 404 (Not Found)
     */
    @GetMapping("/embarcadoras/{id}")
    public ResponseEntity<Embarcadora> getEmbarcadora(@PathVariable Long id) {
        log.debug("REST request to get Embarcadora : {}", id);
        Optional<Embarcadora> embarcadora = embarcadoraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(embarcadora);
    }

    /**
     * DELETE  /embarcadoras/:id : delete the "id" embarcadora.
     *
     * @param id the id of the embarcadora to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/embarcadoras/{id}")
    public ResponseEntity<Void> deleteEmbarcadora(@PathVariable Long id) {
        log.debug("REST request to delete Embarcadora : {}", id);
        embarcadoraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
