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
@Table(name = "volunteer_shift")
public class VolunteerShift {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "volunteer_shift_id_seq")
    @SequenceGenerator(name = "volunteer_shift_id_seq", sequenceName = "public.volunteer_shift_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "season_id", nullable = false)
    private Integer seasonId;

    @Column(name = "race_id")
    private Integer raceId;

    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @Column(name = "tag", length = 100, nullable = false)
    private String tag;

    @Column(name = "race_definition_id")
    private Integer raceDefinitionId;

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
    }

    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getRaceDefinitionId() {
        return raceDefinitionId;
    }

    public void setRaceDefinitionId(Integer raceDefinitionId) {
        this.raceDefinitionId = raceDefinitionId;
    }

    @Override
    public String toString() {
        return "VolunteerShift{" +
                "id=" + id +
                ", seasonId=" + seasonId +
                ", raceId=" + raceId +
                ", name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", raceDefinitionId=" + raceDefinitionId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VolunteerShift that = (VolunteerShift) o;
        return Objects.equals(id, that.id) && Objects.equals(seasonId, that.seasonId) && Objects.equals(raceId, that.raceId) && Objects.equals(name, that.name) && Objects.equals(tag, that.tag) && Objects.equals(raceDefinitionId, that.raceDefinitionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, seasonId, raceId, name, tag, raceDefinitionId);
    }
}

