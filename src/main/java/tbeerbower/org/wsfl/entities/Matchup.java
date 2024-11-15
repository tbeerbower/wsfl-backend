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
@Table(name = "matchup")
public class Matchup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "matchup_id_seq")
    @SequenceGenerator(name = "matchup_id_seq", sequenceName = "public.matchup_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "season_id", nullable = false)
    private Integer seasonId;

    @Column(name = "race_id", nullable = false)
    private Integer raceId;

    @Column(name = "team_a_id", nullable = false)
    private Integer teamAId;

    @Column(name = "team_b_id", nullable = false)
    private Integer teamBId;

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

    public Integer getTeamAId() {
        return teamAId;
    }

    public void setTeamAId(Integer teamAId) {
        this.teamAId = teamAId;
    }

    public Integer getTeamBId() {
        return teamBId;
    }

    public void setTeamBId(Integer teamBId) {
        this.teamBId = teamBId;
    }

    @Override
    public String toString() {
        return "Matchup{" +
                "id=" + id +
                ", seasonId=" + seasonId +
                ", raceId=" + raceId +
                ", teamAId=" + teamAId +
                ", teamBId=" + teamBId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matchup matchup = (Matchup) o;
        return Objects.equals(id, matchup.id) && Objects.equals(seasonId, matchup.seasonId) && Objects.equals(raceId, matchup.raceId) && Objects.equals(teamAId, matchup.teamAId) && Objects.equals(teamBId, matchup.teamBId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, seasonId, raceId, teamAId, teamBId);
    }
}
