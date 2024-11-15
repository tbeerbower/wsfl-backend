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
@Table(name = "race_definition")
public class RaceDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "race_definition_id_seq")
    @SequenceGenerator(name = "race_definition_id_seq", sequenceName = "public.race_definition_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @Column(name = "short_name", length = 45, nullable = false)
    private String shortName;

    @Column(name = "small_icon", length = 45, nullable = false)
    private String smallIcon;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSmallIcon() {
        return smallIcon;
    }

    public void setSmallIcon(String smallIcon) {
        this.smallIcon = smallIcon;
    }

    @Override
    public String toString() {
        return "RaceDefinition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", smallIcon='" + smallIcon + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RaceDefinition that = (RaceDefinition) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(shortName, that.shortName) && Objects.equals(smallIcon, that.smallIcon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shortName, smallIcon);
    }
}
