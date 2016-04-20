package eq.app.domain;

import java.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Lesson.
 */
@Entity
@Table(name = "lesson")
public class Lesson implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "type")
    private String type;

    @Column(name = "level")
    private Integer level;

    @ManyToMany
    @JoinTable(name = "lesson_rider_id",
               joinColumns = @JoinColumn(name="lessons_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="riders_id", referencedColumnName="ID"))
    private Set<Rider> riders = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "lesson_equidae_id",
               joinColumns = @JoinColumn(name="lessons_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="equidaes_id", referencedColumnName="ID"))
    private Set<Equidae> equidaes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Set<Rider> getRiders() {
        return riders;
    }

    public void setRiders(Set<Rider> riders) {
        this.riders = riders;
    }

    public Set<Equidae> getEquidaes() {
        return equidaes;
    }

    public void setEquidaes(Set<Equidae> equidaes) {
        this.equidaes = equidaes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lesson lesson = (Lesson) o;
        if(lesson.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lesson.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Lesson{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", type='" + type + "'" +
            ", level='" + level + "'" +
            '}';
    }
}
