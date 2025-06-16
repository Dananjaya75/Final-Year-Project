package lk.earth.earthuniversity.entity;

import javax.persistence.*;

@Entity
public class Privilages {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "authority")
    private String authority;
    @ManyToOne
    @JoinColumn(name = "operation_id", referencedColumnName = "id", nullable = false)
    private Operation operation;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;
    @ManyToOne
    @JoinColumn(name = "module_id", referencedColumnName = "id", nullable = false)
    private Module module;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Privilages that = (Privilages) o;

        if (id != that.id) return false;
        if (authority != null ? !authority.equals(that.authority) : that.authority != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (authority != null ? authority.hashCode() : 0);
        return result;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
