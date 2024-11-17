package org.tbeerbower.wsfl.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "volunteer")
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "volunteer_id_seq")
    @SequenceGenerator(name = "volunteer_id_seq", sequenceName = "public.volunteer_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "shift_id", nullable = false)
    private Integer shiftId;

    @Column(name = "runner_id", nullable = false)
    private Integer runnerId;

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public Integer getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(Integer runnerId) {
        this.runnerId = runnerId;
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "id=" + id +
                ", shiftId=" + shiftId +
                ", runnerId=" + runnerId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return Objects.equals(id, volunteer.id) && Objects.equals(shiftId, volunteer.shiftId) && Objects.equals(runnerId, volunteer.runnerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shiftId, runnerId);
    }
}
