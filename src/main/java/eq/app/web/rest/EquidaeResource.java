package eq.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import eq.app.domain.Equidae;
import eq.app.repository.EquidaeRepository;
import eq.app.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Equidae.
 */
@RestController
@RequestMapping("/api")
public class EquidaeResource {

    private final Logger log = LoggerFactory.getLogger(EquidaeResource.class);
        
    @Inject
    private EquidaeRepository equidaeRepository;
    
    /**
     * POST  /equidaes -> Create a new equidae.
     */
    @RequestMapping(value = "/equidaes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Equidae> createEquidae(@Valid @RequestBody Equidae equidae) throws URISyntaxException {
        log.debug("REST request to save Equidae : {}", equidae);
        if (equidae.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("equidae", "idexists", "A new equidae cannot already have an ID")).body(null);
        }
        Equidae result = equidaeRepository.save(equidae);
        return ResponseEntity.created(new URI("/api/equidaes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("equidae", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /equidaes -> Updates an existing equidae.
     */
    @RequestMapping(value = "/equidaes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Equidae> updateEquidae(@Valid @RequestBody Equidae equidae) throws URISyntaxException {
        log.debug("REST request to update Equidae : {}", equidae);
        if (equidae.getId() == null) {
            return createEquidae(equidae);
        }
        Equidae result = equidaeRepository.save(equidae);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("equidae", equidae.getId().toString()))
            .body(result);
    }

    /**
     * GET  /equidaes -> get all the equidaes.
     */
    @RequestMapping(value = "/equidaes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Equidae> getAllEquidaes() {
        log.debug("REST request to get all Equidaes");
        return equidaeRepository.findAllWithEagerRelationships();
            }

    /**
     * GET  /equidaes/:id -> get the "id" equidae.
     */
    @RequestMapping(value = "/equidaes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Equidae> getEquidae(@PathVariable Long id) {
        log.debug("REST request to get Equidae : {}", id);
        Equidae equidae = equidaeRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(equidae)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /equidaes/:id -> delete the "id" equidae.
     */
    @RequestMapping(value = "/equidaes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEquidae(@PathVariable Long id) {
        log.debug("REST request to delete Equidae : {}", id);
        equidaeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("equidae", id.toString())).build();
    }
}
