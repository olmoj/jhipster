package eq.app.repository;

import eq.app.domain.Rider;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Rider entity.
 */
public interface RiderRepository extends JpaRepository<Rider,Long> {

    @Query("select distinct rider from Rider rider left join fetch rider.favorite_equidaes")
    List<Rider> findAllWithEagerRelationships();

    @Query("select rider from Rider rider left join fetch rider.favorite_equidaes where rider.id =:id")
    Rider findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select rider from Rider rider where rider.user_rider.id =:id")
    Rider findOnebyUserId(@Param("id") Long id);

}
