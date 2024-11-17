package org.tbeerbower.wsfl.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "race")
public class Race implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "race_id_seq")
    @SequenceGenerator(name = "race_id_seq", sequenceName = "race_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "season_id", insertable = false, updatable = false)
    private Integer seasonId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "season_id", referencedColumnName = "id")
    private Season season;

    @Column(name = "week", nullable = false)
    private Integer week;

    @Column(name = "cancelled", nullable = false)
    private Boolean cancelled;

    @Column(name = "race_definition_id", insertable = false, updatable = false)
    private Integer raceDefinitionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "race_definition_id", referencedColumnName = "id")
    private RaceDefinition raceDefinition;

    public Race() {
    }

    // Getters and Setters

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

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Integer getRaceDefinitionId() {
        return raceDefinitionId;
    }

    public void setRaceDefinitionId(Integer raceDefinitionId) {
        this.raceDefinitionId = raceDefinitionId;
    }

    public RaceDefinition getRaceDefinition() {
        return raceDefinition;
    }

    public void setRaceDefinition(RaceDefinition raceDefinition) {
        this.raceDefinition = raceDefinition;
    }

    @Override
    public String toString() {
        return "Race{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", seasonId=" + seasonId +
                ", week=" + week +
                ", cancelled=" + cancelled +
                ", raceDefinitionId=" + raceDefinitionId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Race race = (Race) o;
        return Objects.equals(id, race.id) && Objects.equals(name, race.name) && Objects.equals(seasonId, race.seasonId) && Objects.equals(week, race.week) && Objects.equals(cancelled, race.cancelled) && Objects.equals(raceDefinitionId, race.raceDefinitionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, seasonId, week, cancelled, raceDefinitionId);
    }
}
