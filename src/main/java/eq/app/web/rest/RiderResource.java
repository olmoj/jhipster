package eq.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import eq.app.domain.Rider;
import eq.app.repository.RiderRepository;
import eq.app.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Rider.
 */
@RestController
@RequestMapping("/api")
public class RiderResource {

    private final Logger log = LoggerFactory.getLogger(RiderResource.class);

    @Inject
    private RiderRepository riderRepository;

    /**
     * POST  /riders -> Create a new rider.
     */
    @RequestMapping(value = "/riders",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rider> createRider(@RequestBody Rider rider) throws URISyntaxException {
        log.debug("REST request to save Rider : {}", rider);
        if (rider.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rider", "idexists", "A new rider cannot already have an ID")).body(null);
        }
        Rider result = riderRepository.save(rider);
        return ResponseEntity.created(new URI("/api/riders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rider", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /riders -> Updates an existing rider.
     */
    @RequestMapping(value = "/riders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rider> updateRider(@RequestBody Rider rider) throws URISyntaxException {
        log.debug("REST request to update Rider : {}", rider);
        if (rider.getId() == null) {
            return createRider(rider);
        }
        Rider result = riderRepository.save(rider);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rider", rider.getId().toString()))
            .body(result);
    }

    /**
     * GET  /riders -> get all the riders.
     */
    @RequestMapping(value = "/riders",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Rider> getAllRiders() {
        log.debug("REST request to get all Riders");
        return riderRepository.findAllWithEagerRelationships();
            }

    /**
     * GET  /riders/:id -> get the "id" rider.
     */
    @RequestMapping(value = "/riders/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rider> getRider(@PathVariable Long id) {
        log.debug("REST request to get Rider : {}", id);
        Rider rider = riderRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(rider)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /riderFromUser/:id -> get the "id" rider.
     */
    @RequestMapping(value = "/riderFromUser/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rider> getRiderFromUser(@PathVariable Long id) {
        log.debug("REST request to get Rider from User id : {}", id);
        Rider rider = riderRepository.findOnebyUserId(id);
        return Optional.ofNullable(rider)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /riders/:id -> delete the "id" rider.
     */
    @RequestMapping(value = "/riders/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRider(@PathVariable Long id) {
        log.debug("REST request to delete Rider : {}", id);
        riderRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rider", id.toString())).build();
    }
}
