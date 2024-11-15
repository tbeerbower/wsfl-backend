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
@Table(name = "team_season")
public class TeamSeason {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_season_id_seq")
    @SequenceGenerator(name = "team_season_id_seq", sequenceName = "public.team_season_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "team_id", nullable = false)
    private Integer teamId;

    @Column(name = "season_id", nullable = false)
    private Integer seasonId;

    @Column(name = "draft_order", nullable = false)
    private Integer draftOrder;

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
    }

    public Integer getDraftOrder() {
        return draftOrder;
    }

    public void setDraftOrder(Integer draftOrder) {
        this.draftOrder = draftOrder;
    }

    @Override
    public String toString() {
        return "TeamSeason{" +
                "id=" + id +
                ", teamId=" + teamId +
                ", seasonId=" + seasonId +
                ", draftOrder=" + draftOrder +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamSeason that = (TeamSeason) o;
        return Objects.equals(id, that.id) && Objects.equals(teamId, that.teamId) && Objects.equals(seasonId, that.seasonId) && Objects.equals(draftOrder, that.draftOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teamId, seasonId, draftOrder);
    }
}
