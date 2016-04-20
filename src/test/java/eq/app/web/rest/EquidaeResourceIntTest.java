package eq.app.web.rest;

import eq.app.Application;
import eq.app.domain.Equidae;
import eq.app.repository.EquidaeRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the EquidaeResource REST controller.
 *
 * @see EquidaeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EquidaeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_REQUIRED_LVL_CSO = 1;
    private static final Integer UPDATED_REQUIRED_LVL_CSO = 2;

    private static final Integer DEFAULT_REQUIRED_LVL_DRESSAGE = 1;
    private static final Integer UPDATED_REQUIRED_LVL_DRESSAGE = 2;

    private static final Integer DEFAULT_REQUIRED_LVL_RIDING = 1;
    private static final Integer UPDATED_REQUIRED_LVL_RIDING = 2;

    private static final LocalDate DEFAULT_UNAVAILABILITIES = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UNAVAILABILITIES = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private EquidaeRepository equidaeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEquidaeMockMvc;

    private Equidae equidae;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EquidaeResource equidaeResource = new EquidaeResource();
        ReflectionTestUtils.setField(equidaeResource, "equidaeRepository", equidaeRepository);
        this.restEquidaeMockMvc = MockMvcBuilders.standaloneSetup(equidaeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        equidae = new Equidae();
        equidae.setName(DEFAULT_NAME);
        equidae.setRequiredLvlCSO(DEFAULT_REQUIRED_LVL_CSO);
        equidae.setRequiredLvlDressage(DEFAULT_REQUIRED_LVL_DRESSAGE);
        equidae.setRequiredLvlRiding(DEFAULT_REQUIRED_LVL_RIDING);
        equidae.setUnavailabilities(DEFAULT_UNAVAILABILITIES);
    }

    @Test
    @Transactional
    public void createEquidae() throws Exception {
        int databaseSizeBeforeCreate = equidaeRepository.findAll().size();

        // Create the Equidae

        restEquidaeMockMvc.perform(post("/api/equidaes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(equidae)))
                .andExpect(status().isCreated());

        // Validate the Equidae in the database
        List<Equidae> equidaes = equidaeRepository.findAll();
        assertThat(equidaes).hasSize(databaseSizeBeforeCreate + 1);
        Equidae testEquidae = equidaes.get(equidaes.size() - 1);
        assertThat(testEquidae.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEquidae.getRequiredLvlCSO()).isEqualTo(DEFAULT_REQUIRED_LVL_CSO);
        assertThat(testEquidae.getRequiredLvlDressage()).isEqualTo(DEFAULT_REQUIRED_LVL_DRESSAGE);
        assertThat(testEquidae.getRequiredLvlRiding()).isEqualTo(DEFAULT_REQUIRED_LVL_RIDING);
        assertThat(testEquidae.getUnavailabilities()).isEqualTo(DEFAULT_UNAVAILABILITIES);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = equidaeRepository.findAll().size();
        // set the field null
        equidae.setName(null);

        // Create the Equidae, which fails.

        restEquidaeMockMvc.perform(post("/api/equidaes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(equidae)))
                .andExpect(status().isBadRequest());

        List<Equidae> equidaes = equidaeRepository.findAll();
        assertThat(equidaes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEquidaes() throws Exception {
        // Initialize the database
        equidaeRepository.saveAndFlush(equidae);

        // Get all the equidaes
        restEquidaeMockMvc.perform(get("/api/equidaes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(equidae.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].requiredLvlCSO").value(hasItem(DEFAULT_REQUIRED_LVL_CSO)))
                .andExpect(jsonPath("$.[*].requiredLvlDressage").value(hasItem(DEFAULT_REQUIRED_LVL_DRESSAGE)))
                .andExpect(jsonPath("$.[*].requiredLvlRiding").value(hasItem(DEFAULT_REQUIRED_LVL_RIDING)))
                .andExpect(jsonPath("$.[*].unavailabilities").value(hasItem(DEFAULT_UNAVAILABILITIES.toString())));
    }

    @Test
    @Transactional
    public void getEquidae() throws Exception {
        // Initialize the database
        equidaeRepository.saveAndFlush(equidae);

        // Get the equidae
        restEquidaeMockMvc.perform(get("/api/equidaes/{id}", equidae.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(equidae.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.requiredLvlCSO").value(DEFAULT_REQUIRED_LVL_CSO))
            .andExpect(jsonPath("$.requiredLvlDressage").value(DEFAULT_REQUIRED_LVL_DRESSAGE))
            .andExpect(jsonPath("$.requiredLvlRiding").value(DEFAULT_REQUIRED_LVL_RIDING))
            .andExpect(jsonPath("$.unavailabilities").value(DEFAULT_UNAVAILABILITIES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEquidae() throws Exception {
        // Get the equidae
        restEquidaeMockMvc.perform(get("/api/equidaes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquidae() throws Exception {
        // Initialize the database
        equidaeRepository.saveAndFlush(equidae);

		int databaseSizeBeforeUpdate = equidaeRepository.findAll().size();

        // Update the equidae
        equidae.setName(UPDATED_NAME);
        equidae.setRequiredLvlCSO(UPDATED_REQUIRED_LVL_CSO);
        equidae.setRequiredLvlDressage(UPDATED_REQUIRED_LVL_DRESSAGE);
        equidae.setRequiredLvlRiding(UPDATED_REQUIRED_LVL_RIDING);
        equidae.setUnavailabilities(UPDATED_UNAVAILABILITIES);

        restEquidaeMockMvc.perform(put("/api/equidaes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(equidae)))
                .andExpect(status().isOk());

        // Validate the Equidae in the database
        List<Equidae> equidaes = equidaeRepository.findAll();
        assertThat(equidaes).hasSize(databaseSizeBeforeUpdate);
        Equidae testEquidae = equidaes.get(equidaes.size() - 1);
        assertThat(testEquidae.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEquidae.getRequiredLvlCSO()).isEqualTo(UPDATED_REQUIRED_LVL_CSO);
        assertThat(testEquidae.getRequiredLvlDressage()).isEqualTo(UPDATED_REQUIRED_LVL_DRESSAGE);
        assertThat(testEquidae.getRequiredLvlRiding()).isEqualTo(UPDATED_REQUIRED_LVL_RIDING);
        assertThat(testEquidae.getUnavailabilities()).isEqualTo(UPDATED_UNAVAILABILITIES);
    }

    @Test
    @Transactional
    public void deleteEquidae() throws Exception {
        // Initialize the database
        equidaeRepository.saveAndFlush(equidae);

		int databaseSizeBeforeDelete = equidaeRepository.findAll().size();

        // Get the equidae
        restEquidaeMockMvc.perform(delete("/api/equidaes/{id}", equidae.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Equidae> equidaes = equidaeRepository.findAll();
        assertThat(equidaes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
