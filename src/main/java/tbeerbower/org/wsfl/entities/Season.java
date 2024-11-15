package tbeerbower.org.wsfl.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "season")
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "season_id_seq")
    @SequenceGenerator(name = "season_id_seq", sequenceName = "public.season_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @Column(name = "num_scores", nullable = false)
    private Integer numScores;

    @Column(name = "draft_rounds", nullable = false)
    private Integer draftRounds;

    @Column(name = "supplemental_rounds", nullable = false)
    private Integer supplementalRounds;

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumScores() {
        return numScores;
    }

    public void setNumScores(Integer numScores) {
        this.numScores = numScores;
    }

    public Integer getDraftRounds() {
        return draftRounds;
    }

    public void setDraftRounds(Integer draftRounds) {
        this.draftRounds = draftRounds;
    }

    public Integer getSupplementalRounds() {
        return supplementalRounds;
    }

    public void setSupplementalRounds(Integer supplementalRounds) {
        this.supplementalRounds = supplementalRounds;
    }

    @Override
    public String toString() {
        return "Season{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numScores=" + numScores +
                ", draftRounds=" + draftRounds +
                ", supplementalRounds=" + supplementalRounds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Season season = (Season) o;
        return Objects.equals(id, season.id) && Objects.equals(name, season.name) && Objects.equals(numScores, season.numScores) && Objects.equals(draftRounds, season.draftRounds) && Objects.equals(supplementalRounds, season.supplementalRounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, numScores, draftRounds, supplementalRounds);
    }
}
