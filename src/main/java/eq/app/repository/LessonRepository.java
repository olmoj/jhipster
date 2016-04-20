package eq.app.repository;

import eq.app.domain.Lesson;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Lesson entity.
 */
public interface LessonRepository extends JpaRepository<Lesson,Long> {

    @Query("select distinct lesson from Lesson lesson left join fetch lesson.riders left join fetch lesson.equidaes")
    List<Lesson> findAllWithEagerRelationships();

    @Query("select lesson from Lesson lesson left join fetch lesson.riders left join fetch lesson.equidaes where lesson.id =:id")
    Lesson findOneWithEagerRelationships(@Param("id") Long id);

}
