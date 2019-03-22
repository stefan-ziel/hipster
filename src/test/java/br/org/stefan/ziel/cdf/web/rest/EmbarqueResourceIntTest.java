package br.org.stefan.ziel.cdf.web.rest;

import br.org.stefan.ziel.cdf.CdfApp;

import br.org.stefan.ziel.cdf.domain.Embarque;
import br.org.stefan.ziel.cdf.repository.EmbarqueRepository;
import br.org.stefan.ziel.cdf.service.EmbarqueService;
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

import br.org.stefan.ziel.cdf.domain.enumeration.CategoriaDeVeiculo;
/**
 * Test class for the EmbarqueResource REST controller.
 *
 * @see EmbarqueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CdfApp.class)
public class EmbarqueResourceIntTest {

    private static final CategoriaDeVeiculo DEFAULT_CATEGORIA_DE_VEICULO = CategoriaDeVeiculo.VUC;
    private static final CategoriaDeVeiculo UPDATED_CATEGORIA_DE_VEICULO = CategoriaDeVeiculo.TOCO;

    private static final String DEFAULT_ORIGEM = "AAAAAAAAAA";
    private static final String UPDATED_ORIGEM = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINO = "AAAAAAAAAA";
    private static final String UPDATED_DESTINO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_QILOMETRAGEM = new BigDecimal(1);
    private static final BigDecimal UPDATED_QILOMETRAGEM = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PESO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PESO = new BigDecimal(2);

    private static final Instant DEFAULT_DATA_DE_COLETA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_DE_COLETA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EmbarqueRepository embarqueRepository;

    @Autowired
    private EmbarqueService embarqueService;

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

    private MockMvc restEmbarqueMockMvc;

    private Embarque embarque;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmbarqueResource embarqueResource = new EmbarqueResource(embarqueService);
        this.restEmbarqueMockMvc = MockMvcBuilders.standaloneSetup(embarqueResource)
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
    public static Embarque createEntity(EntityManager em) {
        Embarque embarque = new Embarque()
            .categoriaDeVeiculo(DEFAULT_CATEGORIA_DE_VEICULO)
            .origem(DEFAULT_ORIGEM)
            .destino(DEFAULT_DESTINO)
            .qilometragem(DEFAULT_QILOMETRAGEM)
            .peso(DEFAULT_PESO)
            .dataDeColeta(DEFAULT_DATA_DE_COLETA);
        return embarque;
    }

    @Before
    public void initTest() {
        embarque = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmbarque() throws Exception {
        int databaseSizeBeforeCreate = embarqueRepository.findAll().size();

        // Create the Embarque
        restEmbarqueMockMvc.perform(post("/api/embarques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(embarque)))
            .andExpect(status().isCreated());

        // Validate the Embarque in the database
        List<Embarque> embarqueList = embarqueRepository.findAll();
        assertThat(embarqueList).hasSize(databaseSizeBeforeCreate + 1);
        Embarque testEmbarque = embarqueList.get(embarqueList.size() - 1);
        assertThat(testEmbarque.getCategoriaDeVeiculo()).isEqualTo(DEFAULT_CATEGORIA_DE_VEICULO);
        assertThat(testEmbarque.getOrigem()).isEqualTo(DEFAULT_ORIGEM);
        assertThat(testEmbarque.getDestino()).isEqualTo(DEFAULT_DESTINO);
        assertThat(testEmbarque.getQilometragem()).isEqualTo(DEFAULT_QILOMETRAGEM);
        assertThat(testEmbarque.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testEmbarque.getDataDeColeta()).isEqualTo(DEFAULT_DATA_DE_COLETA);
    }

    @Test
    @Transactional
    public void createEmbarqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = embarqueRepository.findAll().size();

        // Create the Embarque with an existing ID
        embarque.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmbarqueMockMvc.perform(post("/api/embarques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(embarque)))
            .andExpect(status().isBadRequest());

        // Validate the Embarque in the database
        List<Embarque> embarqueList = embarqueRepository.findAll();
        assertThat(embarqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQilometragemIsRequired() throws Exception {
        int databaseSizeBeforeTest = embarqueRepository.findAll().size();
        // set the field null
        embarque.setQilometragem(null);

        // Create the Embarque, which fails.

        restEmbarqueMockMvc.perform(post("/api/embarques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(embarque)))
            .andExpect(status().isBadRequest());

        List<Embarque> embarqueList = embarqueRepository.findAll();
        assertThat(embarqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPesoIsRequired() throws Exception {
        int databaseSizeBeforeTest = embarqueRepository.findAll().size();
        // set the field null
        embarque.setPeso(null);

        // Create the Embarque, which fails.

        restEmbarqueMockMvc.perform(post("/api/embarques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(embarque)))
            .andExpect(status().isBadRequest());

        List<Embarque> embarqueList = embarqueRepository.findAll();
        assertThat(embarqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataDeColetaIsRequired() throws Exception {
        int databaseSizeBeforeTest = embarqueRepository.findAll().size();
        // set the field null
        embarque.setDataDeColeta(null);

        // Create the Embarque, which fails.

        restEmbarqueMockMvc.perform(post("/api/embarques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(embarque)))
            .andExpect(status().isBadRequest());

        List<Embarque> embarqueList = embarqueRepository.findAll();
        assertThat(embarqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmbarques() throws Exception {
        // Initialize the database
        embarqueRepository.saveAndFlush(embarque);

        // Get all the embarqueList
        restEmbarqueMockMvc.perform(get("/api/embarques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(embarque.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoriaDeVeiculo").value(hasItem(DEFAULT_CATEGORIA_DE_VEICULO.toString())))
            .andExpect(jsonPath("$.[*].origem").value(hasItem(DEFAULT_ORIGEM.toString())))
            .andExpect(jsonPath("$.[*].destino").value(hasItem(DEFAULT_DESTINO.toString())))
            .andExpect(jsonPath("$.[*].qilometragem").value(hasItem(DEFAULT_QILOMETRAGEM.intValue())))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.intValue())))
            .andExpect(jsonPath("$.[*].dataDeColeta").value(hasItem(DEFAULT_DATA_DE_COLETA.toString())));
    }
    
    @Test
    @Transactional
    public void getEmbarque() throws Exception {
        // Initialize the database
        embarqueRepository.saveAndFlush(embarque);

        // Get the embarque
        restEmbarqueMockMvc.perform(get("/api/embarques/{id}", embarque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(embarque.getId().intValue()))
            .andExpect(jsonPath("$.categoriaDeVeiculo").value(DEFAULT_CATEGORIA_DE_VEICULO.toString()))
            .andExpect(jsonPath("$.origem").value(DEFAULT_ORIGEM.toString()))
            .andExpect(jsonPath("$.destino").value(DEFAULT_DESTINO.toString()))
            .andExpect(jsonPath("$.qilometragem").value(DEFAULT_QILOMETRAGEM.intValue()))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.intValue()))
            .andExpect(jsonPath("$.dataDeColeta").value(DEFAULT_DATA_DE_COLETA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmbarque() throws Exception {
        // Get the embarque
        restEmbarqueMockMvc.perform(get("/api/embarques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmbarque() throws Exception {
        // Initialize the database
        embarqueService.save(embarque);

        int databaseSizeBeforeUpdate = embarqueRepository.findAll().size();

        // Update the embarque
        Embarque updatedEmbarque = embarqueRepository.findById(embarque.getId()).get();
        // Disconnect from session so that the updates on updatedEmbarque are not directly saved in db
        em.detach(updatedEmbarque);
        updatedEmbarque
            .categoriaDeVeiculo(UPDATED_CATEGORIA_DE_VEICULO)
            .origem(UPDATED_ORIGEM)
            .destino(UPDATED_DESTINO)
            .qilometragem(UPDATED_QILOMETRAGEM)
            .peso(UPDATED_PESO)
            .dataDeColeta(UPDATED_DATA_DE_COLETA);

        restEmbarqueMockMvc.perform(put("/api/embarques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmbarque)))
            .andExpect(status().isOk());

        // Validate the Embarque in the database
        List<Embarque> embarqueList = embarqueRepository.findAll();
        assertThat(embarqueList).hasSize(databaseSizeBeforeUpdate);
        Embarque testEmbarque = embarqueList.get(embarqueList.size() - 1);
        assertThat(testEmbarque.getCategoriaDeVeiculo()).isEqualTo(UPDATED_CATEGORIA_DE_VEICULO);
        assertThat(testEmbarque.getOrigem()).isEqualTo(UPDATED_ORIGEM);
        assertThat(testEmbarque.getDestino()).isEqualTo(UPDATED_DESTINO);
        assertThat(testEmbarque.getQilometragem()).isEqualTo(UPDATED_QILOMETRAGEM);
        assertThat(testEmbarque.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testEmbarque.getDataDeColeta()).isEqualTo(UPDATED_DATA_DE_COLETA);
    }

    @Test
    @Transactional
    public void updateNonExistingEmbarque() throws Exception {
        int databaseSizeBeforeUpdate = embarqueRepository.findAll().size();

        // Create the Embarque

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmbarqueMockMvc.perform(put("/api/embarques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(embarque)))
            .andExpect(status().isBadRequest());

        // Validate the Embarque in the database
        List<Embarque> embarqueList = embarqueRepository.findAll();
        assertThat(embarqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmbarque() throws Exception {
        // Initialize the database
        embarqueService.save(embarque);

        int databaseSizeBeforeDelete = embarqueRepository.findAll().size();

        // Delete the embarque
        restEmbarqueMockMvc.perform(delete("/api/embarques/{id}", embarque.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Embarque> embarqueList = embarqueRepository.findAll();
        assertThat(embarqueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Embarque.class);
        Embarque embarque1 = new Embarque();
        embarque1.setId(1L);
        Embarque embarque2 = new Embarque();
        embarque2.setId(embarque1.getId());
        assertThat(embarque1).isEqualTo(embarque2);
        embarque2.setId(2L);
        assertThat(embarque1).isNotEqualTo(embarque2);
        embarque1.setId(null);
        assertThat(embarque1).isNotEqualTo(embarque2);
    }
}
