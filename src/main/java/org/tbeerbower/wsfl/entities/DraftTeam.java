package org.tbeerbower.wsfl.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "team_season")
public class DraftTeam {
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

    @Column(name = "league_id", nullable = false, insertable = false, updatable = false)
    private Integer leagueId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "league_id", referencedColumnName = "id")
    private League league;

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

    public Integer getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    @Override
    public String toString() {
        return "Draft{" +
                "id=" + id +
                ", teamId=" + teamId +
                ", seasonId=" + seasonId +
                ", draftOrder=" + draftOrder +
                ", leagueId=" + leagueId +
                ", league=" + league +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DraftTeam draftTeam = (DraftTeam) o;
        return Objects.equals(id, draftTeam.id) && Objects.equals(teamId, draftTeam.teamId) && Objects.equals(seasonId, draftTeam.seasonId) && Objects.equals(draftOrder, draftTeam.draftOrder) && Objects.equals(leagueId, draftTeam.leagueId) && Objects.equals(league, draftTeam.league);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teamId, seasonId, draftOrder, leagueId, league);
    }
}
