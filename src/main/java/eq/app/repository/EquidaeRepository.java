package eq.app.repository;

import eq.app.domain.Equidae;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Equidae entity.
 */
public interface EquidaeRepository extends JpaRepository<Equidae,Long> {

    @Query("select distinct equidae from Equidae equidae left join fetch equidae.favorites")
    List<Equidae> findAllWithEagerRelationships();

    @Query("select equidae from Equidae equidae left join fetch equidae.favorites where equidae.id =:id")
    Equidae findOneWithEagerRelationships(@Param("id") Long id);

}
