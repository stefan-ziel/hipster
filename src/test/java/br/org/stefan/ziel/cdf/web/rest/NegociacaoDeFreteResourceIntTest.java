package br.org.stefan.ziel.cdf.web.rest;

import br.org.stefan.ziel.cdf.CdfApp;

import br.org.stefan.ziel.cdf.domain.NegociacaoDeFrete;
import br.org.stefan.ziel.cdf.repository.NegociacaoDeFreteRepository;
import br.org.stefan.ziel.cdf.service.NegociacaoDeFreteService;
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
import java.util.List;


import static br.org.stefan.ziel.cdf.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.org.stefan.ziel.cdf.domain.enumeration.CategoriaDeVeiculo;
/**
 * Test class for the NegociacaoDeFreteResource REST controller.
 *
 * @see NegociacaoDeFreteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CdfApp.class)
public class NegociacaoDeFreteResourceIntTest {

    private static final CategoriaDeVeiculo DEFAULT_CATEGORIA_DE_VEICULO = CategoriaDeVeiculo.VUC;
    private static final CategoriaDeVeiculo UPDATED_CATEGORIA_DE_VEICULO = CategoriaDeVeiculo.TOCO;

    private static final BigDecimal DEFAULT_PESO_DE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PESO_DE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PESO_ATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PESO_ATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRECO_POR_QUILOMETRO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECO_POR_QUILOMETRO = new BigDecimal(2);

    private static final Integer DEFAULT_PRAZO_DE_ENTREGA = 1;
    private static final Integer UPDATED_PRAZO_DE_ENTREGA = 2;

    @Autowired
    private NegociacaoDeFreteRepository negociacaoDeFreteRepository;

    @Autowired
    private NegociacaoDeFreteService negociacaoDeFreteService;

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

    private MockMvc restNegociacaoDeFreteMockMvc;

    private NegociacaoDeFrete negociacaoDeFrete;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NegociacaoDeFreteResource negociacaoDeFreteResource = new NegociacaoDeFreteResource(negociacaoDeFreteService);
        this.restNegociacaoDeFreteMockMvc = MockMvcBuilders.standaloneSetup(negociacaoDeFreteResource)
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
    public static NegociacaoDeFrete createEntity(EntityManager em) {
        NegociacaoDeFrete negociacaoDeFrete = new NegociacaoDeFrete()
            .categoriaDeVeiculo(DEFAULT_CATEGORIA_DE_VEICULO)
            .pesoDe(DEFAULT_PESO_DE)
            .pesoAte(DEFAULT_PESO_ATE)
            .precoPorQuilometro(DEFAULT_PRECO_POR_QUILOMETRO)
            .prazoDeEntrega(DEFAULT_PRAZO_DE_ENTREGA);
        return negociacaoDeFrete;
    }

    @Before
    public void initTest() {
        negociacaoDeFrete = createEntity(em);
    }

    @Test
    @Transactional
    public void createNegociacaoDeFrete() throws Exception {
        int databaseSizeBeforeCreate = negociacaoDeFreteRepository.findAll().size();

        // Create the NegociacaoDeFrete
        restNegociacaoDeFreteMockMvc.perform(post("/api/negociacao-de-fretes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(negociacaoDeFrete)))
            .andExpect(status().isCreated());

        // Validate the NegociacaoDeFrete in the database
        List<NegociacaoDeFrete> negociacaoDeFreteList = negociacaoDeFreteRepository.findAll();
        assertThat(negociacaoDeFreteList).hasSize(databaseSizeBeforeCreate + 1);
        NegociacaoDeFrete testNegociacaoDeFrete = negociacaoDeFreteList.get(negociacaoDeFreteList.size() - 1);
        assertThat(testNegociacaoDeFrete.getCategoriaDeVeiculo()).isEqualTo(DEFAULT_CATEGORIA_DE_VEICULO);
        assertThat(testNegociacaoDeFrete.getPesoDe()).isEqualTo(DEFAULT_PESO_DE);
        assertThat(testNegociacaoDeFrete.getPesoAte()).isEqualTo(DEFAULT_PESO_ATE);
        assertThat(testNegociacaoDeFrete.getPrecoPorQuilometro()).isEqualTo(DEFAULT_PRECO_POR_QUILOMETRO);
        assertThat(testNegociacaoDeFrete.getPrazoDeEntrega()).isEqualTo(DEFAULT_PRAZO_DE_ENTREGA);
    }

    @Test
    @Transactional
    public void createNegociacaoDeFreteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = negociacaoDeFreteRepository.findAll().size();

        // Create the NegociacaoDeFrete with an existing ID
        negociacaoDeFrete.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNegociacaoDeFreteMockMvc.perform(post("/api/negociacao-de-fretes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(negociacaoDeFrete)))
            .andExpect(status().isBadRequest());

        // Validate the NegociacaoDeFrete in the database
        List<NegociacaoDeFrete> negociacaoDeFreteList = negociacaoDeFreteRepository.findAll();
        assertThat(negociacaoDeFreteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPesoDeIsRequired() throws Exception {
        int databaseSizeBeforeTest = negociacaoDeFreteRepository.findAll().size();
        // set the field null
        negociacaoDeFrete.setPesoDe(null);

        // Create the NegociacaoDeFrete, which fails.

        restNegociacaoDeFreteMockMvc.perform(post("/api/negociacao-de-fretes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(negociacaoDeFrete)))
            .andExpect(status().isBadRequest());

        List<NegociacaoDeFrete> negociacaoDeFreteList = negociacaoDeFreteRepository.findAll();
        assertThat(negociacaoDeFreteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPesoAteIsRequired() throws Exception {
        int databaseSizeBeforeTest = negociacaoDeFreteRepository.findAll().size();
        // set the field null
        negociacaoDeFrete.setPesoAte(null);

        // Create the NegociacaoDeFrete, which fails.

        restNegociacaoDeFreteMockMvc.perform(post("/api/negociacao-de-fretes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(negociacaoDeFrete)))
            .andExpect(status().isBadRequest());

        List<NegociacaoDeFrete> negociacaoDeFreteList = negociacaoDeFreteRepository.findAll();
        assertThat(negociacaoDeFreteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecoPorQuilometroIsRequired() throws Exception {
        int databaseSizeBeforeTest = negociacaoDeFreteRepository.findAll().size();
        // set the field null
        negociacaoDeFrete.setPrecoPorQuilometro(null);

        // Create the NegociacaoDeFrete, which fails.

        restNegociacaoDeFreteMockMvc.perform(post("/api/negociacao-de-fretes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(negociacaoDeFrete)))
            .andExpect(status().isBadRequest());

        List<NegociacaoDeFrete> negociacaoDeFreteList = negociacaoDeFreteRepository.findAll();
        assertThat(negociacaoDeFreteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrazoDeEntregaIsRequired() throws Exception {
        int databaseSizeBeforeTest = negociacaoDeFreteRepository.findAll().size();
        // set the field null
        negociacaoDeFrete.setPrazoDeEntrega(null);

        // Create the NegociacaoDeFrete, which fails.

        restNegociacaoDeFreteMockMvc.perform(post("/api/negociacao-de-fretes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(negociacaoDeFrete)))
            .andExpect(status().isBadRequest());

        List<NegociacaoDeFrete> negociacaoDeFreteList = negociacaoDeFreteRepository.findAll();
        assertThat(negociacaoDeFreteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNegociacaoDeFretes() throws Exception {
        // Initialize the database
        negociacaoDeFreteRepository.saveAndFlush(negociacaoDeFrete);

        // Get all the negociacaoDeFreteList
        restNegociacaoDeFreteMockMvc.perform(get("/api/negociacao-de-fretes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(negociacaoDeFrete.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoriaDeVeiculo").value(hasItem(DEFAULT_CATEGORIA_DE_VEICULO.toString())))
            .andExpect(jsonPath("$.[*].pesoDe").value(hasItem(DEFAULT_PESO_DE.intValue())))
            .andExpect(jsonPath("$.[*].pesoAte").value(hasItem(DEFAULT_PESO_ATE.intValue())))
            .andExpect(jsonPath("$.[*].precoPorQuilometro").value(hasItem(DEFAULT_PRECO_POR_QUILOMETRO.intValue())))
            .andExpect(jsonPath("$.[*].prazoDeEntrega").value(hasItem(DEFAULT_PRAZO_DE_ENTREGA)));
    }
    
    @Test
    @Transactional
    public void getNegociacaoDeFrete() throws Exception {
        // Initialize the database
        negociacaoDeFreteRepository.saveAndFlush(negociacaoDeFrete);

        // Get the negociacaoDeFrete
        restNegociacaoDeFreteMockMvc.perform(get("/api/negociacao-de-fretes/{id}", negociacaoDeFrete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(negociacaoDeFrete.getId().intValue()))
            .andExpect(jsonPath("$.categoriaDeVeiculo").value(DEFAULT_CATEGORIA_DE_VEICULO.toString()))
            .andExpect(jsonPath("$.pesoDe").value(DEFAULT_PESO_DE.intValue()))
            .andExpect(jsonPath("$.pesoAte").value(DEFAULT_PESO_ATE.intValue()))
            .andExpect(jsonPath("$.precoPorQuilometro").value(DEFAULT_PRECO_POR_QUILOMETRO.intValue()))
            .andExpect(jsonPath("$.prazoDeEntrega").value(DEFAULT_PRAZO_DE_ENTREGA));
    }

    @Test
    @Transactional
    public void getNonExistingNegociacaoDeFrete() throws Exception {
        // Get the negociacaoDeFrete
        restNegociacaoDeFreteMockMvc.perform(get("/api/negociacao-de-fretes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNegociacaoDeFrete() throws Exception {
        // Initialize the database
        negociacaoDeFreteService.save(negociacaoDeFrete);

        int databaseSizeBeforeUpdate = negociacaoDeFreteRepository.findAll().size();

        // Update the negociacaoDeFrete
        NegociacaoDeFrete updatedNegociacaoDeFrete = negociacaoDeFreteRepository.findById(negociacaoDeFrete.getId()).get();
        // Disconnect from session so that the updates on updatedNegociacaoDeFrete are not directly saved in db
        em.detach(updatedNegociacaoDeFrete);
        updatedNegociacaoDeFrete
            .categoriaDeVeiculo(UPDATED_CATEGORIA_DE_VEICULO)
            .pesoDe(UPDATED_PESO_DE)
            .pesoAte(UPDATED_PESO_ATE)
            .precoPorQuilometro(UPDATED_PRECO_POR_QUILOMETRO)
            .prazoDeEntrega(UPDATED_PRAZO_DE_ENTREGA);

        restNegociacaoDeFreteMockMvc.perform(put("/api/negociacao-de-fretes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNegociacaoDeFrete)))
            .andExpect(status().isOk());

        // Validate the NegociacaoDeFrete in the database
        List<NegociacaoDeFrete> negociacaoDeFreteList = negociacaoDeFreteRepository.findAll();
        assertThat(negociacaoDeFreteList).hasSize(databaseSizeBeforeUpdate);
        NegociacaoDeFrete testNegociacaoDeFrete = negociacaoDeFreteList.get(negociacaoDeFreteList.size() - 1);
        assertThat(testNegociacaoDeFrete.getCategoriaDeVeiculo()).isEqualTo(UPDATED_CATEGORIA_DE_VEICULO);
        assertThat(testNegociacaoDeFrete.getPesoDe()).isEqualTo(UPDATED_PESO_DE);
        assertThat(testNegociacaoDeFrete.getPesoAte()).isEqualTo(UPDATED_PESO_ATE);
        assertThat(testNegociacaoDeFrete.getPrecoPorQuilometro()).isEqualTo(UPDATED_PRECO_POR_QUILOMETRO);
        assertThat(testNegociacaoDeFrete.getPrazoDeEntrega()).isEqualTo(UPDATED_PRAZO_DE_ENTREGA);
    }

    @Test
    @Transactional
    public void updateNonExistingNegociacaoDeFrete() throws Exception {
        int databaseSizeBeforeUpdate = negociacaoDeFreteRepository.findAll().size();

        // Create the NegociacaoDeFrete

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNegociacaoDeFreteMockMvc.perform(put("/api/negociacao-de-fretes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(negociacaoDeFrete)))
            .andExpect(status().isBadRequest());

        // Validate the NegociacaoDeFrete in the database
        List<NegociacaoDeFrete> negociacaoDeFreteList = negociacaoDeFreteRepository.findAll();
        assertThat(negociacaoDeFreteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNegociacaoDeFrete() throws Exception {
        // Initialize the database
        negociacaoDeFreteService.save(negociacaoDeFrete);

        int databaseSizeBeforeDelete = negociacaoDeFreteRepository.findAll().size();

        // Delete the negociacaoDeFrete
        restNegociacaoDeFreteMockMvc.perform(delete("/api/negociacao-de-fretes/{id}", negociacaoDeFrete.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NegociacaoDeFrete> negociacaoDeFreteList = negociacaoDeFreteRepository.findAll();
        assertThat(negociacaoDeFreteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NegociacaoDeFrete.class);
        NegociacaoDeFrete negociacaoDeFrete1 = new NegociacaoDeFrete();
        negociacaoDeFrete1.setId(1L);
        NegociacaoDeFrete negociacaoDeFrete2 = new NegociacaoDeFrete();
        negociacaoDeFrete2.setId(negociacaoDeFrete1.getId());
        assertThat(negociacaoDeFrete1).isEqualTo(negociacaoDeFrete2);
        negociacaoDeFrete2.setId(2L);
        assertThat(negociacaoDeFrete1).isNotEqualTo(negociacaoDeFrete2);
        negociacaoDeFrete1.setId(null);
        assertThat(negociacaoDeFrete1).isNotEqualTo(negociacaoDeFrete2);
    }
}
