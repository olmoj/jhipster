package eq.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Equidae.
 */
@Entity
@Table(name = "equidae")
public class Equidae implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "required_lvl_cso")
    private Integer requiredLvlCSO;

    @Column(name = "required_lvl_dressage")
    private Integer requiredLvlDressage;

    @Column(name = "required_lvl_riding")
    private Integer requiredLvlRiding;

    @Column(name = "unavailabilities")
    private LocalDate unavailabilities;

    @ManyToMany(mappedBy = "favorite_equidaes")
    @JsonIgnore
    private Set<Rider> favorites = new HashSet<>();

    @ManyToMany(mappedBy = "equidaes")
    @JsonIgnore
    private Set<Lesson> lessons = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRequiredLvlCSO() {
        return requiredLvlCSO;
    }

    public void setRequiredLvlCSO(Integer requiredLvlCSO) {
        this.requiredLvlCSO = requiredLvlCSO;
    }

    public Integer getRequiredLvlDressage() {
        return requiredLvlDressage;
    }

    public void setRequiredLvlDressage(Integer requiredLvlDressage) {
        this.requiredLvlDressage = requiredLvlDressage;
    }

    public Integer getRequiredLvlRiding() {
        return requiredLvlRiding;
    }

    public void setRequiredLvlRiding(Integer requiredLvlRiding) {
        this.requiredLvlRiding = requiredLvlRiding;
    }

    public LocalDate getUnavailabilities() {
        return unavailabilities;
    }

    public void setUnavailabilities(LocalDate unavailabilities) {
        this.unavailabilities = unavailabilities;
    }

    public Set<Rider> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<Rider> riders) {
        this.favorites = riders;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Equidae equidae = (Equidae) o;
        if(equidae.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, equidae.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Equidae{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", requiredLvlCSO='" + requiredLvlCSO + "'" +
            ", requiredLvlDressage='" + requiredLvlDressage + "'" +
            ", requiredLvlRiding='" + requiredLvlRiding + "'" +
            ", unavailabilities='" + unavailabilities + "'" +
            '}';
    }
}
