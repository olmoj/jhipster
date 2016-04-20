package eq.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Rider.
 */
@Entity
@Table(name = "rider")
public class Rider implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "lvl_cso")
    private Integer lvlCSO;

    @Column(name = "lvl_dressage")
    private Integer lvlDressage;

    @Column(name = "lvl_riding")
    private Integer lvlRiding;

    @Column(name = "availabilities")
    private String availabilities;

    @ManyToMany
    @JoinTable(name = "rider_favorite_equidae",
               joinColumns = @JoinColumn(name="riders_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="favorite_equidaes_id", referencedColumnName="ID"))
    private Set<Equidae> favorite_equidaes = new HashSet<>();

    @OneToOne
    private User user_rider;

    @ManyToMany(mappedBy = "riders")
    @JsonIgnore
    private Set<Lesson> lessons = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLvlCSO() {
        return lvlCSO;
    }

    public void setLvlCSO(Integer lvlCSO) {
        this.lvlCSO = lvlCSO;
    }

    public Integer getLvlDressage() {
        return lvlDressage;
    }

    public void setLvlDressage(Integer lvlDressage) {
        this.lvlDressage = lvlDressage;
    }

    public Integer getLvlRiding() {
        return lvlRiding;
    }

    public void setLvlRiding(Integer lvlRiding) {
        this.lvlRiding = lvlRiding;
    }

    public String getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(String availabilities) {
        this.availabilities = availabilities;
    }

    public Set<Equidae> getFavorite_equidaes() {
        return favorite_equidaes;
    }

    public void setFavorite_equidaes(Set<Equidae> equidaes) {
        this.favorite_equidaes = equidaes;
    }

    public User getUser_rider() {
        return user_rider;
    }

    public void setUser_rider(User user) {
        this.user_rider = user;
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
        Rider rider = (Rider) o;
        if(rider.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rider.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Rider{" +
            "id=" + id +
            ", lvlCSO='" + lvlCSO + "'" +
            ", lvlDressage='" + lvlDressage + "'" +
            ", lvlRiding='" + lvlRiding + "'" +
            ", availabilities='" + availabilities + "'" +
            '}';
    }
}
