package tbeerbower.org.wsfl.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "result")
public class Result implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_id_seq")
    @SequenceGenerator(name = "result_id_seq", sequenceName = "result_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "runner_id", nullable = false)
    private Integer runnerId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "runner_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Runner runner;

    @Column(name = "place_gender", nullable = false)
    private Integer placeGender;

    @Column(name = "place_overall", nullable = false)
    private Integer placeOverall;

    @Column(name = "time", nullable = false)
    private String time;

    @Column(name = "race_id", insertable = false, updatable = false)
    private Integer raceId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "race_id", referencedColumnName = "id")
    private Race race;

    public Result() {
    }

    // Getters and Setters

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

    public Runner getRunner() {
        return runner;
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
    }

    public Integer getPlaceGender() {
        return placeGender;
    }

    public void setPlaceGender(Integer placeGender) {
        this.placeGender = placeGender;
    }

    public Integer getPlaceOverall() {
        return placeOverall;
    }

    public void setPlaceOverall(Integer placeOverall) {
        this.placeOverall = placeOverall;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", runnerId=" + runnerId +
                ", placeGender=" + placeGender +
                ", placeOverall=" + placeOverall +
                ", time='" + time + '\'' +
                ", raceId=" + raceId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(id, result.id) && Objects.equals(runnerId, result.runnerId) && Objects.equals(placeGender, result.placeGender) && Objects.equals(placeOverall, result.placeOverall) && Objects.equals(time, result.time) && Objects.equals(raceId, result.raceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, runnerId, placeGender, placeOverall, time, raceId);
    }
}
