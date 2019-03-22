package br.org.stefan.ziel.cdf.web.rest;
import br.org.stefan.ziel.cdf.domain.NegociacaoDeFrete;
import br.org.stefan.ziel.cdf.service.NegociacaoDeFreteService;
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
 * REST controller for managing NegociacaoDeFrete.
 */
@RestController
@RequestMapping("/api")
public class NegociacaoDeFreteResource {

    private final Logger log = LoggerFactory.getLogger(NegociacaoDeFreteResource.class);

    private static final String ENTITY_NAME = "negociacaoDeFrete";

    private final NegociacaoDeFreteService negociacaoDeFreteService;

    public NegociacaoDeFreteResource(NegociacaoDeFreteService negociacaoDeFreteService) {
        this.negociacaoDeFreteService = negociacaoDeFreteService;
    }

    /**
     * POST  /negociacao-de-fretes : Create a new negociacaoDeFrete.
     *
     * @param negociacaoDeFrete the negociacaoDeFrete to create
     * @return the ResponseEntity with status 201 (Created) and with body the new negociacaoDeFrete, or with status 400 (Bad Request) if the negociacaoDeFrete has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/negociacao-de-fretes")
    public ResponseEntity<NegociacaoDeFrete> createNegociacaoDeFrete(@Valid @RequestBody NegociacaoDeFrete negociacaoDeFrete) throws URISyntaxException {
        log.debug("REST request to save NegociacaoDeFrete : {}", negociacaoDeFrete);
        if (negociacaoDeFrete.getId() != null) {
            throw new BadRequestAlertException("A new negociacaoDeFrete cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NegociacaoDeFrete result = negociacaoDeFreteService.save(negociacaoDeFrete);
        return ResponseEntity.created(new URI("/api/negociacao-de-fretes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /negociacao-de-fretes : Updates an existing negociacaoDeFrete.
     *
     * @param negociacaoDeFrete the negociacaoDeFrete to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated negociacaoDeFrete,
     * or with status 400 (Bad Request) if the negociacaoDeFrete is not valid,
     * or with status 500 (Internal Server Error) if the negociacaoDeFrete couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/negociacao-de-fretes")
    public ResponseEntity<NegociacaoDeFrete> updateNegociacaoDeFrete(@Valid @RequestBody NegociacaoDeFrete negociacaoDeFrete) throws URISyntaxException {
        log.debug("REST request to update NegociacaoDeFrete : {}", negociacaoDeFrete);
        if (negociacaoDeFrete.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NegociacaoDeFrete result = negociacaoDeFreteService.save(negociacaoDeFrete);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, negociacaoDeFrete.getId().toString()))
            .body(result);
    }

    /**
     * GET  /negociacao-de-fretes : get all the negociacaoDeFretes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of negociacaoDeFretes in body
     */
    @GetMapping("/negociacao-de-fretes")
    public List<NegociacaoDeFrete> getAllNegociacaoDeFretes() {
        log.debug("REST request to get all NegociacaoDeFretes");
        return negociacaoDeFreteService.findAll();
    }

    /**
     * GET  /negociacao-de-fretes/:id : get the "id" negociacaoDeFrete.
     *
     * @param id the id of the negociacaoDeFrete to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the negociacaoDeFrete, or with status 404 (Not Found)
     */
    @GetMapping("/negociacao-de-fretes/{id}")
    public ResponseEntity<NegociacaoDeFrete> getNegociacaoDeFrete(@PathVariable Long id) {
        log.debug("REST request to get NegociacaoDeFrete : {}", id);
        Optional<NegociacaoDeFrete> negociacaoDeFrete = negociacaoDeFreteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(negociacaoDeFrete);
    }

    /**
     * DELETE  /negociacao-de-fretes/:id : delete the "id" negociacaoDeFrete.
     *
     * @param id the id of the negociacaoDeFrete to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/negociacao-de-fretes/{id}")
    public ResponseEntity<Void> deleteNegociacaoDeFrete(@PathVariable Long id) {
        log.debug("REST request to delete NegociacaoDeFrete : {}", id);
        negociacaoDeFreteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
