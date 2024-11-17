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
@Table(name = "runner_alias")
public class RunnerAlias {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "runner_alias_id_seq")
    @SequenceGenerator(name = "runner_alias_id_seq", sequenceName = "public.runner_alias_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @Column(name = "runner_id", nullable = false)
    private Integer runnerId;

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

    public Integer getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(Integer runnerId) {
        this.runnerId = runnerId;
    }

    @Override
    public String toString() {
        return "RunnerAlias{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", runnerId=" + runnerId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RunnerAlias that = (RunnerAlias) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(runnerId, that.runnerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, runnerId);
    }
}
