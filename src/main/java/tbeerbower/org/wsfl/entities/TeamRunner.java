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
@Table(name = "team_runner")
public class TeamRunner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_runner_id_seq")
    @SequenceGenerator(name = "team_runner_id_seq", sequenceName = "public.team_runner_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "runner_id", nullable = false)
    private Integer runnerId;

    @Column(name = "draft_order", nullable = false)
    private Integer draftOrder = 1;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "team_season_id", nullable = false)
    private Integer teamSeasonId;

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(Integer runnerId) {
        this.runnerId = runnerId;
    }

    public Integer getDraftOrder() {
        return draftOrder;
    }

    public void setDraftOrder(Integer draftOrder) {
        this.draftOrder = draftOrder;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getTeamSeasonId() {
        return teamSeasonId;
    }

    public void setTeamSeasonId(Integer teamSeasonId) {
        this.teamSeasonId = teamSeasonId;
    }

    @Override
    public String toString() {
        return "TeamRunner{" +
                "id=" + id +
                ", runnerId=" + runnerId +
                ", draftOrder=" + draftOrder +
                ", active=" + active +
                ", teamSeasonId=" + teamSeasonId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamRunner that = (TeamRunner) o;
        return Objects.equals(id, that.id) && Objects.equals(runnerId, that.runnerId) && Objects.equals(draftOrder, that.draftOrder) && Objects.equals(active, that.active) && Objects.equals(teamSeasonId, that.teamSeasonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, runnerId, draftOrder, active, teamSeasonId);
    }
}
