package br.org.stefan.ziel.cdf.web.rest;

import br.org.stefan.ziel.cdf.CdfApp;

import br.org.stefan.ziel.cdf.domain.Embarcadora;
import br.org.stefan.ziel.cdf.repository.EmbarcadoraRepository;
import br.org.stefan.ziel.cdf.service.EmbarcadoraService;
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
 * Test class for the EmbarcadoraResource REST controller.
 *
 * @see EmbarcadoraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CdfApp.class)
public class EmbarcadoraResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private EmbarcadoraRepository embarcadoraRepository;

    @Autowired
    private EmbarcadoraService embarcadoraService;

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

    private MockMvc restEmbarcadoraMockMvc;

    private Embarcadora embarcadora;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmbarcadoraResource embarcadoraResource = new EmbarcadoraResource(embarcadoraService);
        this.restEmbarcadoraMockMvc = MockMvcBuilders.standaloneSetup(embarcadoraResource)
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
    public static Embarcadora createEntity(EntityManager em) {
        Embarcadora embarcadora = new Embarcadora()
            .nome(DEFAULT_NOME);
        return embarcadora;
    }

    @Before
    public void initTest() {
        embarcadora = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmbarcadora() throws Exception {
        int databaseSizeBeforeCreate = embarcadoraRepository.findAll().size();

        // Create the Embarcadora
        restEmbarcadoraMockMvc.perform(post("/api/embarcadoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(embarcadora)))
            .andExpect(status().isCreated());

        // Validate the Embarcadora in the database
        List<Embarcadora> embarcadoraList = embarcadoraRepository.findAll();
        assertThat(embarcadoraList).hasSize(databaseSizeBeforeCreate + 1);
        Embarcadora testEmbarcadora = embarcadoraList.get(embarcadoraList.size() - 1);
        assertThat(testEmbarcadora.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createEmbarcadoraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = embarcadoraRepository.findAll().size();

        // Create the Embarcadora with an existing ID
        embarcadora.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmbarcadoraMockMvc.perform(post("/api/embarcadoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(embarcadora)))
            .andExpect(status().isBadRequest());

        // Validate the Embarcadora in the database
        List<Embarcadora> embarcadoraList = embarcadoraRepository.findAll();
        assertThat(embarcadoraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = embarcadoraRepository.findAll().size();
        // set the field null
        embarcadora.setNome(null);

        // Create the Embarcadora, which fails.

        restEmbarcadoraMockMvc.perform(post("/api/embarcadoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(embarcadora)))
            .andExpect(status().isBadRequest());

        List<Embarcadora> embarcadoraList = embarcadoraRepository.findAll();
        assertThat(embarcadoraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmbarcadoras() throws Exception {
        // Initialize the database
        embarcadoraRepository.saveAndFlush(embarcadora);

        // Get all the embarcadoraList
        restEmbarcadoraMockMvc.perform(get("/api/embarcadoras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(embarcadora.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }
    
    @Test
    @Transactional
    public void getEmbarcadora() throws Exception {
        // Initialize the database
        embarcadoraRepository.saveAndFlush(embarcadora);

        // Get the embarcadora
        restEmbarcadoraMockMvc.perform(get("/api/embarcadoras/{id}", embarcadora.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(embarcadora.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmbarcadora() throws Exception {
        // Get the embarcadora
        restEmbarcadoraMockMvc.perform(get("/api/embarcadoras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmbarcadora() throws Exception {
        // Initialize the database
        embarcadoraService.save(embarcadora);

        int databaseSizeBeforeUpdate = embarcadoraRepository.findAll().size();

        // Update the embarcadora
        Embarcadora updatedEmbarcadora = embarcadoraRepository.findById(embarcadora.getId()).get();
        // Disconnect from session so that the updates on updatedEmbarcadora are not directly saved in db
        em.detach(updatedEmbarcadora);
        updatedEmbarcadora
            .nome(UPDATED_NOME);

        restEmbarcadoraMockMvc.perform(put("/api/embarcadoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmbarcadora)))
            .andExpect(status().isOk());

        // Validate the Embarcadora in the database
        List<Embarcadora> embarcadoraList = embarcadoraRepository.findAll();
        assertThat(embarcadoraList).hasSize(databaseSizeBeforeUpdate);
        Embarcadora testEmbarcadora = embarcadoraList.get(embarcadoraList.size() - 1);
        assertThat(testEmbarcadora.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingEmbarcadora() throws Exception {
        int databaseSizeBeforeUpdate = embarcadoraRepository.findAll().size();

        // Create the Embarcadora

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmbarcadoraMockMvc.perform(put("/api/embarcadoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(embarcadora)))
            .andExpect(status().isBadRequest());

        // Validate the Embarcadora in the database
        List<Embarcadora> embarcadoraList = embarcadoraRepository.findAll();
        assertThat(embarcadoraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmbarcadora() throws Exception {
        // Initialize the database
        embarcadoraService.save(embarcadora);

        int databaseSizeBeforeDelete = embarcadoraRepository.findAll().size();

        // Delete the embarcadora
        restEmbarcadoraMockMvc.perform(delete("/api/embarcadoras/{id}", embarcadora.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Embarcadora> embarcadoraList = embarcadoraRepository.findAll();
        assertThat(embarcadoraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Embarcadora.class);
        Embarcadora embarcadora1 = new Embarcadora();
        embarcadora1.setId(1L);
        Embarcadora embarcadora2 = new Embarcadora();
        embarcadora2.setId(embarcadora1.getId());
        assertThat(embarcadora1).isEqualTo(embarcadora2);
        embarcadora2.setId(2L);
        assertThat(embarcadora1).isNotEqualTo(embarcadora2);
        embarcadora1.setId(null);
        assertThat(embarcadora1).isNotEqualTo(embarcadora2);
    }
}
