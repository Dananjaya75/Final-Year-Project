package lk.earth.earthuniversity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Operation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "opetype_id", referencedColumnName = "id", nullable = false)
    private Opetype opetype;
    @ManyToOne
    @JoinColumn(name = "module_id", referencedColumnName = "id")
    private Module module;
    @JsonIgnore
    @OneToMany(mappedBy = "operation")
    private Collection<Privilages> privilages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation = (Operation) o;

        if (id != operation.id) return false;
        if (name != null ? !name.equals(operation.name) : operation.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public Opetype getOpetype() {
        return opetype;
    }

    public void setOpetype(Opetype opetype) {
        this.opetype = opetype;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Collection<Privilages> getPrivilages() {
        return privilages;
    }

    public void setPrivilages(Collection<Privilages> privilages) {
        this.privilages = privilages;
    }
}
