package eq.app.web.rest;

import eq.app.Application;
import eq.app.domain.Rider;
import eq.app.repository.RiderRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the RiderResource REST controller.
 *
 * @see RiderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RiderResourceIntTest {


    private static final Integer DEFAULT_LVL_CSO = 1;
    private static final Integer UPDATED_LVL_CSO = 2;

    private static final Integer DEFAULT_LVL_DRESSAGE = 1;
    private static final Integer UPDATED_LVL_DRESSAGE = 2;

    private static final Integer DEFAULT_LVL_RIDING = 1;
    private static final Integer UPDATED_LVL_RIDING = 2;
    private static final String DEFAULT_AVAILABILITIES = "AAAAA";
    private static final String UPDATED_AVAILABILITIES = "BBBBB";

    @Inject
    private RiderRepository riderRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRiderMockMvc;

    private Rider rider;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RiderResource riderResource = new RiderResource();
        ReflectionTestUtils.setField(riderResource, "riderRepository", riderRepository);
        this.restRiderMockMvc = MockMvcBuilders.standaloneSetup(riderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rider = new Rider();
        rider.setLvlCSO(DEFAULT_LVL_CSO);
        rider.setLvlDressage(DEFAULT_LVL_DRESSAGE);
        rider.setLvlRiding(DEFAULT_LVL_RIDING);
        rider.setAvailabilities(DEFAULT_AVAILABILITIES);
    }

    @Test
    @Transactional
    public void createRider() throws Exception {
        int databaseSizeBeforeCreate = riderRepository.findAll().size();

        // Create the Rider

        restRiderMockMvc.perform(post("/api/riders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rider)))
                .andExpect(status().isCreated());

        // Validate the Rider in the database
        List<Rider> riders = riderRepository.findAll();
        assertThat(riders).hasSize(databaseSizeBeforeCreate + 1);
        Rider testRider = riders.get(riders.size() - 1);
        assertThat(testRider.getLvlCSO()).isEqualTo(DEFAULT_LVL_CSO);
        assertThat(testRider.getLvlDressage()).isEqualTo(DEFAULT_LVL_DRESSAGE);
        assertThat(testRider.getLvlRiding()).isEqualTo(DEFAULT_LVL_RIDING);
        assertThat(testRider.getAvailabilities()).isEqualTo(DEFAULT_AVAILABILITIES);
    }

    @Test
    @Transactional
    public void getAllRiders() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riders
        restRiderMockMvc.perform(get("/api/riders?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rider.getId().intValue())))
                .andExpect(jsonPath("$.[*].lvlCSO").value(hasItem(DEFAULT_LVL_CSO)))
                .andExpect(jsonPath("$.[*].lvlDressage").value(hasItem(DEFAULT_LVL_DRESSAGE)))
                .andExpect(jsonPath("$.[*].lvlRiding").value(hasItem(DEFAULT_LVL_RIDING)))
                .andExpect(jsonPath("$.[*].availabilities").value(hasItem(DEFAULT_AVAILABILITIES.toString())));
    }

    @Test
    @Transactional
    public void getRider() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get the rider
        restRiderMockMvc.perform(get("/api/riders/{id}", rider.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rider.getId().intValue()))
            .andExpect(jsonPath("$.lvlCSO").value(DEFAULT_LVL_CSO))
            .andExpect(jsonPath("$.lvlDressage").value(DEFAULT_LVL_DRESSAGE))
            .andExpect(jsonPath("$.lvlRiding").value(DEFAULT_LVL_RIDING))
            .andExpect(jsonPath("$.availabilities").value(DEFAULT_AVAILABILITIES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRider() throws Exception {
        // Get the rider
        restRiderMockMvc.perform(get("/api/riders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRider() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

		int databaseSizeBeforeUpdate = riderRepository.findAll().size();

        // Update the rider
        rider.setLvlCSO(UPDATED_LVL_CSO);
        rider.setLvlDressage(UPDATED_LVL_DRESSAGE);
        rider.setLvlRiding(UPDATED_LVL_RIDING);
        rider.setAvailabilities(UPDATED_AVAILABILITIES);

        restRiderMockMvc.perform(put("/api/riders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rider)))
                .andExpect(status().isOk());

        // Validate the Rider in the database
        List<Rider> riders = riderRepository.findAll();
        assertThat(riders).hasSize(databaseSizeBeforeUpdate);
        Rider testRider = riders.get(riders.size() - 1);
        assertThat(testRider.getLvlCSO()).isEqualTo(UPDATED_LVL_CSO);
        assertThat(testRider.getLvlDressage()).isEqualTo(UPDATED_LVL_DRESSAGE);
        assertThat(testRider.getLvlRiding()).isEqualTo(UPDATED_LVL_RIDING);
        assertThat(testRider.getAvailabilities()).isEqualTo(UPDATED_AVAILABILITIES);
    }

    @Test
    @Transactional
    public void deleteRider() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

		int databaseSizeBeforeDelete = riderRepository.findAll().size();

        // Get the rider
        restRiderMockMvc.perform(delete("/api/riders/{id}", rider.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Rider> riders = riderRepository.findAll();
        assertThat(riders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
