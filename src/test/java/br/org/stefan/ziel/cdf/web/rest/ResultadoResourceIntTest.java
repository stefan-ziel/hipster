package br.org.stefan.ziel.cdf.web.rest;

import br.org.stefan.ziel.cdf.CdfApp;

import br.org.stefan.ziel.cdf.domain.Resultado;
import br.org.stefan.ziel.cdf.repository.ResultadoRepository;
import br.org.stefan.ziel.cdf.service.ResultadoService;
import br.org.stefan.ziel.cdf.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static br.org.stefan.ziel.cdf.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ResultadoResource REST controller.
 *
 * @see ResultadoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CdfApp.class)
public class ResultadoResourceIntTest {

    private static final BigDecimal DEFAULT_VALOR_CALCULADO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_CALCULADO = new BigDecimal(2);

    private static final Instant DEFAULT_PREVISAO_DE_ENTREGA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PREVISAO_DE_ENTREGA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ResultadoRepository resultadoRepository;

    @Autowired
    private ResultadoService resultadoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restResultadoMockMvc;

    private Resultado resultado;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResultadoResource resultadoResource = new ResultadoResource(resultadoService);
        this.restResultadoMockMvc = MockMvcBuilders.standaloneSetup(resultadoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resultado createEntity(EntityManager em) {
        Resultado resultado = new Resultado()
            .valorCalculado(DEFAULT_VALOR_CALCULADO)
            .previsaoDeEntrega(DEFAULT_PREVISAO_DE_ENTREGA);
        return resultado;
    }

    @Before
    public void initTest() {
        resultado = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultado() throws Exception {
        int databaseSizeBeforeCreate = resultadoRepository.findAll().size();

        // Create the Resultado
        restResultadoMockMvc.perform(post("/api/resultados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultado)))
            .andExpect(status().isCreated());

        // Validate the Resultado in the database
        List<Resultado> resultadoList = resultadoRepository.findAll();
        assertThat(resultadoList).hasSize(databaseSizeBeforeCreate + 1);
        Resultado testResultado = resultadoList.get(resultadoList.size() - 1);
        assertThat(testResultado.getValorCalculado()).isEqualTo(DEFAULT_VALOR_CALCULADO);
        assertThat(testResultado.getPrevisaoDeEntrega()).isEqualTo(DEFAULT_PREVISAO_DE_ENTREGA);
    }

    @Test
    @Transactional
    public void createResultadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultadoRepository.findAll().size();

        // Create the Resultado with an existing ID
        resultado.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultadoMockMvc.perform(post("/api/resultados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultado)))
            .andExpect(status().isBadRequest());

        // Validate the Resultado in the database
        List<Resultado> resultadoList = resultadoRepository.findAll();
        assertThat(resultadoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValorCalculadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultadoRepository.findAll().size();
        // set the field null
        resultado.setValorCalculado(null);

        // Create the Resultado, which fails.

        restResultadoMockMvc.perform(post("/api/resultados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultado)))
            .andExpect(status().isBadRequest());

        List<Resultado> resultadoList = resultadoRepository.findAll();
        assertThat(resultadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrevisaoDeEntregaIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultadoRepository.findAll().size();
        // set the field null
        resultado.setPrevisaoDeEntrega(null);

        // Create the Resultado, which fails.

        restResultadoMockMvc.perform(post("/api/resultados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultado)))
            .andExpect(status().isBadRequest());

        List<Resultado> resultadoList = resultadoRepository.findAll();
        assertThat(resultadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResultados() throws Exception {
        // Initialize the database
        resultadoRepository.saveAndFlush(resultado);

        // Get all the resultadoList
        restResultadoMockMvc.perform(get("/api/resultados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultado.getId().intValue())))
            .andExpect(jsonPath("$.[*].valorCalculado").value(hasItem(DEFAULT_VALOR_CALCULADO.intValue())))
            .andExpect(jsonPath("$.[*].previsaoDeEntrega").value(hasItem(DEFAULT_PREVISAO_DE_ENTREGA.toString())));
    }
    
    @Test
    @Transactional
    public void getResultado() throws Exception {
        // Initialize the database
        resultadoRepository.saveAndFlush(resultado);

        // Get the resultado
        restResultadoMockMvc.perform(get("/api/resultados/{id}", resultado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resultado.getId().intValue()))
            .andExpect(jsonPath("$.valorCalculado").value(DEFAULT_VALOR_CALCULADO.intValue()))
            .andExpect(jsonPath("$.previsaoDeEntrega").value(DEFAULT_PREVISAO_DE_ENTREGA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResultado() throws Exception {
        // Get the resultado
        restResultadoMockMvc.perform(get("/api/resultados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultado() throws Exception {
        // Initialize the database
        resultadoService.save(resultado);

        int databaseSizeBeforeUpdate = resultadoRepository.findAll().size();

        // Update the resultado
        Resultado updatedResultado = resultadoRepository.findById(resultado.getId()).get();
        // Disconnect from session so that the updates on updatedResultado are not directly saved in db
        em.detach(updatedResultado);
        updatedResultado
            .valorCalculado(UPDATED_VALOR_CALCULADO)
            .previsaoDeEntrega(UPDATED_PREVISAO_DE_ENTREGA);

        restResultadoMockMvc.perform(put("/api/resultados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResultado)))
            .andExpect(status().isOk());

        // Validate the Resultado in the database
        List<Resultado> resultadoList = resultadoRepository.findAll();
        assertThat(resultadoList).hasSize(databaseSizeBeforeUpdate);
        Resultado testResultado = resultadoList.get(resultadoList.size() - 1);
        assertThat(testResultado.getValorCalculado()).isEqualTo(UPDATED_VALOR_CALCULADO);
        assertThat(testResultado.getPrevisaoDeEntrega()).isEqualTo(UPDATED_PREVISAO_DE_ENTREGA);
    }

    @Test
    @Transactional
    public void updateNonExistingResultado() throws Exception {
        int databaseSizeBeforeUpdate = resultadoRepository.findAll().size();

        // Create the Resultado

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultadoMockMvc.perform(put("/api/resultados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultado)))
            .andExpect(status().isBadRequest());

        // Validate the Resultado in the database
        List<Resultado> resultadoList = resultadoRepository.findAll();
        assertThat(resultadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResultado() throws Exception {
        // Initialize the database
        resultadoService.save(resultado);

        int databaseSizeBeforeDelete = resultadoRepository.findAll().size();

        // Delete the resultado
        restResultadoMockMvc.perform(delete("/api/resultados/{id}", resultado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Resultado> resultadoList = resultadoRepository.findAll();
        assertThat(resultadoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resultado.class);
        Resultado resultado1 = new Resultado();
        resultado1.setId(1L);
        Resultado resultado2 = new Resultado();
        resultado2.setId(resultado1.getId());
        assertThat(resultado1).isEqualTo(resultado2);
        resultado2.setId(2L);
        assertThat(resultado1).isNotEqualTo(resultado2);
        resultado1.setId(null);
        assertThat(resultado1).isNotEqualTo(resultado2);
    }
}
