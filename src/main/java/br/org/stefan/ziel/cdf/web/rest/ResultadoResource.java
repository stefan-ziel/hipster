package br.org.stefan.ziel.cdf.web.rest;
import br.org.stefan.ziel.cdf.domain.Resultado;
import br.org.stefan.ziel.cdf.service.ResultadoService;
import br.org.stefan.ziel.cdf.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Resultado.
 */
@RestController
@RequestMapping("/api")
public class ResultadoResource {

    private final Logger log = LoggerFactory.getLogger(ResultadoResource.class);

    private static final String ENTITY_NAME = "resultado";

    private final ResultadoService resultadoService;

    public ResultadoResource(ResultadoService resultadoService) {
        this.resultadoService = resultadoService;
    }

    /**
     * POST  /resultados : cria um novo resultado a partir de um embarque
     *
     * @param resultado the resultado to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resultado
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping("/resultados-do-embarque/{id}")
    public ResponseEntity<Resultado> createResultado(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to create Resultado from Embarque : {}", id);
        Optional<Resultado> resultado = resultadoService.calcularFrete(id);
        return ResponseUtil.wrapOrNotFound(resultado);
    }

    /**
     * POST  /resultados-do-dia : processar todos os resutados do dia.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of resultados in body
     */
    @GetMapping("/resultados-do-dia/{dataDeColeta}")
    public List<Resultado> getResultadosDoDia(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataDeColeta) {
        log.debug("REST request to criar todos resultados do dia");
        return resultadoService.calcularFretesDoDia(dataDeColeta.toInstant());
    }
    
    /**
     * GET  /resultados : get all the resultados.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of resultados in body
     */
    @GetMapping("/resultados")
    public List<Resultado> getAllResultados() {
        log.debug("REST request to get all Resultados");
        return resultadoService.findAll();
    }

    /**
     * GET  /resultados/:id : get the "id" resultado.
     *
     * @param id the id of the resultado to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resultado, or with status 404 (Not Found)
     */
    @GetMapping("/resultados/{id}")
    public ResponseEntity<Resultado> getResultado(@PathVariable Long id) {
        log.debug("REST request to get Resultado : {}", id);
        Optional<Resultado> resultado = resultadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resultado);
    }

    /**
     * DELETE  /resultados/:id : delete the "id" resultado.
     *
     * @param id the id of the resultado to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resultados/{id}")
    public ResponseEntity<Void> deleteResultado(@PathVariable Long id) {
        log.debug("REST request to delete Resultado : {}", id);
        resultadoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
