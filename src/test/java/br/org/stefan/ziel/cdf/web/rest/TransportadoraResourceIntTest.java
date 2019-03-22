package br.org.stefan.ziel.cdf.web.rest;

import br.org.stefan.ziel.cdf.CdfApp;

import br.org.stefan.ziel.cdf.domain.Transportadora;
import br.org.stefan.ziel.cdf.repository.TransportadoraRepository;
import br.org.stefan.ziel.cdf.service.TransportadoraService;
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
import java.util.List;


import static br.org.stefan.ziel.cdf.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TransportadoraResource REST controller.
 *
 * @see TransportadoraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CdfApp.class)
public class TransportadoraResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private TransportadoraRepository transportadoraRepository;

    @Autowired
    private TransportadoraService transportadoraService;

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

    private MockMvc restTransportadoraMockMvc;

    private Transportadora transportadora;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransportadoraResource transportadoraResource = new TransportadoraResource(transportadoraService);
        this.restTransportadoraMockMvc = MockMvcBuilders.standaloneSetup(transportadoraResource)
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
    public static Transportadora createEntity(EntityManager em) {
        Transportadora transportadora = new Transportadora()
            .nome(DEFAULT_NOME);
        return transportadora;
    }

    @Before
    public void initTest() {
        transportadora = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransportadora() throws Exception {
        int databaseSizeBeforeCreate = transportadoraRepository.findAll().size();

        // Create the Transportadora
        restTransportadoraMockMvc.perform(post("/api/transportadoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportadora)))
            .andExpect(status().isCreated());

        // Validate the Transportadora in the database
        List<Transportadora> transportadoraList = transportadoraRepository.findAll();
        assertThat(transportadoraList).hasSize(databaseSizeBeforeCreate + 1);
        Transportadora testTransportadora = transportadoraList.get(transportadoraList.size() - 1);
        assertThat(testTransportadora.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createTransportadoraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transportadoraRepository.findAll().size();

        // Create the Transportadora with an existing ID
        transportadora.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransportadoraMockMvc.perform(post("/api/transportadoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportadora)))
            .andExpect(status().isBadRequest());

        // Validate the Transportadora in the database
        List<Transportadora> transportadoraList = transportadoraRepository.findAll();
        assertThat(transportadoraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transportadoraRepository.findAll().size();
        // set the field null
        transportadora.setNome(null);

        // Create the Transportadora, which fails.

        restTransportadoraMockMvc.perform(post("/api/transportadoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportadora)))
            .andExpect(status().isBadRequest());

        List<Transportadora> transportadoraList = transportadoraRepository.findAll();
        assertThat(transportadoraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransportadoras() throws Exception {
        // Initialize the database
        transportadoraRepository.saveAndFlush(transportadora);

        // Get all the transportadoraList
        restTransportadoraMockMvc.perform(get("/api/transportadoras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transportadora.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }
    
    @Test
    @Transactional
    public void getTransportadora() throws Exception {
        // Initialize the database
        transportadoraRepository.saveAndFlush(transportadora);

        // Get the transportadora
        restTransportadoraMockMvc.perform(get("/api/transportadoras/{id}", transportadora.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transportadora.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransportadora() throws Exception {
        // Get the transportadora
        restTransportadoraMockMvc.perform(get("/api/transportadoras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransportadora() throws Exception {
        // Initialize the database
        transportadoraService.save(transportadora);

        int databaseSizeBeforeUpdate = transportadoraRepository.findAll().size();

        // Update the transportadora
        Transportadora updatedTransportadora = transportadoraRepository.findById(transportadora.getId()).get();
        // Disconnect from session so that the updates on updatedTransportadora are not directly saved in db
        em.detach(updatedTransportadora);
        updatedTransportadora
            .nome(UPDATED_NOME);

        restTransportadoraMockMvc.perform(put("/api/transportadoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransportadora)))
            .andExpect(status().isOk());

        // Validate the Transportadora in the database
        List<Transportadora> transportadoraList = transportadoraRepository.findAll();
        assertThat(transportadoraList).hasSize(databaseSizeBeforeUpdate);
        Transportadora testTransportadora = transportadoraList.get(transportadoraList.size() - 1);
        assertThat(testTransportadora.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingTransportadora() throws Exception {
        int databaseSizeBeforeUpdate = transportadoraRepository.findAll().size();

        // Create the Transportadora

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransportadoraMockMvc.perform(put("/api/transportadoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportadora)))
            .andExpect(status().isBadRequest());

        // Validate the Transportadora in the database
        List<Transportadora> transportadoraList = transportadoraRepository.findAll();
        assertThat(transportadoraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransportadora() throws Exception {
        // Initialize the database
        transportadoraService.save(transportadora);

        int databaseSizeBeforeDelete = transportadoraRepository.findAll().size();

        // Delete the transportadora
        restTransportadoraMockMvc.perform(delete("/api/transportadoras/{id}", transportadora.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Transportadora> transportadoraList = transportadoraRepository.findAll();
        assertThat(transportadoraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transportadora.class);
        Transportadora transportadora1 = new Transportadora();
        transportadora1.setId(1L);
        Transportadora transportadora2 = new Transportadora();
        transportadora2.setId(transportadora1.getId());
        assertThat(transportadora1).isEqualTo(transportadora2);
        transportadora2.setId(2L);
        assertThat(transportadora1).isNotEqualTo(transportadora2);
        transportadora1.setId(null);
        assertThat(transportadora1).isNotEqualTo(transportadora2);
    }
}
