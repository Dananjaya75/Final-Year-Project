package lk.earth.earthuniversity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Collection;

@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "salt")
    private String salt;
    @Basic
    @Column(name = "docreated")
    private Date docreated;
    @Basic
    @Column(name = "tocreated")
    private Time tocreated;
    @Basic
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "usetype_id", referencedColumnName = "id", nullable = false)
    private Usetype usetype;
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "usestatus_id", referencedColumnName = "id", nullable = false)
    private Usestatus usestatus;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Collection<Userrole> userroles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getDocreated() {
        return docreated;
    }

    public void setDocreated(Date docreated) {
        this.docreated = docreated;
    }

    public Time getTocreated() {
        return tocreated;
    }

    public void setTocreated(Time tocreated) {
        this.tocreated = tocreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (salt != null ? !salt.equals(user.salt) : user.salt != null) return false;
        if (docreated != null ? !docreated.equals(user.docreated) : user.docreated != null) return false;
        if (tocreated != null ? !tocreated.equals(user.tocreated) : user.tocreated != null) return false;
        if (description != null ? !description.equals(user.description) : user.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        result = 31 * result + (docreated != null ? docreated.hashCode() : 0);
        result = 31 * result + (tocreated != null ? tocreated.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public Usetype getUsetype() {
        return usetype;
    }

    public void setUsetype(Usetype usetype) {
        this.usetype = usetype;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Usestatus getUsestatus() {
        return usestatus;
    }

    public void setUsestatus(Usestatus usestatus) {
        this.usestatus = usestatus;
    }

    public Collection<Userrole> getUserroles() {
        return userroles;
    }

    public void setUserroles(Collection<Userrole> userroles) {
        this.userroles = userroles;
    }
}
